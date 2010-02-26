package algorithm.xArm;

import java.util.ArrayList;


/**
 * 
 * @author mfaulkne
 * Node for cover tree over [0,1]^D
 * 
 * TODO: change implementation to use a designated root class, rather than setting parent to null for the root.
 */
public class CubicCoverNode implements CoverNode{
	private final int dimension;
	private final ArrayList<Double> lowerBounds;
	private final ArrayList<Double> upperBounds;
	private final CubicCoverNode parent;
	private CubicCoverNode leftChild = null;
	private CubicCoverNode rightChild = null;
	
	public CubicCoverNode(int dimension, ArrayList<Double> lowerBounds, ArrayList<Double> upperBounds, CubicCoverNode parent){
		this.dimension = dimension;
		assert(dimension > 0);
		assert(lowerBounds.size() == dimension);
		assert(upperBounds.size() == dimension);
		//initialize
		this.lowerBounds = lowerBounds;
		this.upperBounds = upperBounds;
		this.parent = parent;
		//checks
		checkBounds(lowerBounds, upperBounds);
	}
	
	public void setLeftChild(CubicCoverNode lChild){
		assert(this.leftChild == null);
		this.leftChild = lChild;
	}
	
	public void setRightChild(CubicCoverNode rChild){
		assert(this.rightChild == null);
		this.rightChild = rChild;
	}

	/**
	 * Return the node region's lower bound.
	 * @return
	 */
	public ArrayList<Double> getLowerBounds() {
		return copyBound(lowerBounds); //copy is to enforce immutability of a node's region.
	}

	/**
	 * Return the node region's upper bound.
	 * @return
	 */
	public ArrayList<Double> getUpperBounds() {
		return copyBound(upperBounds); //copy is to enforce immutability of a node's region.
	}

	public CubicCoverNode getParent() {
		return parent;
	}

	public CubicCoverNode getLeftChild() {
		return leftChild;
	}

	public CubicCoverNode getRightChild() {
		return rightChild;
	}
	
	private ArrayList<Double> copyBound(ArrayList<Double> in){
		ArrayList<Double> out = new ArrayList<Double>();
		for(int i = 0; i< in.size(); ++i){
			double element = in.get(i);
			out.add(i,element);
		}
		return out;
	}
		
	/**
	 * 
	 * @param lowerBounds
	 * @param upperBounds
	 */
	private void checkBounds(ArrayList<Double> lowerBounds, ArrayList<Double> upperBounds){
		//System.out.println("dimension is " + Integer.toString(this.dimension));
		//System.out.println("lower bounds size " + Integer.toString(lowerBounds.size()));
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
	
}
