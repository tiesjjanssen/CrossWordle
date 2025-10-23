package wordle;

import javax.swing.*;

public class GameLogic {

    public int wordLength;
    public int maxCols;
    public int maxRows; 
    public String targetWord;

    public final JPanel[][] squares = new JPanel[WordleConstants.MAX_GRID_DIM][WordleConstants.MAX_GRID_DIM]; 
    public final JLabel[][] letters = new JLabel[WordleConstants.MAX_GRID_DIM][WordleConstants.MAX_GRID_DIM];

    public JTextField inputField; 
    public JLabel titleLabel; 
    public JFrame frame;
    public int guesses;
    public int currentRow = 0;

    public GameLogic(int wordLength) {
        this.wordLength = wordLength;
        this.maxCols = wordLength;
        this.maxRows = (wordLength == 6) ? 6 : 5;
        this.guesses = maxRows;
        
        this.targetWord = WordSelector.selectRandomWord(wordLength);
        WordleGUIBuilder.createAndShowGUI(this);
    }

    public static void startGame(int wordLength) {
        // Correct way to start the Swing application
        javax.swing.SwingUtilities.invokeLater(() -> {  
            new GameLogic(wordLength); 
        });
    }

}
