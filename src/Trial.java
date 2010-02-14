/*
 * Represents a trial - i.e. an algorithm running on a problem.
 */
public class Trial {
    // The algorithm to use.
    public Algorithm alg;
    
    // The problem to run on.
    public Bandit bandit;
    
    // Total number of rounds to run. 
    public int total_rounds;
    
    // TOtal number of rounds run so far.
    public int rounds;
    
    // Cumulative regret.
    public double total_regret;
    
    
    /*
     * Constructor.
     * @param alg The algorithm to use.
     * @param bandit The bandit problem to run on.
     */
    public Trial(Algorithm alg, Bandit bandit, int total_rounds) {
        // Initialize values.
        this.alg = alg;
        this.bandit = bandit;
        this.total_rounds = total_rounds;
        this.rounds = 0;
        this.total_regret = 0;
    }
    
    /*
     * Runs the Trial.
     */
    public void run() {
        for (int i = 0; i < this.total_rounds; i++) {            
            DomainElement choice = this.alg.makeChoice(); // Make the choice.
            double reward = this.bandit.getReward(choice); // Get the reward.
            double regret = this.bandit.getRewardMax() - reward; // Get regret.
            this.total_regret += regret;
            this.alg.recieveReward(reward); // Give feedback.
            this.rounds++;
            
            // Test/debug code.
            double average_regret =  this.total_regret / this.rounds;
            if (i % (this.total_rounds / 100) == 0) {
                System.out.println("Average regret after move " + (i + 1) + ": "
                        + average_regret);
            }
        }

    }
    
    
    
}