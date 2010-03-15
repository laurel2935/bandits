package algorithm.xArm;

import java.util.ArrayList;

import algorithm.Algorithm;
import domain.RealVector;

public class XArmRn extends Algorithm{
	private final int dimension;
	private CoverTree<CubicCoverNode> coverTree;
	private CubicCoverNode pendingNode = null;
	
	public XArmRn(int dimension){
		this.dimension = dimension;
		this.coverTree = new CubicCoverTree(dimension);
	}

	@Override
	public RealVector makeChoice() {
		assert(pendingNode == null);
		pendingNode = coverTree.pick();
		
		// choose (any way you want) an element covered by the node
		// i.e. choose the midpoint of its covering region:
		// TODO: generalize this...
		ArrayList<Double> lowerBounds = pendingNode.getLowerBounds();
		ArrayList<Double> upperBounds = pendingNode.getUpperBounds();
		//ArrayList<Double> choice = new ArrayList<Double>(dimension);
		double[] choice = new double[dimension];
		for(int i=0; i<dimension; ++i){
			double l = lowerBounds.get(i);
			double u = upperBounds.get(i);
			//choice.add(i,(l+u)/2);
			choice[i] = (l+u)/2;
		}
		RealVector rv = new RealVector(choice);
		return rv;
	}

	@Override
	public void recieveReward(double reward) {
		assert(pendingNode != null);
		coverTree.update(pendingNode, reward);
		pendingNode = null;
	}
}
