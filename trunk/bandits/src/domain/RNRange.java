package domain;

import java.util.ArrayList;
import java.util.Random;

import domain.RealRange.RealRangeCoveringOracle;

public class RNRange extends Domain {

    // List of dimensions
    private RealRange[] ranges;
    
    // Dimension
    private int dim;
    
    // Random number generator
    private Random rand;

    /*
     * Constructor.
     * @param bounds
     */
    public RNRange(double[][] bounds) {
        // Need two numbers for each dimension
        assert(bounds.length % 2 == 0);
        this.dim = bounds.length;
        // Make the ranges
        this.ranges = new RealRange[this.dim];
        for (int i = 0; i < dim; i++) {
            this.ranges[i] = new RealRange(bounds[i][0], bounds[i][1]);
        }
        this.rand = new Random();
    }

    @Override
    public boolean isIn(DomainElement input) {
        // If not a RealVector, can't possibly be in this range.
        if (! (input instanceof RealVector))
            return false;

        double[] inputVals = ((RealVector) input).getValue();
        if (! (inputVals.length == this.dim))
            return false;
        // Test for inclusion in each interval.
        for (int i = 0; i < this.dim; i++) {
            if (! this.ranges[i].isIn(new RealNumber(inputVals[i]))) {
                return false;
            }
        }
        // In each range.
        return true;
    }

    @Override
    public DomainElement randomElement() {
        double[] vals = new double[this.dim];
        // Put a random value in each entry
        for (int i = 0; i < this.dim; i++) {
            double min = this.ranges[i].getMin();
            double max = this.ranges[i].getMax();
            double val = (max - min) * this.rand.nextDouble() + min;
            vals[i] = val;
        }
        return new RealVector(vals);
    }
    
    @Override
    public CoveringOracle getCoveringOracle() {
        // TODO Auto-generated method stub
        return new RNRangeCoveringOracle();
    }
    
    private class RNRangeCoveringOracle extends CoveringOracle{
    	private class Hypercube{
    		RealVector center;
    		double radius;
    		public Hypercube(RealVector c, double s){
    			center = c;
    			radius = s;
    		}
    		public String toString(){
    			String output = "Center: (";
    			for(double xi:center.getValue())
    				output+=xi+", ";
    			output +=") Radius: "+radius;
    			return output;
    		}
    	}
    	private int dimension;
    	private double position;
    	private ArrayList<CoveringOracle> subCovers;
    	private ArrayList<Hypercube> inputs;
    	private RealRange[] rangeList;
		public RNRangeCoveringOracle(){
			dimension=dim;
			position = 0;
			rangeList=ranges;
			subCovers = new ArrayList<CoveringOracle>();
			inputs = new ArrayList<Hypercube>();
			addSubCover(rangeList[0].getMin(),0);
		}
		
		public RNRangeCoveringOracle(int dim, double val, RealRange[] ranges){
			dimension=dim;
			subCovers = new ArrayList<CoveringOracle>();
			inputs = new ArrayList<Hypercube>();
			position=val;
			rangeList=ranges;
			addSubCover(rangeList[0].getMin(),0);
		}
		
		public double getPosition(){
			return position;
		}
		
		@Override
		public void addElement(DomainElement input, double radius) {
			if(input instanceof RealVector){				
				//System.out.println("Adding element");
				// Calculate subinput to feed to subcovers, and use first value as index
				double vals[] = ((RealVector) input).getValue();
				double subinput[] = new double[vals.length-1];
				double val = vals[0];
				for(int i=1;i<vals.length;i++){
					subinput[i-1]=vals[i];
				}
				double upper=Math.min(val+radius,rangeList[0].getMax());
				double lower=Math.max(val-radius,rangeList[0].getMin());
				
				
				//Add new index to list
				int pos1 = binarySearch(lower,0,subCovers.size()-1);				
				if(pos1 != -1)
					addSubCover(lower,pos1);
				int pos2 = binarySearch(upper,0,subCovers.size()-1);
				if(pos2 != -1 && upper<rangeList[0].getMax())
					addSubCover(upper,pos2);
				
				// add new input to list of inputs.
				// This is used when we need to consider a new n-1 hypercube.
				// This is after we add the new subcovers so the next section does not double add.
				//System.out.println("Adding to Inputs");
				inputs.add(new Hypercube((RealVector) input,radius));
				
				//add new element to subcovers
				//System.out.println("Adding new input to subcovers");
				for(CoveringOracle cover:subCovers){
					if(cover instanceof RNRangeCoveringOracle){
						//System.out.println("Adding "+this.dimension+"D");
						if(((RNRangeCoveringOracle) cover).getPosition()>=lower && ((RNRangeCoveringOracle) cover).getPosition()<upper){
							cover.addElement(new RealVector(subinput), radius);
						}
					}else{
						//System.out.println("Adding 1D");
						if(((RealRangeCoveringOracle) cover).getPosition()>=lower && ((RealRangeCoveringOracle) cover).getPosition()<upper){
							cover.addElement(new RealNumber(subinput[0]), radius);
						}
					}
				
				}
			}			
		}
		
		public void addSubCover(double value,int pos) {
			//System.out.println("Adding new Subcover at position: "+pos+" and value: "+value);
			if(dimension==2){
				RealRangeCoveringOracle addMe = (RealRangeCoveringOracle) rangeList[1].getCoveringOracle();
				addMe.setPosition(value);
				subCovers.add(pos,addMe);
				for(Hypercube cube:inputs){
					//System.out.println(cube);
					double centerCoords[] = (cube.center).getValue();
					double center = centerCoords[0];
					double upper=Math.min(center+cube.radius,rangeList[0].getMax());
					double lower=Math.max(center-cube.radius,rangeList[0].getMin());
					if(value>=lower && value<upper){
						//System.out.println("Action taken");
						addMe.addElement(new RealNumber(centerCoords[1]), cube.radius);
					}
				}
				//System.out.println("New subcover of 1D at "+value+"\n"+addMe);
			}else{
				RealRange[] subRange = new RealRange[rangeList.length-1];
				for(int i=1;i<rangeList.length;i++)
					subRange[i-1]=rangeList[i];
				RNRangeCoveringOracle addMe = new RNRangeCoveringOracle(dimension-1, value,subRange);
				subCovers.add(pos, addMe);
				// Add all pertinent information
				for(Hypercube cube:inputs){
					double centerCoords[] = (cube.center).getValue();
					double subcenter[] = new double[centerCoords.length-1];
					for(int i=1;i<centerCoords.length;i++){
						subcenter[i-1]=centerCoords[i];
					}
					double center = centerCoords[0];
					double upper=Math.min(center+cube.radius,rangeList[0].getMax());
					double lower=Math.max(center-cube.radius,rangeList[0].getMin());
					if(addMe.getPosition()>=lower && addMe.getPosition()<upper){
						addMe.addElement(new RealVector(subcenter), cube.radius);
					}
				}
			}
		}
		
		// Finds location to add new item. Returns -1 if it already is handled
		private int binarySearch(double val,int start, int stop){
			int mid = (start+stop)/2;
			double midLoc = 0;
			if(subCovers.size()==0){
				return 0;
			}
			if(this.dimension >2)
				midLoc = ((RNRangeCoveringOracle) subCovers.get(mid)).getPosition();
			else
				midLoc = ((RealRangeCoveringOracle) subCovers.get(mid)).getPosition();
				//System.out.println(start+","+stop);
			if(start==stop){
				if(midLoc>val){
					return start;
				}else if(midLoc<val){
					return start+1;
				}else{
					return -1;
				}
			}
			if(stop<start){
				return start;
			}
			if(midLoc>val){
				return binarySearch(val,start,mid-1);
			}else if(midLoc<val){
				return binarySearch(val,mid+1,stop);
			}else{
				return -1;
			}
		}		
		
		

		@Override
		public void clearCovering() {
			// TODO Auto-generated method stub
			subCovers = new ArrayList<CoveringOracle>();
	    	inputs = new ArrayList<Hypercube>();
		}

		@Override
		public DomainElement getUncoveredElement() {
			if(dimension > 2){
				RealVector subUncovered=null;
				double value = 0;
				for(CoveringOracle cover:subCovers){
					if(cover.getUncoveredElement()!=null){
						subUncovered = (RealVector)cover.getUncoveredElement();
						value = ((RNRangeCoveringOracle)cover).getPosition();
					}
				}
				if(subUncovered !=null){
					double[] uncovered = new double[subUncovered.getDim()+1];
					for(int i=0;i<subUncovered.getDim();i++)
						uncovered[i+1]=subUncovered.getValue()[i];
					uncovered[0]=value;
					return new RealVector(uncovered);
				}else return null;
			}else {
				RealNumber subUncovered=null;
				double value = 0;
				for(CoveringOracle cover:subCovers){
					if(cover.getUncoveredElement()!=null){
						subUncovered = (RealNumber)cover.getUncoveredElement();
						value = ((RealRangeCoveringOracle)cover).getPosition();
					}
				}
				if(subUncovered !=null){
					double[] uncovered = new double[]{value,subUncovered.getValue()};
					return new RealVector(uncovered);
				}else return null;
			}
		}
		
		// for debug purposes
		public String toString(){
			String output = "D="+this.dimension+"\n";
			for(CoveringOracle subcover:subCovers){
				output += "Coordinate :"+this.position+"\n";
				String output2 = subcover.toString();
				// Just in case this does silly stuff
				output2.replace("\n", "NEWLINE");
				output2.replace("NEWLINE", "\n\t");
				output += output2;
			}
			return output;
		}
    }

}
