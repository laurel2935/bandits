package domain;
import java.lang.Math;

/*
 * Represents an element of R^n.
 */
public class RealVector implements DomainElement {
    // Value of the real number.
    private double[] value;
    
    // Dimension of number
    private int dim;

    /*
     * Constructs a new RealVector.
     * @param vals The values describing this RealVector
     */
    public RealVector(double[] vals) {
        this.value = vals.clone();
        this.dim = this.value.length;
    }
    
    /*
     * Gets the value of this RealVector
     * @return The value of this RealVector.
     */
    public double[] getValue() {
        return this.value;
    }
    
    /*
     * Gets the dimension of this RealVector.
     */
    public int getDim() {
        return this.dim;
    }

	@Override
	public double distanceTo(DomainElement elem) {
		if(elem instanceof RealVector) {
		    assert(((RealVector) elem).dim == this.dim);
		    // Manhattan Distance
		    double dist = 0;
		    double[] other = ((RealVector) elem).getValue();
		    for (int i = 0; i < this.dim; i++) {
		        dist += Math.abs(this.value[i] - other[i]);
		    }
			return dist;
		}
		else
			return 0; // ?? Not sure why this is here, carrying over...
	}    
	public String toString(){
		return value+"";
	}
}
