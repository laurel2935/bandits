package experiments;

import algorithm.Algorithm;
import algorithm.xArm.XArm;
import bandit.ExponentialBandit;
import bandits.Trial;

public class XArmSmoothnessExperiment {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double mu = 0.5;
		double sigmaSq = 1;
		ExponentialBandit exponentialBandit = new ExponentialBandit(mu, sigmaSq);  
		
		
		// Make algorithm
		
		Algorithm alg = new XArm(1);

		// Run
		int numIterations = 10000;
		String dataDir = "experimentalResults/";
		String filename = dataDir+"xArmSmoothness_one.txt";
		
		Trial trial = new Trial(alg, exponentialBandit, numIterations, filename); 
		trial.setWriteInterval(1);
		trial.run();
	}

}
