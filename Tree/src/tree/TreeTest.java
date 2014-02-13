package tree;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import org.junit.rules.ExpectedException;

public class TreeTest {

	@Before
	public void setUp() throws Exception {
		
	}
	
	@Rule
	  public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testSetValue() {
		Tree<String> tree1 = new Tree<String>("");
		tree1.setValue("val");
		assertEquals(tree1.getValue(), "val");
		Tree<Integer> tree2 = new Tree<Integer>(Integer.valueOf(0));
		tree2.setValue(Integer.valueOf(1));
		assertEquals(tree2.getValue(), Integer.valueOf(1));
		
	}
	
	@Test
	public void testGetValue() {
		Tree<String> tree1 = new Tree<String>("val");
		assertEquals(tree1.getValue(), "val");
		Tree<Integer> tree2 = new Tree<Integer>(Integer.valueOf(0));
		assertEquals(tree2.getValue(), Integer.valueOf(0));
	}
	
	@Test
	public void testNumberOfChildren() {
		Tree<String> child1 = new Tree<String>("1");
		Tree<String> child2 = new Tree<String>("2");
		Tree<String> child3 = new Tree<String>("3");
		Tree<String> parent = new Tree<String>("0");
		assertEquals(parent.numberOfChildren(),0);
		parent = new Tree<String>("0", child1);
		assertEquals(parent.numberOfChildren(),1);
		parent = new Tree<String>("0", child1, child2);
		assertEquals(parent.numberOfChildren(),2);
		parent = new Tree<String>("0", child1, child2, child3);
		assertEquals(parent.numberOfChildren(),3);
		
	}
	
	@Test
	public void testFirstChild() {
		Tree<String> child1 = new Tree<String>("1");
		Tree<String> child2 = new Tree<String>("2");
		Tree<String> child3 = new Tree<String>("3");
		Tree<String> parent = new Tree<String>("0", child1, child2, child3);
		assertEquals(parent.firstChild(),child1);
	}
	
	@Test
	public void testLastChild() {
		Tree<String> child1 = new Tree<String>("1");
		Tree<String> child2 = new Tree<String>("2");
		Tree<String> child3 = new Tree<String>("3");
		Tree<String> parent = new Tree<String>("0", child1, child2, child3);
		assertEquals(parent.lastChild(),child3);
	}
	
	@Test
	public void testChild() {
		Tree<String> child1 = new Tree<String>("1");
		Tree<String> child2 = new Tree<String>("2");
		Tree<String> child3 = new Tree<String>("3");
		Tree<String> parent = new Tree<String>("0", child1, child2, child3);
		assertEquals(parent.child(0),child1);
		assertEquals(parent.child(1),child2);
		assertEquals(parent.child(2),child3);
	}
	
	
	@Test
	public void testIsLeaf() {
		Tree<String> tree1 = new Tree<String>("1");
		Tree<String> tree2 = new Tree<String>("2");
		Tree<String> tree3 = new Tree<String>("3", tree2);
		Tree<String> tree4 = new Tree<String>("0", tree1, tree3);
		assertTrue(tree1.isLeaf());
		assertTrue(tree2.isLeaf());
		assertFalse(tree3.isLeaf());
		assertFalse(tree4.isLeaf());
	}
	
	@Test
	public void testToString() {
		Tree<String> tree1 = new Tree<String>("1");
		Tree<String> tree2 = new Tree<String>("2");
		Tree<String> tree3 = new Tree<String>("3", tree2);
		Tree<String> tree4 = new Tree<String>("0", tree1, tree3);
		String str = "0\n  1\n  3\n    2";
		assertEquals(tree4.toString(),str);
	}
	
	@Test
	public void testEquals() {
		Tree<String> tree1 = new Tree<String>("1");
		Tree<String> tree2 = new Tree<String>("1");
		Tree<String> tree3 = new Tree<String>("0", tree1);
		Tree<String> tree4 = new Tree<String>("0", tree2);
		assertEquals(tree3,tree3);
		assertEquals(tree3,tree4);
		assertTrue(tree3.equals(tree4));
		assertFalse(tree3.equals(tree1));
	}
	
	@Test
	public void testAddChild() {
		Tree<String> tree1 = new Tree<String>("1");
		Tree<String> tree2 = new Tree<String>("2");
		Tree<String> tree3 = new Tree<String>("3");
		Tree<String> tree4 = new Tree<String>("4");
		exception.expect(IllegalArgumentException.class);
		tree4.addChild(tree4);
		tree4.addChild(tree3);
		assertEquals(tree4.numberOfChildren(),1);
		assertEquals(tree4.firstChild(),tree3);
		exception.expect(IllegalArgumentException.class);
		tree4.addChild(tree3);
		exception.expect(IllegalArgumentException.class);
		tree3.addChild(tree4);
		
		exception.expect(IllegalArgumentException.class);
		tree4.addChild(2,tree1);
		
		tree4.addChild(0,tree2);
		assertEquals(tree4.numberOfChildren(),2);
		assertEquals(tree4.firstChild(),tree2);
		assertEquals(tree4.lastChild(),tree3);
	}
	
	@Test
	public void testAddChildren() {
		Tree<String> tree1 = new Tree<String>("1");
		Tree<String> tree2 = new Tree<String>("2");
		Tree<String> tree3 = new Tree<String>("3");
		Tree<String> tree4 = new Tree<String>("4");
				
		tree4.addChildren(tree1,tree2);
		assertEquals(tree4.numberOfChildren(),2);
		assertEquals(tree4.firstChild(),tree1);
		assertEquals(tree4.lastChild(),tree2);
		
		exception.expect(IllegalArgumentException.class);
		tree4.addChildren(tree3,tree2);
		assertEquals(tree4.numberOfChildren(),3);
		assertEquals(tree4.firstChild(),tree1);
		assertEquals(tree4.lastChild(),tree3);
		
	}

	@Test
	public void testRemoveChildren() {
		Tree<String> child1 = new Tree<String>("1");
		Tree<String> child2 = new Tree<String>("2");
		Tree<String> child3 = new Tree<String>("3");
		Tree<String> parent = new Tree<String>("4", child1, child2, child3);
		
		exception.expect(NoSuchElementException.class);
		parent.removeChild(3);
		
		parent.removeChild(1);
		assertEquals(parent.numberOfChildren(),2);
		assertEquals(parent.firstChild(),child1);
		assertEquals(parent.lastChild(),child2);
		
	}
	
}
