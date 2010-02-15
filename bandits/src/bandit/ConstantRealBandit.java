package bandit;


public class ConstantRealBandit extends PolynomialRealBandit {
    
    /*
     * Constructor.
     * @param value The constant that this bandit problem is equal to.
     */
    public ConstantRealBandit(double value, double domain_min,
                              double domain_max) {
        super(new double[] {value}, domain_min, domain_max);
        this.reward_max = value;
    }   
}