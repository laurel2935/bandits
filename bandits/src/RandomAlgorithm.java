
/*
 * Algorithm that makes random choices.
 */
public class RandomAlgorithm extends Algorithm {

    // Domain of problem.
    Domain domain;
    
    /*
     * Initializer.
     * @param domain The domain of the problem.
     */
    public RandomAlgorithm(Domain domain) {
        this.domain = domain;
    }
    
    @Override
    public DomainElement makeChoice() {
        return this.domain.randomElement();
    }

    @Override
    public void recieveReward(double reward) {
        // Do nothing.
    }

}
