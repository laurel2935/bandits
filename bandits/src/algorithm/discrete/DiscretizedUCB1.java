package algorithm.discrete;
import domain.Domain;
import domain.DomainElement;
import algorithm.Algorithm;

/*
 * Implements the UCB1 algorithm after discretizing the domain.
 */
public class DiscretizedUCB1 extends Algorithm {

    // Number of arms
    private int num_arms;

    // Input value of each arm
    private DomainElement[] vals;

    // Times each arm has been played
    private int[] times_played;
    
    // Sum of rewards on each arm
    private double[] arm_sums;
    
    // Total number of plays made so far
    private int total_plays;
    
    // Current arm in action.
    private int current_arm;

    /*
     * Constructor.
     * @param domain The domain this algorithm will be working over.
     * @param num_arms The number of arms to use.
     */
    public DiscretizedUCB1(Domain domain, int num_arms) {
        // Initialize some values
        this.domain = domain;
        this.num_arms = num_arms;
        this.vals = new DomainElement[this.num_arms];
        this.times_played = new int[this.num_arms];
        this.arm_sums = new double[this.num_arms];
        this.total_plays = 0;
        this.current_arm = -1;
        
        // Discretize domain.
        for (int i = 0; i < this.num_arms; i++) {
            this.vals[i] = this.domain.randomElement();
            this.arm_sums[i] = 0;
            this.times_played[i] = 0;
        }
    }

    
    
    @Override
    public DomainElement makeChoice() {
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
        
        // Now we have the arm to use, so make the choice.
        this.current_arm = arm_choice;
        DomainElement choice = this.vals[arm_choice];
        return choice;
    }

    
    @Override
    public void recieveReward(double reward) {
        // Merely record the reward and increment counters appropriately. 
        this.arm_sums[this.current_arm] += reward;
        this.times_played[this.current_arm]++;
        this.total_plays++;
    }
}