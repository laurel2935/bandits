
import java.lang.Math;

/*
 * A bandit problem on [0,1] with a polynomial reward function of finite degree.
 */
public class PolynomialRealBandit extends RealBandit {

    // Coefficients on the polynomial
    private double[] coeffs;
    
    // Degree of the polynomial
    private int degree;
    
    /*
     * Constructor.
     * @param coeffs An array specifying the coefficients in the polynomial in
     *               increasing degree.
     */
    public PolynomialRealBandit(double[] coeffs) {
        this.domain_min = 0;
        this.domain_max = 1;
        this.degree = coeffs.length;
        this.coeffs = coeffs.clone();
    }
    
    @Override
    public double reward(double input) {
        double sum = 0;
        for (int pow = 0; pow < this.degree; pow++) {
            sum += coeffs[pow] * Math.pow(input, pow);
        }
        return sum;
    }
}
