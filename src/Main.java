public class Main {

    // Just testing stuff out for now.
    public static void main(String[] args) {
        double[] coeffs = {0, 1};
        PolynomialRealBandit test_prob = new PolynomialRealBandit(coeffs, 0, 1);
        test_prob.setRewardMax(1);
        NoisyBandit noisy = new NoisyBandit(test_prob, 1, 1);
        
        int arms = 100;
        Algorithm test_alg = new DiscretizedExp3(noisy.domain, arms);        
        int num_plays = 1000000;
        Trial test_run = new Trial(test_alg, test_prob, num_plays, "exp3.txt"); 
        test_run.setWriteInterval(1000);
        test_run.run();
    }

}
