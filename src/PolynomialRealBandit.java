
import java.lang.Math;

/*
 * Represents a bandit problem with a polynomial reward function.
 */
public class PolynomialRealBandit extends RealBandit {

    // Coefficients of the polynomial, starting with the constant term.
    private double[] coeffs;
    
    // Degree of the polynomial.
    private int degree;

    /*
     * Constructor.
     * @param coeffs An array specifying the coefficients in the polynomial in
     *               increasing degree.
     */
    public PolynomialRealBandit(double[] coeffs, double domain_min,
                                double domain_max) {
        super(domain_min, domain_max);
        this.degree = coeffs.length;
        this.coeffs = coeffs.clone();
    }

    @Override
    public double reward(DomainElement input) {
        double val = ((RealNumber)input).getValue(); // Value of input.
        // Sum each term in the polynomial
        double sum = 0;
        for (int pow = 0; pow < this.degree; pow++) {
            sum += coeffs[pow] * Math.pow(val, pow);
        }
        return sum;
    }

}