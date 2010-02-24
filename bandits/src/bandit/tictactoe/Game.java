package bandit.tictactoe;

public class Game {

    // Players
    public Player p1;
    public Player p2;
    
    // Board
    public Board board;
    
    /*
     *  Constructor.
     *  @param p1 Player 1.
     *  @param p2 Player 2.
     */
    public Game(Player p1, Player p2) {
        this.p1 = p1;
        this.p1.setSide(Board.X); // Player 1 is X
        this.p2 = p2;
        this.p2.setSide(Board.O); // Player 2 is O
        this.board = new Board();
    }
    
    /*
     * Runs the game.
     * @return Whoever won the game, according to player definitions in Board.
     */
    public int run() {
        // Player 1 plays first, and is X.
        while (true) {
            if (this.board.gameOver()) {
                break;
            }
            Move m1 = p1.makeMove(this.board.clone());
            this.board.makeMove(m1, this.p1.getSide());
            
            if (this.board.gameOver()) {
                break;
            }
            Move m2 = p2.makeMove(this.board.clone());
            this.board.makeMove(m2, this.p2.getSide());   
        }
        // Done with game.
        return this.board.winner();
    }
    
}
