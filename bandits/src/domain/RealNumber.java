package domain;


/*
 * Represents a real number.
 */
public class RealNumber implements DomainElement {
    // Value of the real number.
    private double value;
    
    /*
     * Constructs a new RealNumber.
     * @param value The value of this RealNumber.
     */
    public RealNumber(double value) {
        this.value = value;
    }
    
    /*
     * Gets the value of this RealNumber.
     * @return The value of this RealNumber.
     */
    public double getValue() {
        return this.value;
    }

	@Override
	public double distanceTo(DomainElement elem) {
		if(elem instanceof RealNumber)
			return Math.abs(((RealNumber) elem).getValue()-this.getValue());
		else
			return 0;
	}    
	public String toString(){
		return value+"";
	}
}
