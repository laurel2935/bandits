package bandit.tictactoe;

import java.util.ArrayList;

public class WeightsPlayer extends Player {

    // Weights on each square.
    double[][] weights;
    
    /*
     * Constructor.
     * @param weights A list of weights on each square, starting with the
     * top-left square and proceeding by columns first, then rows.
     */
    public WeightsPlayer(double[][] weights) {
        this.weights = new double[Board.BOARD_SIZE][Board.BOARD_SIZE];
        this.weights = weights.clone();
    }
    
    /*
     * Scores the given board, using this player's weights.
     * @param board The board to be scored.
     * @return The score of the board.
     */
    public double scoreBoard(Board board) {
        double score = 0;
        final int otherSide;
        if (this.SIDE == Board.O) {
            otherSide = Board.X;
        }
        else {
            otherSide = Board.O;
        }
        for (int r = 0; r < Board.BOARD_SIZE; r++) {
            for (int c = 0; c < Board.BOARD_SIZE; c++) {
                if (board.getEntry(r, c) == this.SIDE) {
                    score += this.weights[r][c];
                }
                else if (board.getEntry(r, c) == otherSide) {
                    score -= this.weights[r][c];
                }
            }
        }
        return score;
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
