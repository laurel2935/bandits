package bandit.tictactoe;

import java.util.ArrayList;
import java.lang.Math;

public class PerfectPlayer extends Player {

    
    /*
     * Constructor.
     */
    public PerfectPlayer() {        
    }
    
    @Override
    public Move makeMove(Board board) {
        // Do an alpha-beta search.
        // Get list of child nodes.
        ArrayList<Move> moves = board.emptySquares();
        ArrayList<boardNode> nodes = new ArrayList<boardNode>();
        int currentPlayer = this.SIDE;
        for (int i = 0; i < moves.size(); i++) {
            // Put each new node in list.
            Board newBoard = board.clone();
            newBoard.makeMove(moves.get(i), currentPlayer);
            nodes.add(new boardNode(Board.oppositeSide(currentPlayer), newBoard,
                    moves.get(i)));
        }
        // Find the move with the best alpha value.
        Move bestMove = new Move();
        int bestAlpha = java.lang.Integer.MIN_VALUE;
        for (int i = 0; i < nodes.size(); i++) {
            int alphaNode = -this.alphaBetaNode(nodes.get(i),
                    java.lang.Integer.MIN_VALUE, java.lang.Integer.MAX_VALUE);
            if (alphaNode > bestAlpha) {
                bestAlpha = alphaNode;
                bestMove = nodes.get(i).move;
            }
        }
        return bestMove;
    }
    
    
    /*
     * Implements an alpha-beta search of the game tree.
     * @param node The current node.
     * @param alpha Current alpha value.
     * @param beta Current beta value.
     * @return alpha for this node.
     */
    public int alphaBetaNode(boardNode node, int alpha, int beta) {
        Board board = node.getBoard();
        // Check if we're at a leaf of the alpha-beta search tree
        if (board.gameOver())
            return node.score();
        // Get list of child nodes.
        ArrayList<Move> moves = board.emptySquares();
        ArrayList<boardNode> nodes = new ArrayList<boardNode>();
        int currentPlayer = node.currentPlayer;
        for (int i = 0; i < moves.size(); i++) {
            // Put each new node in list.
            Board newBoard = board.clone();
            newBoard.makeMove(moves.get(i), currentPlayer);
            nodes.add(new boardNode(Board.oppositeSide(currentPlayer), newBoard,
                    moves.get(i)));
        }
        // Update alpha for each child node
        for (int i = 0; i < nodes.size(); i++) {
            alpha = Math.max(alpha, -this.alphaBetaNode(nodes.get(i), -beta,
                             -alpha));
            if (beta <= alpha)
                break;
        }
        
        // Return alpha
        return alpha;   
    }
    
        
    /*
     * Represents a node in the alpha-beta search.
     */
    public class boardNode {
        
        // The player currently making a move.
        private int currentPlayer;
        
        // The current board.
        private Board currentBoard;
        
        // The move that led to this board.
        private Move move;

        /*
         * Constructor.
         * @param currentPlayer The player currently making a move.
         * @param board The current board.
         * @param move The move that led to this board.
         */
        public boardNode(int currentPlayer, Board currentBoard, Move move) {
            this.currentPlayer = currentPlayer;
            this.currentBoard = currentBoard;
            this.move = move;
            
        }
        
        /*
         * Returns the score of this node
         * @return -1 if lost game, 1 if won game, 0 if neither.
         */ 
        public int score(){
            int winner = this.currentBoard.winner();
            if (winner == this.currentPlayer)
                return 1; // This player wins
            else if (winner == 0)
                return 0; // No winner
            else
                return -1; // Other player wins
        }
        
        /*
         * Gets the current board.
         * @return A copy of the current board.
         */
        public Board getBoard() {
            return this.currentBoard.clone();
        }
    }
    

}
