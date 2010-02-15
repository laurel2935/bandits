package algorithm;
import domain.DomainElement;
import domain.RealRange;
import bandit.Bandit;


public abstract class RealBandit extends Bandit {

    /*
     * Constructs a new RealBandit.
     * @param min Minimum real domain element.
     * @param max Maximum real domain element.
     */
    public RealBandit(double min, double max) {
        this.domain = new RealRange(min, max);
    }
    
    @Override
    public abstract double reward(DomainElement input);

}