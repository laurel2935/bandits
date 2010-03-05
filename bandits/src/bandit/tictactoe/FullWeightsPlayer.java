package bandit.tictactoe;

import java.util.ArrayList;

/*
 * There is a weight/ranking associated with each possible board.  The mapping
 * from boards to indices is given by considering the board to be a
 * number in ternary, with the ones digit the top-left square.
 * X = 2
 * O = 1
 * blank = 0
 */
public class FullWeightsPlayer extends Player {

    // Weights on each board.
    double[] weights;
    
    /*
     * Constructor.
     * @param weights A list of weights for each board.
     */
    public FullWeightsPlayer(double[] weights) {
        assert(weights.length == Math.pow(3, Math.pow(Board.BOARD_SIZE, 2))); 
        this.weights = weights.clone();
    }
    
    /*
     * Scores the given board, using this player's weights.
     * @param board The board to be scored.
     * @return The score of the board.
     */
    public double scoreBoard(Board board) {
        int index = 0;
        for (int r = 0; r < Board.BOARD_SIZE; r++) {
            for (int c = 0; c < Board.BOARD_SIZE; c++) {
                int power = r * Board.BOARD_SIZE + c; // Power of 3 to use
                // Get the value of this digit
                int digit;                
                if (board.getEntry(r, c) == Board.X)
                    digit = 2;
                else if (board.getEntry(r, c)== Board.O)
                    digit = 1;
                else
                    digit = 0;
                // Add the contribution of this digit to the index
                index += digit * Math.pow(Board.BOARD_SIZE, power); 
            }
        }
        // Get weight from weights for this player
        return this.weights[index];
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
        assert(moves.size() > 0);
        
        // Find the move with the best score.
        double bestScore = Double.NEGATIVE_INFINITY;
        Move bestMove = new Move(0, 0);
        for (int i = 0; i < moves.size(); i++) {
            board.makeMove(moves.get(i), this.SIDE);
            // Check for new best score.
            if (this.scoreBoard(board) > bestScore) {
                bestMove = moves.get(i);
                bestScore = this.scoreBoard(board);
            }
            // Unmake move
            board.makeMove(moves.get(i), Board.BLANK);
        }
        return bestMove;
    }

}
