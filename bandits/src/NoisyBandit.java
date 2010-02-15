
import java.util.Random;

/*
 * Represents a bandit problem with a noisy reward function.
 * Here noise is represented as just multiplying the non-noisy result by a
 * random number.
 */
public class NoisyBandit extends Bandit {

    // Bandit to modify.
    private Bandit band;
    
    // Minimum multiplier
    private double min_mult;
    
    // Maximum multiplier
    private double max_mult;
    
    // Random number generator
    private Random rand;
    
    /*
     * Constructor.
     * @param band The noiseless bandit problem.
     * @param min_mult Minimum multiplier.
     * @param max_mult Maximum multiplier.
     */
    public NoisyBandit(Bandit band, double min_mult, double max_mult) {
        this.band = band;
        this.min_mult = min_mult;
        this.max_mult = max_mult;
        this.rand = new Random();
        // Keep domain and max reward from input bandit
        this.domain = this.band.getDomain();
        // Scale max reward according to multipliers
        this.reward_max = this.band.getRewardMax() *
                          (this.max_mult + this.min_mult) / 2;
    }
    
    
    @Override
    public double reward(DomainElement input) {
        // Get noiseless result
        double result = band.reward(input);
        double multiplier = rand.nextDouble() * (this.max_mult - this.min_mult)
                          + this.min_mult;
        return result * multiplier;
    }

}
