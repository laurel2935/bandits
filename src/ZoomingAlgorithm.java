import java.util.ArrayList;


public class ZoomingAlgorithm extends Algorithm {
	
	private class Arm{
		private double identity;
		private int n = 0;
		private double r = 0;
		public Arm(double input){
			identity = input;
		}
		public double getIdentity(){
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
	
	// The list of active arms, as chosen by the oracle
	// I'm not yet sure how we're representing our arms
	private ArrayList<Arm> active;
	
	
	//The current average reward for an active arm
	private double mt(Arm v){
		return v.mt();
	}
	
	//??????????????
	//I think this needs to be able to cover everything with
	//balls that shrink in radius each round.
	public ArrayList<Arm> coveringOracle(){
		return null;
	}
	
	
	// Index of a strategy (arm)
	private double It(Arm v){
		return v.mt()+2*v.rt();
	}
	public void runPhase(){		
		for(int r = 0; r<Math.pow(2, iph);r++){
			runRound();
		}
		iph ++;
	}
	private void runRound(){
		// add active arms if necesssary
		active = coveringOracle();
		//Find the highest index arm
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
		active.get(maxI).play(reward(active.get(maxI).getIdentity()));
	}

	@Override
	public DomainElement makeChoice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void recieveReward(double reward) {
		// TODO Auto-generated method stub
		
	}
}
