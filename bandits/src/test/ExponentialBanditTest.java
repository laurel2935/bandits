package test;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bandit.ExponentialBandit;
import domain.RealNumber;
import static org.junit.Assert.*;

public class ExponentialBanditTest {
	private final double delta = 0.001;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testConstructor(){
		double mu = 1/2;
		double sigmaSq = 1;
		ExponentialBandit expBandit = new ExponentialBandit(mu, sigmaSq);
	}

	@Test
	public void testMeanReward(){
		double mu = 1/2;
		double sigmaSq = 1;
		ExponentialBandit expBandit = new ExponentialBandit(mu, sigmaSq);
		RealNumber mean = new RealNumber(mu);
		double meanReward = expBandit.reward(mean);
		assertEquals(1,meanReward,delta);
	}
	
	@Test
	public void testReward(){
		double mu = 1/2;
		double sigmaSq = 1;
		ExponentialBandit expBandit = new ExponentialBandit(mu, sigmaSq);
		double testPoint = 0.2;
		RealNumber real = new RealNumber(testPoint);
		double meanReward = expBandit.reward(real);
		double desiredReward = Math.exp(- (testPoint - mu)*(testPoint - mu) / (2*sigmaSq) );
		assertEquals(desiredReward,meanReward,delta);
	}
}
