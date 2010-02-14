/*
 * Represents a range of real numbers.
 */
public class RealRange extends Domain {

    // Minimum and maximum of this range of real numbers.
    private double min;
    private double max;
    
    /*
     * Constructor.
     * @param min Minimum value of this range of real numbers.
     * @param max Maximum value of this range of real numbers. 
     */
    public RealRange(double min, double max) {
        super();
        this.min = min;
        this.max = max;
    }
    
    
    public boolean isIn(DomainElement input) {
        if (! (input instanceof RealNumber))
            return false;
        // Test for inclusion in this interval.
        return this.min <= ((RealNumber)input).getValue() && 
               this.max >= ((RealNumber)input).getValue();
    }
    
    public RealNumber randomElement() {
        // Just get a random number in the range.
        double val = this.rand.nextDouble() * (this.max - this.min) + this.min;
        return new RealNumber(val);
    }

    private class RealRangeCoveringOracle extends CoveringOracle{

		@Override
		public void addElement(DomainElement input, double radius) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public DomainElement getUncoveredElement() {
			// TODO Auto-generated method stub
			return null;
		}
    	
    }
    
	@Override
	public CoveringOracle getCoveringOracle() {
		return new RealRangeCoveringOracle();
	}
}
