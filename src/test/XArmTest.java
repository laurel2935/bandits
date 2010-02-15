package test;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import xArm.XArm;

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
		ArrayList<Double> choice = xArm.makeChoice();
		System.out.println("choice is " + Double.toString(choice.get(0)));
	}

	@Test
	public void testReceiveReward() {
		int dimension = 1;
		XArm xArm = new XArm(dimension);
		int numIterations = 10;
		for(int i=0; i<numIterations; ++i){
			ArrayList<Double> choice = xArm.makeChoice();
			double choiceVal = choice.get(0);
			System.out.println("choice is " + Double.toString(choiceVal));
			double reward = choiceVal;
			xArm.receiveReward(reward);
		}
	}

}
