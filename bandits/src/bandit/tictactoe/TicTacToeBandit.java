package bandit.tictactoe;

import domain.DomainElement;
import java.util.Random;
import domain.RealVector;
import bandit.RNBandit;

public class TicTacToeBandit extends RNBandit {

    // Random number generator
    Random rand;
    
    
    public TicTacToeBandit() {
        // 9 weights, each one takes value between -1 and 1
        super(new double[][] {{-1, 1}, {-1, 1}, {-1, 1},
                              {-1, 1}, {-1, 1}, {-1, 1},
                              {-1, 1}, {-1, 1}, {-1, 1}});
        this.setRewardMax(1);
        this.rand = new Random();
    }
    
    @Override
    public double reward(DomainElement input) {
        double[][] weights = new double[Board.BOARD_SIZE][Board.BOARD_SIZE];
        double[] inputWeights = ((RealVector) input).getValue();
        // Transfer input weights to 2D array of weights
        for (int r = 0; r < Board.BOARD_SIZE; r++) {
            for (int c = 0; c < Board.BOARD_SIZE; c++) {
                weights[r][c] = inputWeights[3 * r + c];
            }
        }
        // Make our player
        WeightsPlayer ourPlayer = new WeightsPlayer(weights);
        
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
