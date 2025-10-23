package wordle;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class EndGameDialogWordle { 
    
    public static void show(GameLogic game, boolean win) {
        game.frame.setVisible(false);
        JDialog dialog = new JDialog(game.frame, null, true); 
        dialog.setSize(350, 200);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setLocationRelativeTo(game.frame); 
        dialog.getContentPane().setBackground(new Color(240, 240, 240));
        dialog.setResizable(false);

        // Bericht opmaak
        String message;
        if (win) {
            message = "You solved it in " + (game.maxRows - game.guesses) + " guess(es)!";
        } else {
            message = "Game Over! The word was: ";
        }

        // Titel Label
        JLabel statusLabel = new JLabel((win ? "CONGRATULATIONS" : "GAME OVER"), SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        dialog.add(statusLabel, BorderLayout.NORTH);

        // Hoofdtekst/antwoord label
        JLabel answerLabel = new JLabel(message + (win ? "" : game.targetWord), SwingConstants.CENTER);
        answerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        answerLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
        dialog.add(answerLabel, BorderLayout.CENTER);
        
        // OK knop
        JButton okButton = new JButton("Play Again (Start Screen)");
        okButton.addActionListener(e -> {
            dialog.dispose(); // Sluit de dialoog
            frame.dispose(); // Sluit het Wordle frame
            StartScreen.showStartScreen(); //start het startscherm opnieuw
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

}