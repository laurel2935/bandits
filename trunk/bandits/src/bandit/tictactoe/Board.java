package bandit.tictactoe;

/*
 * Implements a tic-tac-toe board.
 */
public class Board {
    // The board -1 = X, 0 = blank, 1 = O.
    private int[][] board;
    
    // Size of board.
    private static final int BOARD_SIZE = 3;
    
    // Values
    public static final int X = -1;
    public static final int BLANK = 0;
    public static final int O = 1;
    
    /*
     * Constructor.
     */
    public Board() {
        // Make a blank board.
        this.board = new int[BOARD_SIZE][BOARD_SIZE];
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                this.board[r][c] = 0;
            }
        }
    }
    
    /*
     * Gets an entry in a board.
     * @param row Row of entry to get.
     * @param col Column of entry to get.
     * @return The value in that entry.
     */
    public int getEntry(int row, int col) {
        return this.board[row][col];
    }
        
    /*
     * Sets an entry in a board.
     * @param row Row of entry to set.
     * @param col Column of entry to set.
     * @param val Value to set to.
     */
    public void setEntry(int row, int col, int val) {
        assert(val == O || val == X || val == BLANK);
        this.board[row][col] = val;
    }

    
    /*
     * Determines whether the given move is valid.
     * @param m The given move.
     * @return True if the move is valid (board is empty at that location).
     */
    public boolean validMove(Move m) {
        int r = m.getRow();
        int c = m.getCol();
        return (this.board[r][c] == BLANK);        
    }
    
    /*
     * Sets an entry in the board, given a move and value.
     * @param m The move (location)
     * @param val Value to set to.
     */
    public void makeMove(Move m, int val) {
        int r = m.getRow();
        int c = m.getCol();
        assert(this.board[r][c] == BLANK);
        this.setEntry(r, c, val);
    }
    
    /*
     * Checks if the game is over, one way or another.
     * @return True if the game is over, False otherwise.
     */
    public boolean gameOver() {
        // Check if someone has won.
        if (winner() != BLANK) {
            return true;
        }
        // See how many blanks there are.
        int blanks = 0;
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                if (this.board[r][c] == BLANK) {
                    blanks++;
                }
            }
        }
        return (blanks == 0); // All board spaces are filled.
    }
    
    /*
     * Determines if any player has won the game on this board.  Assumes that
     * only one player could have won.
     * @return The value of the winner, or BLANK if no winner.
     */
    public int winner() {
        // Check starting positions of a three-in-a-row
        int[] values = {X, O};
        for (int v = 0; v < values.length; v++) {
            int value = values[v];
            // Possible offsets to go from a starting position.
            int[][] offsets = {{0, 1}, {1, 0}, {1, 1}}; 
            for (int r = 0; r < BOARD_SIZE; r++) {
                for (int c = 0; c < BOARD_SIZE; c++) {
                    // Check in each direction from position.
                    for (int o = 0; o < offsets.length; o++) {
                        int offset_r = offsets[o][0];
                        int offset_c = offsets[0][1];
                        boolean isValue = true;
                        // Check each position in the 3-in-a-row.
                        for (int off = 0; off < 3; off++) {
                            int r_check = r + offset_r * off;
                            int c_check = c + offset_c * off;
                            // Check if this is a valid position.
                            if (r_check >= BOARD_SIZE || c_check >= BOARD_SIZE) {
                                isValue = false;
                                break;
                            }
                            // Check if this entry is the same as 'value'.
                            isValue = isValue &&
                                      (this.board[r_check][c_check] == value);
                        }
                        // Check if this was a 3-in-a-row
                        if (isValue)
                            return value;
                    }
                }
            }
        }
        // No winner.
        return BLANK;
    }
}
