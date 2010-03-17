package experiments;

import algorithm.Algorithm;
import algorithm.discrete.DiscretizedEpsilonGreedy;
import algorithm.xArm.XArmRn;
import bandit.RNDistanceBandit;
import bandits.Trial;
import domain.RNRange;
import domain.RealVector;

/**
 * Evaluate the performance of XArmRn across different dimensionalities.
 * @author mfaulk
 *
 */
public class XArmRnDimensionality {
	
	/**
	 * 
	 * @param d Dimensionality
	 * @return
	 */
	private static RNDistanceBandit initializeBandit(int d){
		// initialize bounds to [0,...,0], [1,...,1]
		double[][] bounds = new double[d][2];
		for (int i = 0; i<d; ++i){
			bounds[i][0] = 0;
			bounds[i][1] = 1;
		}
		//construct the center or "target point" for the distance bandit
		double[] vals = new double[d];
		for (int i = 0; i<d; ++i){
			vals[i] = 0.5;
		}
		RealVector center = new RealVector(vals);
		RNDistanceBandit rnDistanceBandit = new RNDistanceBandit(center, bounds);
		return rnDistanceBandit;
	}
	
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
		int minDimension = 1;
		int maxDimension = 5;
		int numIterations = 10000;
		for(int d =minDimension; d <= maxDimension; d++){
			
			RNDistanceBandit rnDistanceBandit = initializeBandit(d);

			// Make algorithm

			RNRange range = new RNRange(createBounds(d));
			Algorithm alg = new DiscretizedEpsilonGreedy(range, 1000*d);
			// Run
			String dimensionString = Integer.toString(d);
			String dataDir = "experimentalResults/";
			String filename = dataDir+"greedy_dim_"+dimensionString+".txt";

			Trial trial = new Trial(alg, rnDistanceBandit, numIterations, filename); 
			trial.setWriteInterval(1);
			trial.run();
			
			// ------------------------------------------------
			Algorithm xarm = new XArmRn(d);
			
			String filenameXarm = dataDir+"xArm_dim_"+dimensionString+".txt";

			Trial trialXarm = new Trial(xarm, rnDistanceBandit, numIterations, filenameXarm); 
			trialXarm.setWriteInterval(1);
			trialXarm.run();
			
			
			System.out.println("-------- "+Integer.toString(d)+" : done ---------------");
		}
	}
}
