package experiments;

import algorithm.Algorithm;
import algorithm.discrete.DiscretizedEpsilonGreedy;
import algorithm.xArm.XArm;
import bandit.ExponentialBandit;
import bandits.Trial;
import domain.RealRange;

public class XArmSmoothnessExperiment {

	   /**
     * Raw arrays are a pain. This should be used to implement a different constructor.
     * @param lower
     * @param upper
     * @return
     */
	private static double[][] createBounds(int d){
//		double[] lowerVals = lower.getValue();
//		double[] upperVals = upper.getValue();
//		int d = lowerVals.length;
//		assert(upperVals.length == d);
		double[][] bounds = new double[d][2];
		for (int i = 0; i<d; ++i){
			bounds[i][0] = 0;
			bounds[i][1] = 1;
		}
		return bounds;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double[] sigmaSqVals = {1.1, 0.1, 0.01, 0.001, 0.0001};
		int numIterations = 10000;
		int d = 1; //dimensionality
		
		for(int i= 0; i<sigmaSqVals.length; ++i){

			double mu = 0.271;
			double sigmaSq = sigmaSqVals[i];
			
			ExponentialBandit exponentialBandit = new ExponentialBandit(mu, sigmaSq);  

			// Make algorithm

			Algorithm xarm = new XArm(1);
			
			RealRange range = new RealRange(0,1);
			Algorithm deg = new DiscretizedEpsilonGreedy(range, 1000*d);
			// Run
			
			String indexString = Integer.toString(i);
			String dataDir = "experimentalResults/";
			String filename = dataDir+"greedy_smooth_"+indexString+".txt";

			Trial trial = new Trial(deg, exponentialBandit, numIterations, filename); 
			trial.setWriteInterval(1);
			trial.run();
			
			// -----------------------------------------------
			
			String xArmfilename = dataDir+"xArm_smooth_"+indexString+".txt";

			Trial trialXarm = new Trial(xarm, exponentialBandit, numIterations, xArmfilename); 
			trialXarm.setWriteInterval(1);
			trialXarm.run();
		}
	}
}
