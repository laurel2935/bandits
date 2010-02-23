package bandit;

import domain.*;

public abstract class RNBandit extends Bandit{

    /*
     * Constructor.
     * @param bounds An array describing the bounds of the interval in R^n.
     * Should have length 2n.
     */
    public RNBandit(double[] bounds) {
        this.domain = new RNRange(bounds);
    }
    
    @Override
    public abstract double reward(DomainElement input);
}
