
/*
 * A bandit problem on [0,1] with a constant reward function.
 */
public class ConstantRealBandit extends RealBandit {

    // The constant that the reward function is equal to.
    private double value;
    
    /*
     * Constructor.
     * @param value The constant that this bandit problem is equal to.
     */
    public ConstantRealBandit(double value) {
        this.domain_min = 0;
        this.domain_max = 1;
        this.reward_max = value;
        this.value = value;
    }
    
    @Override
    public double reward(double input) {
        return this.value;
    }

}
