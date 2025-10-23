package wordle;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;

public class WordleGUIBuilder {
    
    public static void createAndShowGUI(GameLogic game) { 
        game.frame.setVisible(false);
        game.frame = new JFrame (game.wordLength + " letter wordle"); // get rid of targetword at end 
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setSize(WordleConstants.WIDTH, WordleConstants.HEIGHT);
        game.frame.setLocationRelativeTo(null);
        game.frame.setLayout(new BorderLayout());

        // --- Title box at the top ---
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(220, 220, 220));
        titlePanel.setBorder(new EmptyBorder(5, 0, 5, 0));

        // Use the class variable titleLabel
        game.titleLabel = new JLabel("Number of guesses left: " + game.guesses); 
        game.titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        game.titleLabel.setForeground(Color.BLACK);
        titlePanel.add(game.titleLabel);

        // --- Grid panel ---
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(game.maxRows, game.maxCols, 6, 6));
        gridPanel.setBackground(Color.DARK_GRAY);
        gridPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        for (int r = 0; r < game.maxRows; r++) {
            for (int c = 0; c < game.maxCols; c++) {
                game.squares[r][c] = new JPanel();
                game.squares[r][c].setBackground(WordleConstants.WHITE);
                game.squares[r][c].setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
                game.squares[r][c].setLayout(new GridBagLayout()); 

                game.letters[r][c] = new JLabel("", SwingConstants.CENTER);
                game.letters[r][c].setFont(new Font("Arial", Font.BOLD, 48)); 
                game.letters[r][c].setForeground(Color.BLACK);
                game.squares[r][c].add(game.letters[r][c]);

                gridPanel.add(game.squares[r][c]);
            }
        }

        // --- Input panel at the bottom ---
        JPanel inputPanel = new JPanel(new BorderLayout(8, 8));
        inputPanel.setBorder(new EmptyBorder(10, 16, 10, 16));
        inputPanel.setBackground(new Color(240, 240, 240));

        // Use the class variable inputField
        game.inputField = new JTextField(); 
        game.inputField.setFont(new Font("Arial", Font.PLAIN, 18));
        JButton sendButton = new JButton("Send Guess");
        
        // Attach the guess method to the button and the text field (Enter key)
        ActionListener guessAction = e -> WordleLogic.processGuess(game);
        sendButton.addActionListener(guessAction);
        game.inputField.addActionListener(guessAction);


        inputPanel.add(game.inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // --- Add all to frame ---
        game.frame.add(titlePanel, BorderLayout.NORTH);
        game.frame.add(gridPanel, BorderLayout.CENTER);
        game.frame.add(inputPanel, BorderLayout.SOUTH);

        game.frame.setVisible(true);
    }
}