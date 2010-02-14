import java.util.Random;

/*
 * Represents the domain over which a problem can be over.
 */
public abstract class Domain {

    // Random number generator.
    protected Random rand;
    
    /*
     * Constructor.  Initializes random number generator.
     */
    public Domain() {
       rand = new Random(); 
    }
    
    
    /*
     * @param input An element which we want to see is in this domain.
     * @return True if the input is in this domain, False otherwise.
     */
    public abstract boolean isIn(DomainElement input);
    
    /*
     * Returns a 'random' element of this domain.
     * @return A random element of this domain.
     */
    public abstract DomainElement randomElement();
}
