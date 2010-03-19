package bandit;
import domain.Domain;
import domain.DomainElement;


/*
 * Represents a multi-player bandit problem, abstractly.
 */
public abstract class MultiplayerBandit {

    // What domain this problem is over for each player.
    public Domain[] domains;

    // The maximum expected reward.  Used to calculate regret, if necessary.
    public double[] reward_max;
    
    // Number of players
    public int players;
    
    /*
     * Constructor.
     * @param domains An array of domains listing which domain each player plays
     *        on.
     */
    public MultiplayerBandit(Domain[] domains) {
        this.domains = domains;
        this.players = domains.length;
        this.reward_max = new double[this.players];
    }

    
    /*
     * Given an input, returns the reward associated with it.
     * @param input An array giving the input for each player.
     * @return The rewards associated with the input.
     * @raises IllegalArgumentException
     */
    public double[] getReward(DomainElement[] input) {
        if (!validInput(input)) {
            throw new IllegalArgumentException("Invalid input " + input);
        }
        return reward(input);
    }


    /*
     * The actual cost function.
     * @param input A valid input array.
     * @return The reward associated with the input.
     */
    abstract public double[] reward(DomainElement[] input);
        
    /*
     * Checks whether a given input is valid or not.
     * @param input An input for each user, put in an array.
     * @return True if the input is in the permissible domain, False otherwise.
     */
    private boolean validInput(DomainElement[] input) {
        // Must have same length as the number of players
        if (input.length != domains.length)
            return false;
        
        // The input must be valid component-wise.
        for (int i = 0; i < domains.length; i++) {
            if (!domains[i].isIn(input[i]))
                return false;
        }

        // If no failures, all good
        return true;
    }
        
    /*
     * Sets the max rewards.
     * @param max_reward The maximum possible reward.
     */
    public void setRewardMax(double[] max_rewards) {
        this.reward_max = max_rewards;
    }

    /*
     * Sets the max reward in some index.
     * @param player Index of player to set max reward for.
     * @param max_reward The maximum possible reward for this player.
     */
    public void setRewardMax(int player, double max_reward) {
        this.reward_max[player] = max_reward;
    }
    
    /*
     * Gets the max rewards.
     * @return The maximum possible reward.
     */
    public double[] getRewardMax() {
        return this.reward_max;
    }
    
    /*
     * Gets the domains.
     * @return The domains of this bandit problem.
     */
    public Domain[] getDomain() {
        return this.domains;
    }

}