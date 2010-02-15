package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import algorithm.xArm.CubicCoverNode;


public class CubicCoverNodeTest {
	
	CubicCoverNode root;
	CubicCoverNode nodeA;
	//CubicCoverNode nodeB;
	
	ArrayList<Double> lowerBounds; 
	ArrayList<Double> upperBounds; 
	
	@Before
	public void setUp() throws Exception {
		final int dimension = 3;
		
		lowerBounds = new ArrayList<Double>(dimension);
		lowerBounds.add(0, 0.0);
		lowerBounds.add(1, 0.0);
		lowerBounds.add(2, 0.0);
		
		upperBounds = new ArrayList<Double>(dimension);
		upperBounds.add(0,1.0);
		upperBounds.add(1,1.0);
		upperBounds.add(2,1.0);
		
		root = new CubicCoverNode(3, lowerBounds, upperBounds, null);
		nodeA = new CubicCoverNode(3, lowerBounds, upperBounds, root);
		//nodeB = new CubicCoverNode(3, lowerBounds, upperBounds, nodeA);
		//nodeA.setLeftChild(nodeB);
		
	}
		
	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * Verify initial values
	 */
	@Test
	public void testCubicCoverNode() {
		//root
		assertEquals(null, root.getParent());
		assertEquals(null, root.getLeftChild());
		assertEquals(null, root.getRightChild());
		assertEquals(lowerBounds, root.getLowerBounds());
		assertEquals(upperBounds, root.getUpperBounds());
		
		// nodeA
		assertEquals(root, nodeA.getParent());
		assertEquals(null, nodeA.getLeftChild());
		assertEquals(null, nodeA.getRightChild());
		assertEquals(lowerBounds, nodeA.getLowerBounds());
		assertEquals(upperBounds, nodeA.getUpperBounds());
	}

	@Test
	public void testSetLeftChild() {
		//root
		root.setLeftChild(nodeA);
		assertEquals(nodeA, root.getLeftChild());
		assertEquals(null, root.getRightChild());
		
		//nodeA
	}

	@Test
	public void testSetRightChild() {
		//root
		root.setRightChild(nodeA);
		assertEquals(nodeA, root.getRightChild());
		assertEquals(null, root.getLeftChild());
		
		//nodeA
	}

	@Test
	public void testGetLowerBounds() {
		//root
		assertEquals(lowerBounds, root.getLowerBounds());
		//nodeA
	}

	@Test
	public void testGetUpperBounds() {
		//root
		assertEquals(upperBounds, root.getUpperBounds());
	}

	@Test
	public void testGetParent() {
		//root
		assertEquals(null, root.getParent());
		//nodeA
		assertEquals(root, nodeA.getParent());
	}

	@Test
	public void testGetLeftChild() {
		assertEquals(null, root.getLeftChild());
		root.setLeftChild(nodeA);
		assertEquals(null, root.getRightChild());
		assertEquals(nodeA, root.getLeftChild());
	}

	@Test
	public void testGetRightChild() {
		assertEquals(null, root.getRightChild());
		root.setRightChild(nodeA);
		assertEquals(null, root.getLeftChild());
		assertEquals(nodeA, root.getRightChild());
	}

}
