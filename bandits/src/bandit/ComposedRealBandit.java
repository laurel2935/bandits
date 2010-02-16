package bandit;
import domain.Domain;
import domain.DomainElement;
import domain.RealNumber;
import domain.RealRange;


/*
 * Gives a reward function that is the composition of two input reward functions
 * (bandits).
 * NOTE: THIS REQUIRES THAT BAND2 OUTPUTS VALUES IN THE APPROPRIATE RANGE FOR
 * BAND1. 
 */
public class ComposedRealBandit extends RealBandit {

    // Outer Bandit
    private Bandit band1;
    
    // Inner Bandit
    private Bandit band2;

    /*
     * Constructor.  Makes band1(band2(x))
     * @param band The noiseless bandit problem.
     */
    public ComposedRealBandit(Bandit band1, Bandit band2) {
        // The
        super(((RealRange)(band2.domain)).getMin(),
              ((RealRange)(band2.domain)).getMax());
        this.band1 = band1;
        this.band2 = band2;
        // Keep max reward from band1
        this.reward_max = this.band1.getRewardMax();
    }
    
    
    @Override
    public double reward(DomainElement input) {
        // Get noiseless result
        double result = this.band2.reward(input);
        return band1.getReward(new RealNumber(result));
    }
    
    /*
     * Sets the domain of this function.  Might be necessary with composition.
     * @param d The new domain
     */
    public void setDomain(Domain d) {
        this.domain = d;
    }


}
