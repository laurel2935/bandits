package bandit.tictactoe;

import java.util.ArrayList;
import java.util.Random;

public class RandomPlayer extends Player {

    // Random number generator
    private Random rand;

    /*
     * Constructor.
     */
    public RandomPlayer() {
       this.rand = new Random(); 
    }
    
    
    @Override
    public Move makeMove(Board board) {
        ArrayList<Move> moves = new ArrayList<Move>();
        // Find all valid moves
        for (int r = 0; r < Board.BOARD_SIZE; r++) {
            for (int c = 0; c < Board.BOARD_SIZE; c++) {
                if (board.getEntry(r, c) == Board.BLANK){
                    moves.add(new Move(r, c));
                }
            }
        }

        // Choose a random valid move
        int move_index = this.rand.nextInt(moves.size());
        return moves.get(move_index);
    }

}
