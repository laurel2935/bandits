package experiments;

import algorithm.Algorithm;
import algorithm.xArm.XArmRn;
import bandit.RNDistanceBandit;
import bandits.Trial;
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
	 * @param args
	 */
	public static void main(String[] args) {
		int minDimension = 6;
		int maxDimension = 10;
		for(int d =minDimension; d <= maxDimension; d++){
			RNDistanceBandit rnDistanceBandit = initializeBandit(d);

			// Make algorithm
			Algorithm alg = new XArmRn(d);

			// Run
			int numIterations = 10000;
			String dimensionString = Integer.toString(d);
			String dataDir = "experimentalResults/";
			String filename = dataDir+"xArmRn_dimensionality_"+dimensionString+".txt";

			Trial trial = new Trial(alg, rnDistanceBandit, numIterations, filename); 
			trial.setWriteInterval(1);
			trial.run();
		}
	}
}
