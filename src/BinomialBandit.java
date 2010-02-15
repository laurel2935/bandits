
import java.util.Random;

/*
 * Represents a bandit problem with a binomial reward function.
 * The probability of being 1 is given by some input bandit function.
 * MAPS VALUES NOT IN [0,1] TO VALUES IN [0,1]
 */
public class BinomialBandit extends RealBandit {

    // Bandit to modify.
    private Bandit band;
        
    // Random number generator
    private Random rand;
    
    /*
     * Constructor.
     * @param band The noiseless bandit problem.
     */
    public BinomialBandit(Bandit band) {
        // Defined over the same domain.
        super(((RealRange)(band.domain)).getMin(),
              ((RealRange)(band.domain)).getMax());
        this.band = band;
        this.rand = new Random();
        // Keep domain and max reward from input bandit
        this.reward_max = this.band.getRewardMax();
    }
    
    
    @Override
    public double reward(DomainElement input) {
        // Get noiseless result
        double result = band.reward(input);
        double p = this.rand.nextDouble();
        if (p < result)
            return 1;
        else
            return 0;
    }

}
