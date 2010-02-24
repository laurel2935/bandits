package domain;

import java.util.Random;

public class RNRange extends Domain {

    // List of dimensions
    private RealRange[] ranges;
    
    // Dimension
    private int dim;
    
    // Random number generator
    private Random rand;

    /*
     * Constructor.
     * @param bounds
     */
    public RNRange(double[][] bounds) {
        // Need two numbers for each dimension
        assert(bounds.length % 2 == 0);
        this.dim = bounds.length;
        // Make the ranges
        this.ranges = new RealRange[this.dim];
        for (int i = 0; i < dim; i++) {
            this.ranges[i] = new RealRange(bounds[i][0], bounds[i][1]);
        }
        this.rand = new Random();
    }
    
    @Override
    public CoveringOracle getCoveringOracle() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isIn(DomainElement input) {
        // If not a RealVector, can't possibly be in this range.
        if (! (input instanceof RealVector))
            return false;

        double[] inputVals = ((RealVector) input).getValue();
        if (! (inputVals.length == this.dim))
            return false;
        // Test for inclusion in each interval.
        for (int i = 0; i < this.dim; i++) {
            if (! this.ranges[i].isIn(new RealNumber(inputVals[i]))) {
                return false;
            }
        }
        // In each range.
        return true;
    }

    @Override
    public DomainElement randomElement() {
        double[] vals = new double[this.dim];
        // Put a random value in each entry
        for (int i = 0; i < this.dim; i++) {
            double min = this.ranges[i].getMin();
            double max = this.ranges[i].getMax();
            double val = (max - min) * this.rand.nextDouble() + min;
            vals[i] = val;
        }
        return new RealVector(vals);
    }

}
