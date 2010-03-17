package bandit;

import domain.DomainElement;
import domain.RealNumber;

public class ExponentialBandit extends RealBandit {
	private final double mu;
	private final double sigmaSq;
	public ExponentialBandit(double mu, double sigmaSq){
		super(0,1);
		this.mu = mu;
		this.sigmaSq = sigmaSq;
		this.reward_max = 1;
	}

	@Override
	public double reward(DomainElement input) {
		//ugly hack: casting...
		double val = ((RealNumber)input).getValue(); // Value of input.
		// TODO: check that x is in [0,1]
		assert( (0 <= val) && (val <= 1 ));
		return Math.exp( -Math.pow(val-mu,2)/(2*sigmaSq));
	}
}
