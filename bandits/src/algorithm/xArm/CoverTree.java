package algorithm.xArm;


public interface CoverTree<Node extends CoverNode> {
	public Node pick();
	public void update(Node node, double reward);
}
