public class Main {

    // Just testing stuff out for now.
    public static void main(String[] args) {
        double[] coeffs = {0, 1};
        PolynomialRealBandit test_prob = new PolynomialRealBandit(coeffs, 0, 1);
        test_prob.setRewardMax(1);
        int arms = 100;
        DiscretizedUCB1 test_alg = new DiscretizedUCB1(test_prob.domain, arms);        
        int num_plays = 1000000;
        Trial test_run = new Trial(test_alg, test_prob, num_plays, "test1.txt"); 
        test_run.setWriteInterval(1000);
        test_run.run();
    }

}
