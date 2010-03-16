package bandit;

import domain.DomainElement;
import domain.RNRange;
import domain.RealVector;
/*
 * Represents a bandit where the reward is maximized at a specific point in R^n, 
 * and decreases uniformly with distance.
 */
public class RNDistanceBandit extends RNBandit{
	// maximum value
	RealVector center;
	public RNDistanceBandit(RealVector point, double[][] bounds){
		super(bounds);
		if(!this.domain.isIn(point))
			throw new Error("Point is not within bounds");
		if(point.getDim()!=((RNRange) this.domain).getDimension())
			throw new Error("Dimension mismatch");
		this.center = point;
	}
	@Override
	public double reward(DomainElement input) {
		// This function is a max when the distance is 0. Elsewhere, it decreases according to distance.
		// Since distance is always nonnegative, this will be inbetween 0 and 1.
		return 1./(1+input.distanceTo(center));
	}

}
