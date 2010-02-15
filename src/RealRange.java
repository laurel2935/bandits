/*
 * Represents a range of real numbers.
 */
public class RealRange extends Domain {

    // Minimum and maximum of this range of real numbers.
    private double min;
    private double max;
    
    /*
     * Constructor.
     * @param min Minimum value of this range of real numbers.
     * @param max Maximum value of this range of real numbers. 
     */
    public RealRange(double min, double max) {
        super();
        this.min = min;
        this.max = max;
    }
    
    
    public boolean isIn(DomainElement input) {
        if (! (input instanceof RealNumber))
            return false;
        // Test for inclusion in this interval.
        return this.min <= ((RealNumber)input).getValue() && 
               this.max >= ((RealNumber)input).getValue();
    }
    
    
    /*
     * Gets the min value in this range.
     * @return The minimal value of this range.
     */
    public double getMin() {
        return this.min;
    }
    
    /*
     * Gets the max value in this range.
     * @return The maximal value of this range.
     */
    public double getMax() {
        return this.max;
    }
    
    
    public RealNumber randomElement() {
        // Just get a random number in the range.
        double val = this.rand.nextDouble() * (this.max - this.min) + this.min;
        return new RealNumber(val);
    }

    private class RealRangeCoveringOracle extends CoveringOracle{
    	
    	// Used to indicate covered regions
    	private class Range{
    		
    		public double start;
    		public double end;
    		
    		public Range(double s, double e) {
				start = Math.max(s,min);
				end = Math.min(e, max);
			}
    		
    		public boolean contains(double v){
    			return (start<=v) && (v<=end);
    		}
    		
    		public String toString(){
    			return "("+start+", " + end + ")\n";
    		}
    	}
    	
    	
    	DoublyLinkedList<Range> coveredRegion;
    	public RealRangeCoveringOracle(){
    		coveredRegion = new DoublyLinkedList<Range>();
    	}
    	
		@Override
		public void addElement(DomainElement input, double radius) {
			if(input instanceof RealNumber){
				double center = ((RealNumber) input).getValue();
				coveredRegion.toFirst();
				if(coveredRegion.currentIsNull()){
					coveredRegion.addElement(new Range(center - radius, center + radius));
				} else {
					boolean inserted = false;
					while(!inserted){
						// If new sphere overlaps with current
						if(coveredRegion.getCurrentValue().contains(center - radius) || coveredRegion.getCurrentValue().contains(center + radius)){
							Range oldRange = coveredRegion.getCurrentValue();
							Range newRange = new Range(Math.min(oldRange.start, center - radius), Math.max(oldRange.end, center + radius));
							coveredRegion.updateCurrent(newRange);
							inserted = true;
							//check overlap with next.
							if(coveredRegion.next()){
								if(coveredRegion.getCurrentValue().start < center + radius){
									double newEnd = coveredRegion.getCurrentValue().end;
									coveredRegion.removeCurrent();
									coveredRegion.updateCurrent(new Range(newRange.start,newEnd));
									inserted = true;
								}
							}
						// if new sphere is before current, add before
						} else if (coveredRegion.getCurrentValue().start > center + radius){
							coveredRegion.insertBeforeCurrent(new Range(center - radius, center + radius));
							inserted = true;
						// If there are no more elements, add on to the end, else increment
						} else if(!coveredRegion.next()){
							coveredRegion.addElement(new Range(center - radius, center + radius));
							inserted = true;
						}
					}
				}
			}			
		}

		@Override
		public DomainElement getUncoveredElement() {
			coveredRegion.toFirst();
			if(coveredRegion.currentIsNull()){
				return new RealNumber((min + max)/2);
			} else {
				double lastMax = min;
				do{
					if(lastMax < coveredRegion.getCurrentValue().start)
						return new RealNumber((lastMax+coveredRegion.getCurrentValue().start)/2);
					lastMax = coveredRegion.getCurrentValue().end;
				}while (coveredRegion.next());
				if(lastMax < max){
					return new RealNumber((max+lastMax)/2);
				}
			}
			return null;
		}

		@Override
		public void clearCovering() {
			// TODO Auto-generated method stub
			coveredRegion = new DoublyLinkedList<Range>();
		}
    	
		public String toString(){
			String output = "";
			coveredRegion.toFirst();
			while(!coveredRegion.currentIsNull()){
				output += coveredRegion.getCurrentValue().toString();
				coveredRegion.next();
			}
			return output+"\n\n";
		}
    }
    
	@Override
	public CoveringOracle getCoveringOracle() {
		return new RealRangeCoveringOracle();
	}
}
