package algorithm.xArm;

import java.util.ArrayList;
import java.util.Random;

import algorithm.Algorithm;
import domain.RealVector;

public class XArmRn extends Algorithm{
	private final int dimension;
	private final double gamma;
	private CoverTree<CubicCoverNode> coverTree;
	private CubicCoverNode pendingNode = null;
	
	
	/**
	 * Standarm XArm for R^n
	 * @param dimension
	 */
	public XArmRn(int dimension){
		this(dimension,0); // constructor chaining.
	}
	
	/**
	 * XArm for R^n with randomized exploration
	 * @param dimension
	 * @param gamma - probability that the algorithm randomly explores.
	 */
	public XArmRn(int dimension, double gamma){
		assert(dimension > 0);
		assert(gamma <=1);
		assert(0 <= gamma);
		this.dimension = dimension;
		this.gamma = gamma;
		this.coverTree = new CubicCoverTree(dimension);
	}

	@Override
	public RealVector makeChoice() {
		assert(pendingNode == null);
		
		Random random = new Random();
		double val = random.nextDouble();
		if(val >= gamma){
			// standard selection with prob 1-gamma
			pendingNode = coverTree.pick();
		}else{
			// explore with prob gamma
			pendingNode = coverTree.pickRandom();
		}
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
