
/*
 * Represents the domain over which a problem can be over.
 */
public abstract class CoveringOracle {

    /*
     * @param input An element which we want to see is in this domain.
     * @return True if the input is in this domain, False otherwise.
     */
    public abstract void addElement(DomainElement input, double radius);
    
    public abstract DomainElement getUncoveredElement();
    /*
     * Returns a 'random' element of this domain.
     * @return A random element of this domain.
     */
    public abstract void clearCovering();
    
}
