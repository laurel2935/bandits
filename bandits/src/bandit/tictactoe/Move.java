package bandit.tictactoe;

public class Move {

    // Stores one move, consisting of a location.
    private int row;
    private int col;
    
    /*
     * Constructor.
     * @param r Row
     * @param c Column
     */
    public Move(int r, int c){
        this.row = r;
        this.col = c;
    }
    
    /*
     * Gets the row of this move.
     * @return The row.
     */
    public int getRow() {
        return this.row;
    }
    
    /*
     * Gets the column of this move.
     * @return The column.
     */
    public int getCol() {
        return this.col;
    }
    
}
