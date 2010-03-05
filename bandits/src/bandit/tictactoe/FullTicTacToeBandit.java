package bandit.tictactoe;

import domain.DomainElement;
import domain.RNRange;

import java.util.Random;
import domain.RealVector;
import bandit.RNBandit;

public class FullTicTacToeBandit extends RNBandit {

    // Random number generator
    Random rand;
    
    
    public FullTicTacToeBandit() {
        // Set up the domain.
        // Number of weights needed.
        int dim = (int)Math.pow(3, Math.pow(Board.BOARD_SIZE, 2));
        double[][] bounds = new double[dim][2];
        // Put all weights in [0,1]
        for (int i = 0; i < dim; i++) {
            bounds[i][0] = 0;
            bounds[i][1] = 1;
        }
        this.domain = new RNRange(bounds);

        this.setRewardMax(1);
        this.rand = new Random();
    }
    
    @Override
    public double reward(DomainElement input) {
        // Get weights to give to FullWeightsPlayer
        double[] weights = ((RealVector) input).getValue();

        // Make our player
        FullWeightsPlayer ourPlayer = new FullWeightsPlayer(weights);
        
        // Set up a game.
        Game game;
        // Randomly choose who goes first.
        if (this.rand.nextInt(2) == 0) {
            game = new Game(ourPlayer, new RandomPlayer());
        }
        else {
            game = new Game(new RandomPlayer(), ourPlayer);
        }
        
        // Run the game.
        int result = game.run();
        
        // Interpret result.
        // Win
        if (result == ourPlayer.SIDE)
            return 1;
        // Tie
        else if (result == Board.BLANK)
            return .5;
        // Lose
        else
            return 0;
    }

}
