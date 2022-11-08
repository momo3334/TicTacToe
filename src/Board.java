import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Board extends JFrame {
    private int m_boardSize;               //Width(in Cases) of the board.
    private Case[] m_cases;                 //Array of all the cases in the board.
    private int m_cursorRow;                //The current row on which the cursor is located.
    private int m_cursorColumn;             //The current column on which the cursor is located.
    private char m_currentPiece;            //Current Piece.

    private String m_currentPlayerName;     //Name of current player.
    private GameManager m_gameManager;

    //Creates a new board with the default dimensions of 3 by 3.
    public Board(GameManager p_gameManager)
    {
        this.setSize(663,612);
        this.setVisible(true);
        this.setLocationRelativeTo(null); // centré

        this.m_gameManager = p_gameManager;

        this.m_cursorRow = 0;
        this.m_cursorColumn = 0;

        this.m_boardSize = 6;

        this.m_currentPiece = 'X';
        this.m_cases = new Case[(int) Math.pow(this.m_boardSize, 2)];
        this.setMinimumSize(new Dimension(m_cases.length * 12, m_cases.length * 12));

        // Layout that will contain all the cases in the board.
        GridLayout boardGrid = new GridLayout(this.m_boardSize, this.m_boardSize);
        this.setLayout(boardGrid);

        for (int i = 0; i < this.m_cases.length; i++) {
            int xCaseCoordinate = i / this.m_boardSize;
            int yCaseCoordinate = i % this.m_boardSize;

            this.m_cases[i] = new Case(xCaseCoordinate, yCaseCoordinate);
            this.m_cases[i].addMouseListener(new CaseClicked());
            this.add(this.m_cases[i]);
        }
    }

    public boolean doMove(int p_line, int p_col, char p_piece) {
        Case playedCase = this.getCase(p_line, p_col);

        if (playedCase == null || !playedCase.isEmpty()){
            return false;
        }

        playedCase.setContent(String.valueOf(p_piece));
        return true;
    }

    private Case getCase(int p_line, int p_col) {
        if (p_line < this.m_boardSize && p_col < this.m_boardSize)
        {
            int indexInArray;

            indexInArray = (this.m_boardSize * p_line) + p_col;

            return m_cases[indexInArray];
        }
        else
        {
            return null;
        }
    }

    public int getNbOfCase(){
        return this.m_cases.length;
    }

    public boolean checkForWin(char p_checkedPiece)
    {
        return checkForVerticalLine(p_checkedPiece, 0, 0)
                || checkForHorizontalLine(p_checkedPiece, 0,0)
                || checkForDiagonalLine(p_checkedPiece, 0, 0, 1)
                || checkForDiagonalLine(p_checkedPiece, 0, this.m_boardSize -1, -1);
    }

    // Recursively check case's content in a vertical line to and returns true if a vertical line is found
    public boolean checkForVerticalLine(char p_checkedPiece, int p_line, int p_col ){
        int nextCaseLine = p_line + 1;
        int nextCaseCol = p_col;

        if (p_line == m_boardSize - 1) {
            return true;
        }

        if (p_col < m_boardSize){
            boolean nextCaseIsSameSymbol = compareCaseValues(p_line, p_col, nextCaseLine, nextCaseCol);

            if (nextCaseIsSameSymbol)
            {
                return checkForVerticalLine(p_checkedPiece, nextCaseLine, nextCaseCol);
            }
            else{
                return checkForVerticalLine(p_checkedPiece, 0, p_col + 1);
            }
        }
        else {
            return false;
        }
    }


    // Recursively check case's content in a horizontal line to and returns true if a horizontal line is found
    public boolean checkForHorizontalLine(char p_checkedPiece, int p_line, int p_col ){
        int nextCaseLine = p_line;
        int nextCaseCol = p_col + 1;

        if (p_col == m_boardSize - 1) {
            return true;
        }

        if (p_line < m_boardSize){
            boolean nextCaseIsSameSymbol = compareCaseValues(p_line, p_col, nextCaseLine, nextCaseCol);

            if (nextCaseIsSameSymbol)
            {
                return checkForHorizontalLine(p_checkedPiece, nextCaseLine, nextCaseCol);
            }
            else{
                return checkForHorizontalLine(p_checkedPiece, p_line + 1, 0);
            }
        }
        else {
            return false;
        }
    }

    // Recursively check case's content in a diagonal line to and returns true if a diagonal line is found
    public boolean checkForDiagonalLine(char p_checkedPiece, int p_line, int p_col, int direction){
        int nextCaseLine = p_line + 1;
        int nextCaseCol = p_col + direction;

        if (p_line == m_boardSize - 1) {
            return true;
        }

        boolean nextCaseIsSameSymbol = compareCaseValues(p_line, p_col, nextCaseLine, nextCaseCol);

        if (nextCaseIsSameSymbol)
        {
            return checkForDiagonalLine(p_checkedPiece, nextCaseLine, nextCaseCol, direction);
        }
        else
        {
            return false;
        }
    }

    // Check three values to see if they are the same. If so, we have a winner.
    boolean compareCaseValues(int p_xCheckedCaseOne,int p_yCheckedCaseOne, int p_xCheckedCaseTwo, int p_yCheckedCaseTwo)
    {
        Case checkedCaseOne = this.getCase(p_xCheckedCaseOne, p_yCheckedCaseOne);
        Case checkedCaseTwo = this.getCase(p_xCheckedCaseTwo, p_yCheckedCaseTwo);

        if (checkedCaseTwo.isEmpty()){
            return false;
        }
        return checkedCaseOne.getCharContent() == checkedCaseTwo.getCharContent();
    }

    public void resetBoard() {
        for (Case currentCase:
             this.m_cases) {
            currentCase.setContent(" ");
        }
    }

    private class CaseClicked implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getSource() instanceof Case){
                Case clickedCase = (Case) e.getSource();
                if (m_gameManager != null){
                    m_gameManager.doMove(clickedCase.getXCoordinate(), clickedCase.getYCoordinate());
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
