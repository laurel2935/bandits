package test;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import algorithm.xArm.XArm;
import algorithm.xArm.XArmRn;
import domain.RealVector;

public class XArmRnTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testXArm() {
		//just test that nothing crashes...
		int dimension = 5;
		XArmRn xArmRn = new XArmRn(dimension);
	}
	
	@Test
	public void testMakeChoice() {
		int dimension = 1;
		XArmRn xArmRn = new XArmRn(dimension);
		//ArrayList<Double> choice = xArm.makeChoice();
		//System.out.println("choice is " + Double.toString(choice.get(0)));
		RealVector choice = xArmRn.makeChoice();
		System.out.println("choice is " + displayRealVector(choice));
	}

	@Test
	public void testMakeChoice2d() {
		int dimension = 2;
		XArmRn xArmRn = new XArmRn(dimension);
		//ArrayList<Double> choice = xArm.makeChoice();
		//System.out.println("choice is " + Double.toString(choice.get(0)));
		RealVector choice = xArmRn.makeChoice();
		System.out.println("choice is " + displayRealVector(choice));
	}
	
	@Test
	public void testReceiveReward() {
		/*
		 * This isn't a test per se, but convergence to the "target" can be 
		 * observed.
		 */
		int dimension = 3;
		XArmRn xArmRn = new XArmRn(dimension);
		int numIterations = 1000;
		for(int i=0; i<numIterations; ++i){
			RealVector choice = xArmRn.makeChoice();
			System.out.println("choice is " + displayRealVector(choice));
			// compute reward for choice, say, as 1 - distance to the point (0.2,0.3,0.4)
			double[] target = {0.7, 0.9, 0.8};
			RealVector targetRv = new RealVector(target);
			double reward = coerce(1.0 - choice.distanceTo(targetRv));
			xArmRn.recieveReward(reward);
		}
	}
	
	/**
	 * 
	 * @param val
	 * @return
	 */
	private double coerce(double val){
		if (val > 1)
			return 1;
		else if (val < 0)
			return 0;
		else
			return val;
	}
	
	private String displayRealVector(RealVector rv){
		String disp = "[";
		double[] vals = rv.getValue();
		for(int i=0; i< vals.length;i++){
			disp += Double.toString(vals[i]) + " ";
		}
		disp += "]";
		return disp;
	}
	
	
	
}
