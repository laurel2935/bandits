public class Main {

    // Just testing stuff out for now.
    public static void main(String[] args) {
        double[] coeffs = {0, 1};
        PolynomialRealBandit test_prob = new PolynomialRealBandit(coeffs);
        test_prob.setRewardMax(1);
        DiscretizedUCB1 test_alg = new DiscretizedUCB1(test_prob, 100);

        int num_plays = 1000000;
        for (int i = 0; i < num_plays; i++) {
            test_alg.makeMove();
            if (i % (num_plays / 100) == 0) {
                System.out.println("Average regret after move " + (i + 1) + ": "
                        + test_alg.getAverageRegret());
            }
        }

    }

}
