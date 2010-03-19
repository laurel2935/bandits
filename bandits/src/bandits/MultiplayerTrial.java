package bandits;
import java.io.FileWriter;
import java.io.IOException;
import domain.DomainElement;
import bandit.MultiplayerBandit;
import algorithm.Algorithm;

/*
 * Represents a trial - i.e. an algorithm running on a problem.
 */
public class MultiplayerTrial {
	//Debug flag to toggle debug features
	private static final boolean DEBUG = true;
	
    // The algorithm to use.
    public Algorithm[] algs;
    
    // The problem to run on.
    public MultiplayerBandit bandit;
    
    // Total number of rounds to run. 
    public int total_rounds;
    
    // Total number of rounds run so far.
    public int rounds;
    
    // Cumulative regret.
    public double[] total_regret;
    
    // File writers, if needed.
    public FileWriter[] writers;
    
    // Determines whether we should write to file.
    boolean[] toFile;
    
    // Determines how often to log to file
    int writeInterval;
    
    // Number of players
    public int players;
    
    
    /*
     * Constructor.
     * @param algs The algorithms to use.
     * @param bandit The bandit problem to run on.
     * @param total_rounds Number of rounds to run the trial.
     */
    public MultiplayerTrial(Algorithm[] algs, MultiplayerBandit bandit,
                            int total_rounds) {
        // Initialize values.
        this.algs = algs;
        this.bandit = bandit;
        this.players = this.bandit.players;
        assert(this.players == algs.length);
        this.total_rounds = total_rounds;
        this.rounds = 0;
        this.total_regret = new double[this.players];
        this.toFile = new boolean[this.players];
        for (int i = 0; i < this.players; i++) {
            this.total_regret[i] = 0;
            this.toFile[i] = false;
        }
        
        
    }
    
    /*
     * Constructor with filenames.
     * @param alg The algorithm to use.
     * @param bandit The bandit problem to run on.
     * @param total_rounds Number of rounds to run the trial.
     * @param filename Name of file to write to.
     */
    public MultiplayerTrial(Algorithm[] algs, MultiplayerBandit bandit,
                            int total_rounds, String[] filenames) {
        // Initialize values.
        // Initialize values.
        this.algs = algs;
        this.bandit = bandit;
        this.players = this.bandit.players;
        assert(this.players == algs.length);
        this.total_rounds = total_rounds;
        this.rounds = 0;
        this.total_regret = new double[this.players];
        this.toFile = new boolean[this.players];
        for (int i = 0; i < this.players; i++) {
            this.total_regret[i] = 0;
            this.toFile[i] = false;
        }
        
        // Parse filenames
        for (int i = 0; i < filenames.length; i++) {
            // If empty filename, don't record.
            if (filenames[i] == "")
                continue;
            try {
                this.writers[i] = new FileWriter(filenames[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.toFile[i] = true;
            this.writeInterval = 1;
        }
    }
    
    
    
    /*
     * Runs the Trial.
     */
    public void run() {
        for (int i = 0; i < this.total_rounds; i++) {            
            // Construct the choice.
            DomainElement[] choices = new DomainElement[this.players];
            for (int j = 0; j < this.players; j++) {
                choices[j] = this.algs[j].makeChoice();
            }
            double[] reward = this.bandit.getReward(choices); // Get the reward.

            // Get regrets
            double[] regrets = new double[this.players];
            for (int j = 0; j < this.players; j++) {
                regrets[j] = this.bandit.getRewardMax()[j] - reward[j];
                this.total_regret[j] += regrets[j];
            }

            // Report rewards
            for (int j = 0; j < this.players; j++) {
                this.algs[j].recieveReward(reward[j]);
            }
            this.rounds++;

            // Get average regrets;
            double average_regret[] = new double[this.players];
            for (int j = 0; j < this.players; j++) {
                average_regret[j] = this.total_regret[j] / this.rounds;
            }

            // Write to files, if necessary
            for (int j = 0; j < this.players; j++) {
                if (this.toFile[j] && (rounds % writeInterval == 0)) {
                    try {
                        // Write instantaneous regret
                        this.writers[j].write("" + this.rounds + " " +
                                regrets[j] + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(DEBUG){
            	// Test/debug code.
            	if (i % (Math.max(this.total_rounds / 100,1)) == 0) {
            		System.out.println("Average regret after move " + (i + 1) + ": "
            				+ average_regret);
            	}
            }
        }
        
        // Finish writing to files, if necessary.
        for (int j = 0; j < this.players; j++) {
            if (this.toFile[j]) {
                try {
                    this.writers[j].flush();
                    this.writers[j].close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    
    /*
     * Sets writeInterval.
     * @param newInterval The new writing interval
     */
    public void setWriteInterval(int newInterval) {
        this.writeInterval = newInterval;
    }
    
    
    
}