
import java.util.Random;
import java.lang.Math;
import java.lang.Double;

/*
 * Implements the UCB1 algorithm on the real line after discretizing the domain.
 */
public class DiscretizedUCB1 {

    // The domain of the reward function is [domain_min, domain_max]  
    private double domain_min;
    private double domain_max;

    // Number of arms
    private int num_arms;
    
    // Input value of each arm
    private double[] vals;
    
    // Times each arm has been played
    private int[] times_played;
    
    // Sum of rewards on each arm
    private double[] arm_sums;
    
    // Total number of plays made so far
    private int total_plays;
    
    // The problem we're solving
    private RealBandit problem;
    
    // Cumulative regret
    private double total_regret;
    

    /*
     * Constructor.
     * @param domain_min Minimum of the domain.
     * @param domain_max Maximum of the domain.
     * @param num_arms How many arms to use.
     */
    public DiscretizedUCB1(RealBandit problem, int num_arms) {
        // Initialize some values
        this.domain_min = problem.getDomainMin();
        this.domain_max = problem.getDomainMax();
        this.problem = problem;
        this.num_arms = num_arms;
        this.vals = new double[this.num_arms];
        this.times_played = new int[this.num_arms];
        this.arm_sums = new double[this.num_arms];
        this.total_plays = 0;
        this.total_regret = 0;
        
        Random rand = new Random();
        // Initialize array values
        for (int i = 0; i < this.num_arms; i++) {
            this.vals[i] = rand.nextDouble() *
                (this.domain_max - this.domain_min) + this.domain_min;
            this.arm_sums[i] = 0;
            this.times_played[i] = 0;
        }
    }


    /*
     * Makes a move.
     */
    public void makeMove() {
        int arm_choice = -1;
        
        // Check if we're still in the initial stage.
        if (this.total_plays < this.num_arms) {
            arm_choice = this.total_plays;
        }

        // Otherwise, figure out the best arm to use
        else {
            double best_val = Double.NEGATIVE_INFINITY;
            for (int i = 0; i < this.num_arms; i++) {
                double average = this.arm_sums[i] / this.times_played[i];
                double val = average + Math.sqrt(2 * Math.log(this.total_plays)
                        / this.times_played[i]);
                if (val > best_val) {
                    arm_choice = i;
                    best_val = val;
                }
            }
        }
        
        // Now we have the arm to use, so make the move.
        double choice = this.vals[arm_choice];
        this.times_played[arm_choice]++;
        double reward = this.problem.getReward(choice);
        this.arm_sums[arm_choice] += reward;
        this.total_regret += this.problem.getRegret(choice);
        this.total_plays++;
    }

    /*
     * Gets the total regret so far.
     * @return The total regret so far.
     */
    public double getTotalRegret() {
       return this.total_regret; 
    }
    
    /*
     * Gets the average regret so far.
     * @return The average regret so far.
     */
    public double getAverageRegret() {
        return this.total_regret / this.total_plays;
    }
}