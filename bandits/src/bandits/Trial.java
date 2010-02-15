package bandits;
import java.io.FileWriter;
import java.io.IOException;

import domain.DomainElement;

import bandit.Bandit;

import algorithm.Algorithm;

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
    
    // File writer, if needed.
    public FileWriter writer;
    
    // Determines whether we should write to file.
    boolean toFile;
    
    // Determines how often to log to file
    int writeInterval;
    
    
    /*
     * Constructor.
     * @param alg The algorithm to use.
     * @param bandit The bandit problem to run on.
     * @param total_rounds Number of rounds to run the trial.
     */
    public Trial(Algorithm alg, Bandit bandit, int total_rounds) {
        // Initialize values.
        this.alg = alg;
        this.bandit = bandit;
        this.total_rounds = total_rounds;
        this.rounds = 0;
        this.total_regret = 0;
        this.toFile = false;
    }
    
    /*
     * Constructor with filename.
     * @param alg The algorithm to use.
     * @param bandit The bandit problem to run on.
     * @param total_rounds Number of rounds to run the trial.
     * @param filename Name of file to write to.
     */
    public Trial(Algorithm alg, Bandit bandit, int total_rounds,
                 String filename) {
        // Initialize values.
        this.alg = alg;
        this.bandit = bandit;
        this.total_rounds = total_rounds;
        this.rounds = 0;
        this.total_regret = 0;
        // Set up file writer.
        try {
            this.writer = new FileWriter(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.toFile = true;
        this.writeInterval = 1;
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
            double average_regret =  this.total_regret / this.rounds;

            // Write to file, if necessary
            if (toFile && (rounds % writeInterval == 0)) {
                try {
                    writer.write("" + this.rounds + " " + average_regret +
                                 "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            // Test/debug code.
            if (i % (Math.max(this.total_rounds / 100,1)) == 0) {
                System.out.println("Average regret after move " + (i + 1) + ": "
                        + average_regret);
            }
        }
        
        // Finish writing to file, if necessary.
        if (toFile) {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    
    /*
     * Sets writeInterval.
     * @param newInterval The new writing interval
     */
    void setWriteInterval(int newInterval) {
        this.writeInterval = newInterval;
    }
    
    
    
}