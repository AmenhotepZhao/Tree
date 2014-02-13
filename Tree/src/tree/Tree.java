package tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * @author Fan Zhao
 *
 * @param <V> the type of a node of the tree
 */
public class Tree<V> {
	
	V value;
	LinkedList<Tree<V>> children;
	
	/**
	 * Constructs a new tree with given value and children
	 * 
	 * @param value     the value of the new tree
	 * @param children  are the children of the new tree
	 */
	@SuppressWarnings("unchecked")
	public Tree(V value, Tree<V>... children) {
		setValue(value);
		addChildren(children);
	}
	
	/**
	 * Set the value of the tree
	 * 
	 * @param value the new value of the tree
	 */
	public void setValue(V value) {
		this.value = value;
	}
	
	/**
	 * Gets the value of the tree
	 * 
	 * @return the value of the tree
	 */
	public V getValue() {
		return value;
	}
	
	/**
	 * Gives the number of the children
	 * 
	 * @return the number of children of the tree
	 */
	public int numberOfChildren() {
		if (children == null) return 0;
		return children.size();
	}
	
	/**
	 * Gives the first child of the tree
	 * 
	 * @return the first child of the tree, null if there is no child
	 */
	public Tree<V> firstChild() {
		if(numberOfChildren() == 0) return null;
		return children.getFirst();
	}
	
	/**
	 * Gives the last child of the tree
	 * 
	 * @return the last child of the tree, null if there is no child
	 */
	public Tree<V> lastChild() {
		if(numberOfChildren() == 0) return null;
		return children.getLast();
	}
	
	/**
	 * Gives the index-th child of the tree
	 * 
	 * @param index  					the index of the child
	 * @return		 					the index-th child of the tree	
	 * @throws NoSuchElementException	if the index is out of bound
	 */
	public Tree<V> child(int index) throws NoSuchElementException {
		if(index < 0 || index >= numberOfChildren()) throw new NoSuchElementException();
		return children.get(index);
	}
	
	/**
	 * Validates whether a tree is a leaf 
	 * 
	 * @return true if the tree has no children, false if the tree has children
	 */
	public boolean isLeaf() {
		return (numberOfChildren() == 0);
	}

	/**
	 * Creates an iterator for the children of the tree
	 * 
	 * Use the iterator of LinkedList to make the desired iterator
	 * 
	 * @return an iterator of the children
	 */
	public Iterator<Tree<V>> children() {
		return new ArrayList<Tree<V>>(children).iterator(); 
	}
	
	/**
	 * Checked whether the tree contains a node
	 * 
	 * @param node  the node need to be checked whether in the tree
	 * @return true if the tree contains the node, false if the tree doesn't  
	 */
	private boolean contains(Tree<V> node) {
		if (this == node) return true;
		if (numberOfChildren() == 0) return false;
		for (Tree<V> child : children) {
			if (child.contains(node)) return true;
		}
		return false;
		
	}
	
	/**
	 * Overrides the toString method
	 */
	@Override
	public String toString() {
		return traversal("");
	}
	
	/**
	 * creates a string representing the tree by doing pre-order traversal of the tree recursively
	 * 
	 * @param indent the space before this node
	 * @return a string that represents the tree
	 */
	private String traversal(String indent) {
		String str = indent + value.toString();
		if (isLeaf()) return str;
		Iterator<Tree<V>> itr = children();
		while (itr.hasNext()) {
			str = str + "\n" + itr.next().traversal("  " + indent);
		}
		return str;
	}

	/**
	 * Overrides the equals method
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Tree<?>)) return false;
		Tree<?> tree = (Tree<?>) obj;
		if (!value.equals(tree.value)) return false;
		if (numberOfChildren() != tree.numberOfChildren()) return false;
		for (int i=0; i<numberOfChildren(); i++){
			if (!child(i).equals(tree.child(i))) return false;
		}
		return true;
	}
	
	/**
	 * add a new child to the tree
	 * 
	 * @param newChild                   the child needed to be add
	 * @throws IllegalArgumentException  if adding the child made an invalid tree
	 */
	public void addChild(Tree<V> newChild) throws IllegalArgumentException {
		
		if (newChild.contains(this) || contains(newChild)) throw new IllegalArgumentException();
		if (children == null) children = new LinkedList<Tree<V>>();
		children.add(newChild);
	}
	
	/**
	 * Adds a new child to a given location of the tree
	 * 
	 * @param index                      the location that the child would be added to
	 * @param newChild                   the child needed to be add
	 * @throws IllegalArgumentException  if adding the child made an invalid tree or the index is out of bound
	 */
	public void addChild(int index, Tree<V> newChild) throws IllegalArgumentException {
		if (newChild.contains(this)) throw new IllegalArgumentException();
		if (index < 0 || index > numberOfChildren())  throw new IllegalArgumentException();
		if (children == null) children = new LinkedList<Tree<V>>();
		children.add(index, newChild);
	}
	
	/**
	 * Adds a list of children to the tree
	 * 
	 * @param children                  the children to be added
	 * @throws IllegalArgumentException if failed to add any one of the child 
	 */
	@SuppressWarnings("unchecked")
	public void addChildren(Tree<V>... children ) throws IllegalArgumentException {
		for (Tree<V> child : children) {
			try {
				addChild(child);
			} catch (IllegalArgumentException e) {
				throw e;
			}
		}
	}
	
	/**
	 * Removes the child ar a given location
	 * 
	 * @param index                   the location of the child to be removed
	 * @return						  the removed child
	 * @throws NoSuchElementException if the index is out of bound
	 */
	public Tree<V> removeChild(int index) throws NoSuchElementException {
		if (index < 0 || index >= numberOfChildren()) throw new NoSuchElementException();
		return children.remove(index);
	}
	
}
