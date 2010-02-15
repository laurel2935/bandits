package algorithm;
import domain.Domain;
import domain.DomainElement;

/*
 * Represents an Algorithm, abstractly.
 */
public abstract class Algorithm {
    // Domain the algorithm is working on.
    public Domain domain;
    
    /*
     * Makes an arm choice.
     * @return The arm chosen.
     */
    public abstract DomainElement makeChoice();
    
    /*
     * Receive the reward from the last given arm.  Typically an algorithm will
     * behave differently depending on the reward received.
     */
    public abstract void recieveReward(double reward);
}