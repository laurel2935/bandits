
/*
 * Represents the domain over which a problem can be over.
 */
public abstract class CoveringOracle {

    /*
     * @param input The center of the sphere we are covering
     * @param radius The radius of the sphere we are adding
     * Adds the sphere to our covering.
     */
    public abstract void addElement(DomainElement input, double radius);
    
    /*
     * @return An uncovered DomainElement
     */
    public abstract DomainElement getUncoveredElement();
    /*
     * Clears the covering.
     */
    public abstract void clearCovering();
    
}
