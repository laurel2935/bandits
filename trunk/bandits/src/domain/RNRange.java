package domain;

public class RNRange extends Domain {

    // List of dimensions
    private RealRange[] ranges;
    
    // Dimension
    private int dim;

    /*
     * Constructor.
     * @param bounds
     */
    public RNRange(double[] bounds) {
        // Need two numbers for each dimension
        assert(bounds.length % 2 == 0);
        this.dim = bounds.length % 2;
        // Make the ranges
        this.ranges = new RealRange[this.dim];
        for (int i = 0; i < dim; i++) {
            this.ranges[i] = new RealRange(bounds[2 * i], bounds[2 * i + 1]);
        }
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
        // TODO Auto-generated method stub
        return null;
    }

}
