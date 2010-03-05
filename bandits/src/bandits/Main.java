package bandits;
import domain.RNRange;
import bandit.BinomialBandit;
import bandit.ComposedRealBandit;
import bandit.NoisyBandit;
import bandit.PolynomialRealBandit;
import bandit.tictactoe.FullTicTacToeBandit;
import bandit.tictactoe.TicTacToeBandit;
import algorithm.*;
import algorithm.zoom.*;
import algorithm.discrete.*;

public class Main {

    // Just testing stuff out for now.
    public static void main(String[] args) {
        // Make bandit
/*        double[] coeffs = {1, -3, 3};
        PolynomialRealBandit poly = new PolynomialRealBandit(coeffs, 0, 1);
        poly.setRewardMax(1);
        BinomialBandit bin = new BinomialBandit(poly);
        NoisyBandit noise = new NoisyBandit(bin, .5, 1);
*/
        FullTicTacToeBandit toe = new FullTicTacToeBandit();

        // Make algorithm
        int arms = 1000;
        Algorithm test_alg = new DiscretizedEpsilonGreedy(toe.domain, arms);

        // Run
        int num_plays = 1000000;
        Trial test_run = new Trial(test_alg, toe, num_plays, "epsgredfulltoe.txt"); 
        test_run.setWriteInterval(1);
        test_run.run();
    }

}
