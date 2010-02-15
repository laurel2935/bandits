package xArm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author mfaulkne
 * Cover Tree over [0,1]^D
 */
public class CubicCoverTree implements CoverTree<CubicCoverNode>{
	private final int dimension;
	private List <CubicCoverNode> nodes;
	private Map<CubicCoverNode, Integer> visitCounts;
	//total reward of all nodes in the subtree rooted at a given node:
	private Map<CubicCoverNode, Double> totalRewards;
	//private Map<CubicCoverNode, Double> upperConfidenceBounds; //these need to be recomputed on each round...
	private Map<CubicCoverNode, Integer> depths;
	private CubicCoverNode root;
	// number of nodes picked so far:
	int numPicked = 0;
	private final double nu;
	private final double rho;
	
	/**
	 * Set nu and rho to the values described in Remark 2.
	 * @param dimension Must be greater than zero.
	 */
	public CubicCoverTree(int dimension){
		this(dimension,Math.sqrt(dimension)/2, Math.pow(2.0, -1/dimension));
	}
	
	/**
	 * 
	 * @param dimension
	 * @param nu
	 * @param rho
	 */
	public CubicCoverTree(int dimension, double nu, double rho){
		assert(dimension > 0);
		assert(nu > 0);
		assert(0 < rho && rho < 1);
		
		this.dimension = dimension;
		nodes = new LinkedList<CubicCoverNode>();
		visitCounts = new HashMap<CubicCoverNode, Integer>();
		totalRewards = new HashMap<CubicCoverNode, Double>();
		//upperConfidenceBounds = new HashMap<CubicCoverNode, Double>();
		depths = new HashMap<CubicCoverNode, Integer>();
		this.nu = nu;
		this.rho = rho;
		
		//TODO: create root node, and initialize its associated values.
		ArrayList<Double> lowerBounds = new ArrayList<Double>(dimension);
		ArrayList<Double> upperBounds = new ArrayList<Double>(dimension);
		for(int i=0; i<dimension; ++i){
			lowerBounds.add(i, 0.0);
			upperBounds.add(i,1.0);
		}
		root = new CubicCoverNode(dimension,lowerBounds,upperBounds,null);
	}
	
	/**
	 * 
	 * @return A new covering node.
	 */
	public CubicCoverNode pick(){
		CubicCoverNode returnNode;
		if(numPicked==0){
			//first pick
			returnNode = root;
			depths.put(root,0);
		} else {
			returnNode = findNewNode(root);
		}

		nodes.add(returnNode);
		numPicked++;
		return returnNode;
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 */
	private CubicCoverNode findNewNode(CubicCoverNode node){

		CubicCoverNode returnNode;
		
		//if left child is null, instantiate it and return it
		if(node.getLeftChild() == null){
			ArrayList<Double> lowerBounds = node.getLowerBounds();
			ArrayList<Double> upperBounds = computeLeftUpperBounds(node.getLowerBounds(), node.getUpperBounds());
 			CubicCoverNode newNode = new CubicCoverNode(dimension, lowerBounds, upperBounds, node);
			returnNode = newNode;
		} else if(node.getRightChild() == null){
			ArrayList<Double> lowerBounds = computeRightLowerBounds(node.getLowerBounds(), node.getUpperBounds());
			ArrayList<Double> upperBounds = node.getUpperBounds();
			CubicCoverNode newNode = new CubicCoverNode(dimension, lowerBounds, upperBounds, node);
			returnNode = newNode;
		}else{
			double leftB = getBValue(node.getLeftChild());
			double rightB = getBValue(node.getRightChild());
			returnNode = (leftB > rightB) ? findNewNode(node.getLeftChild()) : findNewNode(node.getRightChild());
		}
		
		return returnNode;
	}
	
	/**
	 * Helper function to compute the B-value of a node.
	 * @param node
	 * @return
	 */
	private double getBValue(CubicCoverNode node){
		if( (node.getLeftChild() == null) || (node.getRightChild() == null)){
			return Double.MAX_VALUE;
		}else{
			double leftB = getBValue(node.getLeftChild());
			double rightB = getBValue(node.getRightChild());
			double U = computeUpperConfidenceBound(node);
			return Math.min(U, Math.max(leftB, rightB));
		}
	}
	
	/**
	 * Compute upper confidence bound U
	 * @param node
	 * @return
	 */
	private double computeUpperConfidenceBound(CubicCoverNode node){
		int h = depths.get(node);
		double U = totalRewards.get(node) / visitCounts.get(node) 
		+ Math.sqrt( 2*Math.log(numPicked) / visitCounts.get(node) ) 
		+ nu * Math.pow(rho, h);
		return U;
	}
	
	/**
	 * Feed back a reward in [0,1] to the chosen node.
	 * @param node
	 * @param reward
	 */
	public void update(CubicCoverNode node, double reward){
		assert(0 <= reward && reward <=1);
		// compute new visit count
		if(visitCounts.containsKey(node)){
			int visitsSoFar = visitCounts.get(node);
			visitCounts.put(node, visitsSoFar+1);
		}else{
			visitCounts.put(node, 1);
		}
		
		// compute new total reward
		if(totalRewards.containsKey(node)){
			double rewardSoFar = totalRewards.get(node);
			totalRewards.put(node, rewardSoFar + reward);
		}else{
			totalRewards.put(node, reward);
		}
		
		if(node == root){
			return;
		}else{
			update(node.getParent(),reward);
		}
		repcheck();
	}
	
	
	/**
	 * This is vaguely an abstraction violation... used for testing.
	 * @return number of nodes in the tree.
	 */
	public int size(){
		return nodes.size();
	}
	
	/**
	 * Also an abstraction violation for convenient testing.
	 * @param node
	 * @return the empirical average reward "mu" of all paths through the specified node.
	 */
	public double getEmpiricalAverage(CubicCoverNode node){
		return totalRewards.get(node)/visitCounts.get(node);
	}
	
	/**
	 * Also an abstraction violation for convenient testing.
	 * @param node
	 * @return the upper confidence bound "U" for the node.
	 */
	public double getUpperConfidenceBound(CubicCoverNode node){
		return computeUpperConfidenceBound(node);
	}
	
	/**
	 * Selects a dimension along which to split the region covered by lowerBounds and upperBounds
	 * See Remark 2 in the paper.
	 * 
	 * @param lowerBounds
	 * @param upperBounds
	 * @return dimension to split the bounds along.
	 */
	private int chooseSplittingDimension(ArrayList<Double> lowerBounds, ArrayList<Double> upperBounds){
		checkBounds(lowerBounds, upperBounds);
		//choose the longest axis
		int longestAxis = 0;
		double longestAxisLength = 0;
		for(int i=0; i<lowerBounds.size(); ++i){
			double axisLength = upperBounds.get(i) - lowerBounds.get(i);
			if(axisLength > longestAxisLength){
				longestAxis = i;
				longestAxisLength = axisLength;
			}
		}
		return longestAxis;
	}
	
	/**
	 * Compute the new upper bounds for a left child (its lower bounds are the same as its parent)
	 * @param lowerBounds
	 * @param upperBounds
	 * @return
	 */
	private ArrayList<Double> computeLeftUpperBounds(ArrayList<Double> lowerBounds, ArrayList<Double> upperBounds){
		int splittingDimension = chooseSplittingDimension(lowerBounds, upperBounds);
		double midpoint = (lowerBounds.get(splittingDimension) + upperBounds.get(splittingDimension)) /2;
		ArrayList<Double> newUpperBounds = upperBounds;
		newUpperBounds.set(splittingDimension, midpoint);
		return newUpperBounds;
	}
	
	/**
	 * Compute the new lower bounds for a right child (its upper bounds are the same as its parent)
	 * @param lowerBounds
	 * @param upperBounds
	 * @return
	 */
	private ArrayList<Double> computeRightLowerBounds(ArrayList<Double> lowerBounds, ArrayList<Double> upperBounds){
		int splittingDimension = chooseSplittingDimension(lowerBounds, upperBounds);
		double midpoint = (lowerBounds.get(splittingDimension) + upperBounds.get(splittingDimension)) /2;
		ArrayList<Double> newLowerBounds = lowerBounds;
		newLowerBounds.set(splittingDimension, midpoint);
		return newLowerBounds;
	}
	
	/**
	 * 
	 * @param lowerBounds
	 * @param upperBounds
	 */
	public void checkBounds(ArrayList<Double> lowerBounds, ArrayList<Double> upperBounds){
		assert(lowerBounds.size() == dimension); 
		assert(upperBounds.size() == dimension);
		
		for(int i = 0; i<dimension; i++){
			double lower = lowerBounds.get(i);
			double upper = upperBounds.get(i);
			assert(lower <= upper);
			assert(0<= lower);
			assert(lower <= 1);
			assert(0<=upper);
			assert(upper<=1);
		}
	}
	
	/**
	 * Check that any representation invariants still hold.
	 */
	private void repcheck(){
		assert(this.visitCounts.size() == numPicked);
		assert(this.nodes.size() == numPicked);
	}
}
