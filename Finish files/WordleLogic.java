package wordle;

import java.awt.Color;
import javax.swing.JOptionPane;

public class WordleLogic {

    public static void processGuess(GameLogic game) {
        // Use frame for JOptionPane parent component
        if (game.currentRow >= game.maxRows) {
            return;
        }
        
        String guess = game.inputField.getText().trim().toUpperCase();

        if (guess.length() != game.wordLength) {
            JOptionPane.showMessageDialog(game.frame, "Guess must be " + game.wordLength + " letters.");
            return;
        }
        
        // Here we implement ordle Color Logic 
        // Use a flag for robust duplicate handling
        boolean[] targetUsed = new boolean[game.wordLength];
        
        // 1. First Pass: Check for GREEN
        for (int i = 0; i < game.wordLength; i++) {
            game.letters[game.currentRow][i].setText(String.valueOf(guess.charAt(i)));
            
            if (guess.charAt(i) == game.targetWord.charAt(i)) {
                game.squares[game.currentRow][i].setBackground(WordleConstants.GREEN);
                game.letters[game.currentRow][i].setForeground(Color.WHITE);
                targetUsed[i] = true;
            } else {
                game.squares[game.currentRow][i].setBackground(WordleConstants.WHITE); 
                game.letters[game.currentRow][i].setForeground(WordleConstants.BLACK);
            }
        }

        // 2. Second Pass: Check for YELLOW and GRAY
        for (int i = 0; i < game.wordLength; i++) {
            // Only process non-green squares
            if (game.squares[game.currentRow][i].getBackground() != WordleConstants.GREEN) {
                char charGuess = guess.charAt(i);
                boolean foundYellow = false;
                
                // Check if the character exists elsewhere in the target word
                for (int j = 0; j < game.wordLength; j++) {
                    if (!targetUsed[j] && charGuess == game.targetWord.charAt(j)) {
                        game.squares[game.currentRow][i].setBackground(WordleConstants.YELLOW);
                        game.letters[game.currentRow][i].setForeground(Color.WHITE);
                        targetUsed[j] = true; // Mark this target letter as used
                        foundYellow = true;
                        break;
                    }
                }
                
                // If not found (and not yellow), it must be gray
                if (!foundYellow) {
                    game.squares[game.currentRow][i].setBackground(WordleConstants.GRAY);
                    game.letters[game.currentRow][i].setForeground(Color.WHITE);
                }
            }
        }

        // Output if the user quessed the word or did not solve it.
        
        if (guess.equals(game.targetWord)) {
            game.guesses--;
            game.titleLabel.setText("Number of guesses left: " + game.guesses);
            EndGameDialog.show(game, true);
            game.currentRow = game.maxRows; // End the game
        } else {
            game.currentRow++;
            game.guesses--;
            game.titleLabel.setText("Number of guesses left: " + game.guesses);
            
            if (game.currentRow >= game.maxRows) {
                EndGameDialog.show(game, false);
            }
        }

        game.inputField.setText("");
        game.inputField.requestFocusInWindow();
    }

}
