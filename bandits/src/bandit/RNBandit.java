package bandit;

import domain.*;

public abstract class RNBandit extends Bandit{

    /*
     * Default constructor.  Note that the domain must still be initialized!
     */
    public RNBandit() {
    }
    
    /*
     * Constructor.
     * @param bounds An array describing the bounds of the interval in R^n.
     * Should have length 2n.
     */
    public RNBandit(double[][] bounds) {
        this.domain = new RNRange(bounds);
    }
    
    @Override
    public abstract double reward(DomainElement input);
    
    /**
     * Raw arrays are a pain. This should be used to implement a different constructor.
     * @param lower
     * @param upper
     * @return
     */
//	private static double[][] createBounds(RealVector lower, RealVector upper){
//		double[] lowerVals = lower.getValue();
//		double[] upperVals = upper.getValue();
//		int d = lowerVals.length;
//		assert(upperVals.length == d);
//		double[][] bounds = new double[d][2];
//		for (int i = 0; i<d; ++i){
//			bounds[i][0] = 0; //use lower here
//			bounds[i][1] = 1; //use upper here
//		}
//		return bounds;
//	}
}
