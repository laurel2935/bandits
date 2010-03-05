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
}
