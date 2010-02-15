package test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import xArm.CubicCoverNode;
import xArm.CubicCoverTree;

public class CubicCoverTreeTest {
	// for comparing doubles
	double delta = 0.0000001;
	
	int dimension = 3;
	CubicCoverTree treeA;
	
	double nu =Math.sqrt(dimension)/2;
	double rho = Math.pow(2.0, -1.0 / dimension);
	
	@Before
	public void setUp() throws Exception {
		treeA  = new CubicCoverTree(dimension);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCubicCoverTree() {
		//test some initial values
		assertEquals(0, treeA.size());
		
	}

	@Test
	public void testSize() {
		//initial value
		assertEquals(0, treeA.size());
		
		CubicCoverNode newNode;
		int numIterations = 100;
		for(int i=1; i<=numIterations; ++i){
			newNode = treeA.pick();
			treeA.update(newNode, 0.5);
			assertEquals(i, treeA.size());
		}
	}

	@Test
	public void testGetEmpiricalAverage() {
		
		CubicCoverNode root = treeA.pick();
		treeA.update(root, 0.7);
		assertEquals(0.7, treeA.getEmpiricalAverage(root), delta);
		
		CubicCoverNode newNode = treeA.pick();
		treeA.update(newNode, 0.2);
		assertEquals(0.45, treeA.getEmpiricalAverage(root),delta);
	}

	@Test
	public void testGetUpperConfidenceBound() {
		CubicCoverNode root = treeA.pick();
		treeA.update(root, 0.7);
		double expectedU = 0.7 + nu;
		assertEquals(expectedU, treeA.getUpperConfidenceBound(root), delta);
		
		CubicCoverNode newNode = treeA.pick();
		treeA.update(newNode, 0.2);
		expectedU = 0.45 + Math.sqrt(2*Math.log(2) / 2) + nu;
		assertEquals(expectedU, treeA.getUpperConfidenceBound(root), delta);
	}

}
