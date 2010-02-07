/*
 * Represents an infinite-bandit problem whose domain is some interval of the
 * real numbers.
 */
public abstract class RealBandit {

    // A valid input must lie in the range [domain_min, domain_max]
    protected double domain_min;
    protected double domain_max;

    // The maximum expected reward.  Used to calculate regret.
    protected double reward_max;
    
    /*
     * Given an input, returns the reward associated with it.
     * @param input A real-valued input.
     * @return The reward associated with the input.
     * @raises IllegalArgumentException
     */
    public double getReward(double input) {
        if (!validInput(input)) {
            throw new IllegalArgumentException("Input " + input + " must be in "
                + "the range [" + domain_min + ", " + domain_max + "].");
        }
        return reward(input);
        
    }
    
    /*
     * Given an input, calculates the expected regret associated with that
     * input.
     * @param input A real-valued input.
     * @return The regret associated with the input.
     */
    public double getRegret(double input) {
        return this.reward_max - getReward(input);
    }
    
    /*
     * The actual cost function.
     * @param input A valid real-valued input.
     * @return The reward associated with the input.
     */
    abstract public double reward(double input);
    
    /*
     * Checks whether a given input is valid or not.
     * @param input A real-valued input.
     * @return True if the input is in the permissible range, False otherwise.
     */
    private boolean validInput(double input) {
        return this.domain_min <= input && input <= this.domain_max;
    }


    /*
     * Gets the minimum domain value.
     * @return The minimum domain value.
     */
    public double getDomainMin() {
        return domain_min;
    }


    /*
     * Gets the maximum domain value.
     * @return The maximum domain value.
     */
    public double getDomainMax() {
        return domain_max;
    }
    
    /*
     * Sets the max reward.
     * @param max_reward The maximum possible reward.
     */
    public void setRewardMax(double max_reward) {
        this.reward_max = max_reward;
    }

}
