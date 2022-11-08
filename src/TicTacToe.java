public class TicTacToe {
    //public static Thread t;
    GameManager game;
    public static boolean run;


    // Main entry point of the application.
    public static void main(String[] args) {
        TicTacToe ticTacToe = new TicTacToe();
    }

    public TicTacToe() {
        this.game = new GameManager();
    }
}