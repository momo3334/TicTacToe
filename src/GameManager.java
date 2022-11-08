import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GameManager {
    //Member variables...
    private boolean running;            //Condition for the game loop.
    private Player[] m_players;         //List of players.
    private Board m_board;              //Current board.
    private int serialNumber = 2;       //Serial Number.
    private int currentPlayer = 0;
    private boolean win = false;
    private boolean m_boardChanged = false;
    private int m_nbMove = 0;

    /* private NetModule m_netModule;
     private Thread netThread;*/

    public GameManager() {
        this.m_board = new Board(this);
        this.m_board.addWindowListener(new CloseWindowEvent());
        this.m_board.setTitle("TicTacToe - Tour des " + this.getCurrentPiece());

    }

    public void doMove(int p_line, int p_col)
    {
        if (m_board.doMove(p_line, p_col, this.getCurrentPiece())){
            this.m_nbMove++;
            if (this.m_board.checkForWin(this.getCurrentPiece()) || this.m_nbMove >= this.m_board.getNbOfCase()){
                String gameMessage = "";
                if (this.m_nbMove >= this.m_board.getNbOfCase()){
                    gameMessage = "Partie nulle toutes les cases ont été jouées!\nVoulez-vous rejouer?";
                }
                else{
                    gameMessage = "Le joueur " + this.getCurrentPiece() + " à gagné!\nVoulez-vous rejouer?";
                }

                if(JOptionPane.showConfirmDialog(this.m_board, gameMessage,"WARNING",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                {
                    // YES option
                    this.resetGame();
                    return;
                }
                else
                {
                    // NO option
                    this.m_board.dispatchEvent(new WindowEvent(this.m_board, WindowEvent.WINDOW_CLOSING));
                }

            }
            this.changeTurns();
        }
    }

    private void resetGame() {
        this.m_nbMove = 0;
        this.currentPlayer = 0;
        this.m_board.resetBoard();
    }

    public void changeTurns()
    {
        if (currentPlayer == 0)
        {
            currentPlayer = 1;
        }
        else
        {
            currentPlayer = 0;
        }
        this.m_board.setTitle("TicTacToe - Tour des " + this.getCurrentPiece());
    }

    public char getCurrentPiece()
    {
        return currentPlayer == 0 ? 'X' : 'O';
    }

    private static class CloseWindowEvent extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);
            if (e.getSource() instanceof JFrame){
                JFrame closingFrame = (JFrame) e.getSource();
                closingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        }
    }
}