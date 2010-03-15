package test;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import algorithm.xArm.XArm;

/*
 * This should probably be retired, in favor of using the 
 * one dimensional XArmRn
 */
public class XArmTest {

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
		XArm xArm = new XArm(dimension);
	}

	@Test
	public void testMakeChoice() {
		int dimension = 1;
		XArm xArm = new XArm(dimension);
		//ArrayList<Double> choice = xArm.makeChoice();
		//System.out.println("choice is " + Double.toString(choice.get(0)));
		double choice = xArm.makeChoice().getValue();
		System.out.println("choice is" + choice);
		
	}

	@Test
	public void testReceiveReward() {
		int dimension = 1;
		XArm xArm = new XArm(dimension);
		int numIterations = 100;
		for(int i=0; i<numIterations; ++i){
			//ArrayList<Double> choice = xArm.makeChoice();
			//double choiceVal = choice.get(0);
			double choiceVal = xArm.makeChoice().getValue();
			System.out.println("choice is " + Double.toString(choiceVal));
			double reward = choiceVal;
			xArm.recieveReward(reward);
		}
	}
}
