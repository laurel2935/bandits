package bandit.tictactoe;

public abstract class Player {

    /*
     * Make a move, given the current board.
     * @param board The current board.
     * @return The position to play.
     */
    public abstract Move makeMove(Board board); // Makes a move
    
    private int SIDE; // O or X.
    
    /*
     * Sets this player's side.
     * @param side The side to set to.
     */
    public void setSide(int side) {
        this.SIDE = side;
    }
    
    /*
     * Gets this player's side.
     * @return side The side of this player
     */
    public int getSide() {
        return this.SIDE;
    }
}
