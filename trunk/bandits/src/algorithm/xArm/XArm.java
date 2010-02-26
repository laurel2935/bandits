package algorithm.xArm;

import java.util.ArrayList;

import algorithm.Algorithm;
import domain.RealNumber;

/**
 * 
 * @author mfaulkne
 * 
 * TODO: needs to implement/extend an interface/abstract class
 * TODO: currently requires elements to be represented by vectors
 */
public class XArm extends Algorithm{
	private final int dimension;
	private CoverTree<CubicCoverNode> coverTree;
	private CubicCoverNode pendingNode = null;
	
	public XArm(int dimension){
		this.dimension = dimension;
		this.coverTree = new CubicCoverTree(dimension);
	}
	
	/**
	 * TODO: this currently bottlenecks down to 1-D case. Need to implement more domains.
	 */
	public RealNumber makeChoice(){
		assert(pendingNode == null);
		pendingNode = coverTree.pick();
		
		// choose (any way you want) an element covered by the node
		// i.e. choose the midpoint of its covering region:
		// TODO: generalize this...
		ArrayList<Double> lowerBounds = pendingNode.getLowerBounds();
		ArrayList<Double> upperBounds = pendingNode.getUpperBounds();
		ArrayList<Double> choice = new ArrayList<Double>(dimension);
		for(int i=0; i<dimension; ++i){
			double l = lowerBounds.get(i);
			double u = upperBounds.get(i);
			choice.add(i,(l+u)/2);
		}
		RealNumber r = new RealNumber(choice.get(0));
		return r;
	}
	
	
	public void recieveReward(double reward){
		assert(pendingNode != null);
		coverTree.update(pendingNode, reward);
		pendingNode = null;
	}
}
