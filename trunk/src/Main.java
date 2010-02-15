public class Main {

    // Just testing stuff out for now.
    public static void main(String[] args) {
        // Make bandit
        double[] coeffs = {0, 1};
        PolynomialRealBandit poly = new PolynomialRealBandit(coeffs, 0, 1);
        poly.setRewardMax(1);
        ComposedRealBandit comp = new ComposedRealBandit(poly, poly);
        BinomialBandit bin = new BinomialBandit(comp);
        NoisyBandit noise = new NoisyBandit(bin, .5, 1);

        // Make algorithm
        int arms = 100;
        Algorithm test_alg = new DiscretizedUCB1(noise.domain, arms);        

        // Run
        int num_plays = 1000000;
        Trial test_run = new Trial(test_alg, noise, num_plays, "ucb3comp.txt"); 
        test_run.setWriteInterval(1000);
        test_run.run();
    }

}
