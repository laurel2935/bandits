package bandit.multiplayer;

import domain.Domain;
import domain.DomainElement;
import domain.RealNumber;
import domain.RealRange;
import bandit.MultiplayerBandit;

/*
 * This class represents the generalization of the prisoner's dilemma game
 * to choices being in real numbers in [0,1]
 */
public class PrisonerBandit extends MultiplayerBandit {

    /*
     * Constructor. 
     */
    public PrisonerBandit() {
        super(new Domain[] {new RealRange(0, 1), new RealRange(0, 1)});
    }
    
    
    @Override
    public double[] reward(DomainElement[] input) {
        /* We use as our reward function
         * r1(x, y) =  1/3 x - 2/3 y + 2/3
         * r2(x, y) = -2/3 x + 1/3 y + 2/3
         * Where the input vector is [x, y]
         * 
         * These reward function have the properties
         * {r1(0,0), r2(0,0)} = {2/3, 2/3}
         * {r1(1,0), r2(1,0)} = {1, 0}
         * {r1(0,1), r2(0,1)} = {0, 1}
         * {r1(1,1), r2(1,1)} = {1/3, 1/3}
         * 
         * Which is what we want for a prisoner's dilemma-type problem.
         */
        double x = ((RealNumber)input[0]).getValue();
        double y = ((RealNumber)input[1]).getValue();
        double[] r = new double[2];
        r[0] =  1. / 3 * x - 2. / 3 * y + 2. / 3;
        r[1] = -2. / 3 * x + 1. / 3 * y + 2. / 3;
        return r;
    }
}
