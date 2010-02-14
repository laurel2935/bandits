import java.util.ArrayList;


public class ZoomingAlgorithm extends Algorithm {
	private CoveringOracle oracle;
	public ZoomingAlgorithm(Domain d){
		domain = d;
		oracle = domain.getCoveringOracle();
	}
	private class Arm{
		private DomainElement identity;
		private int n = 0;
		private double r = 0;
		public Arm(DomainElement input){
			identity = input;
		}
		public DomainElement getIdentity(){
			return identity;
		}
		
		// Called to indicate that the arm was played
		public void play(double reward){
			n++;
			r+=reward;
		}
		
		//The average reward
		public double mt(){
			return r/n;			
		}
		
		//The number of times the arm has been played in a phase
		public double nt(){
			return n;			
		}
		
		// the confidence radius for this arm
		public double rt(){
			return Math.sqrt(8*iph/(2+nt()));
		}
	}
	
	private int iph = 1; //phase i
	private int round = 1; //round number of the given phase
	private Arm chosen = null;
	
	// The list of active arms, as chosen by the oracle
	// I'm not yet sure how we're representing our arms
	private ArrayList<Arm> active;
	
	
	// Creates a complete covering given the set of active arms
	public ArrayList<Arm> getCovering(ArrayList<Arm> list){
		oracle.clearCovering();
		for(Arm arm:list)
			//add arm and radius to oracle
			oracle.addElement(arm.getIdentity(), arm.rt());
		DomainElement uncovered = oracle.getUncoveredElement();
		while(uncovered != null){
			Arm newArm = new Arm(uncovered);
			list.add(newArm);
			oracle.addElement(newArm.getIdentity(), newArm.rt());
			uncovered = oracle.getUncoveredElement();
		}
		return list;
	}
	
	
	// Index of a strategy (arm)
	private double It(Arm v){
		return v.mt()+2*v.rt();
	}

	@Override
	public DomainElement makeChoice() {
		// Determine if the phase has ended
		if(round > Math.pow(2, iph)){
			iph ++;
			round = 1;
		}
		// Reset active strategies if a new phase has begun
		if(round == 1)
			active = new ArrayList<Arm>();
		
		// Cover all strategies
		active = getCovering(active);
		
		int maxI = 0;
		double maxV = It(active.get(0));
		for(int v = 1; v < active.size(); v++){
			double nextIt = It(active.get(v));
			if(nextIt>maxV){
				maxI = v;
				maxV = nextIt;
			}				
		}
		// Play arm of highest index
		//active.get(maxI).play(reward(active.get(maxI).getIdentity()));
		chosen = active.get(maxI);
		return active.get(maxI).getIdentity();
	}

	@Override
	public void recieveReward(double reward) {
		// TODO Auto-generated method stub
		chosen.play(reward);
	}
}
