package algorithm.discrete;

import java.lang.Math;
import java.util.Random;

import domain.Domain;
import domain.DomainElement;

import algorithm.Algorithm;

/*
 * Implements the Exp3 algorithm after discretizing the domain.
 * Here we use gamma = num_arms / total_round.
 * NOTE: ASSUMES REWARDS ARE ALWAYS IN [0,1], PROBABLY. MAYBE JUST BOUNDED.
 * TODO: FIX THIS.
 */
public class DiscretizedExp3 extends Algorithm {

    // Number of arms
    private int num_arms;

    // Input value of each arm
    private DomainElement[] vals;
    
    // Weight on each arm
    private double[] weights;
    
    // Probability of choosing each arm
    private double[] probs;
    
    // Total number of plays made so far
    private int total_plays;
    
    // Current arm in action.
    private int current_arm;
    
    // Random number generator
    private Random rand;
    
    

    /*
     * Constructor.
     * @param domain The domain this algorithm will be working over.
     * @param num_arms The number of arms to use.
     */
    public DiscretizedExp3(Domain domain, int num_arms) {
        // Initialize some values
        this.domain = domain;
        this.num_arms = num_arms;
        this.vals = new DomainElement[this.num_arms];
        this.weights = new double[this.num_arms];
        this.probs = new double[this.num_arms];
        this.total_plays = 0;
        this.current_arm = -1;
        this.rand = new Random();
        
        
        // Discretize domain.
        for (int i = 0; i < this.num_arms; i++) {
            this.vals[i] = this.domain.randomElement();
            this.weights[i] = 1;
        }
    }

    
    
    @Override
    public DomainElement makeChoice() {
        int arm_choice = -1;
                
        // Get sum of weights
        double weight_sum = 0;
        for (int i = 0; i < this.num_arms; i++) {
            weight_sum += this.weights[i];
        }

        // Assign probabilities
        // Probability of random play.
        double gamma = Math.min(1, (double) this.num_arms /
                                   (this.total_plays + 1));
        
        for (int i = 0; i < this.num_arms; i++) {
            this.probs[i] = (1 - gamma) * this.weights[i] / weight_sum +
                            gamma / this.num_arms;
        }

        // Now choose arm
        double p = this.rand.nextDouble();
        double sum_p = 0;
        for (int i = 0; i < this.num_arms; i++) {
            sum_p += this.probs[i];
            if (sum_p > p) {
                arm_choice = i;
                break;
            }
        }
        
        // Now we have the arm to use, so make the choice.
        this.current_arm = arm_choice;
        DomainElement choice = this.vals[arm_choice];
        return choice;
    }

    
    @Override
    public void recieveReward(double reward) {
        // Adjust weights
        double gamma = Math.min(1, (double) this.num_arms /
                                   (this.total_plays + 1));
        this.weights[current_arm] *=
            Math.exp((gamma / this.num_arms) *
                     (reward / this.probs[this.current_arm]));
        this.total_plays++;
    }
}