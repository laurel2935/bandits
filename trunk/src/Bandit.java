
/*
 * Represents a bandit problem, abstractly.
 */
public abstract class Bandit {

    // What domain this problem is over.
    public Domain domain;

    // The maximum expected reward.  Used to calculate regret, if necessary.
    public double reward_max;

    
    /*
     * Given an input, returns the reward associated with it.
     * @param input An element of the domain
     * @return The reward associated with the input.
     * @raises IllegalArgumentException
     */
    public double getReward(DomainElement input) {
        if (!validInput(input)) {
            throw new IllegalArgumentException("Invalid input " + input);
        }
        return reward(input);
    }


    /*
     * The actual cost function.
     * @param input A valid real-valued input.
     * @return The reward associated with the input.
     */
    abstract public double reward(DomainElement input);
        
    /*
     * Checks whether a given input is valid or not.
     * @param input A real-valued input.
     * @return True if the input is in the permissible range, False otherwise.
     */
    private boolean validInput(DomainElement input) {
        return domain.isIn(input);
    }
        
    /*
     * Sets the max reward.
     * @param max_reward The maximum possible reward.
     */
    public void setRewardMax(double max_reward) {
        this.reward_max = max_reward;
    }

    /*
     * Gets the max reward.
     * @return The maximum possible reward.
     */
    public double getRewardMax() {
        return this.reward_max;
    }
    

}