import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;

public class CrossWordle1 extends JFrame {
    private static final int SIZE = 4; // 8x8 grid
    private static final Color GREEN = new Color(106, 170, 100);
    private static final Color YELLOW = new Color(201, 180, 88);
    private static final Color GRAY = new Color(120, 124, 126);

    private JPanel[][] squares = new JPanel[SIZE][SIZE];
    private JLabel[][] letters = new JLabel[SIZE][SIZE];
    private JTextField inputField;

    private static final String[] wordlist = {"DELAY", "ERROR", "ANGEL", "FRAME", "FINAL", "ACTOR", "BROWN", "DEVIL", "FRUIT", "APPLE", "MANGO"};
    Random randomNumber = new Random();
    String targetword = wordlist.get[randomNumber];
    private int currentRow = 0;

    public CrossWordle1() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(512, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- Title panel ---
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(220, 220, 220));
        titlePanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        JLabel titleLabel = new JLabel("CROSSWORDLE");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // --- Grid panel ---
        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE, 1, 1));
        gridPanel.setBackground(Color.GRAY);
        gridPanel.setBorder(new EmptyBorder(16, 16, 16, 16));

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                squares[r][c] = new JPanel(new BorderLayout());
                squares[r][c].setBackground(Color.WHITE);
                squares[r][c].setBorder(new LineBorder(Color.BLACK));

                letters[r][c] = new JLabel("", SwingConstants.CENTER);
                letters[r][c].setFont(new Font("Arial", Font.BOLD, 24));
                squares[r][c].add(letters[r][c]);
                gridPanel.add(squares[r][c]);
            }
        }
        add(gridPanel, BorderLayout.CENTER);

        // --- Input panel ---
        JPanel inputPanel = new JPanel(new BorderLayout(8, 8));
        inputPanel.setBorder(new EmptyBorder(8, 16, 16, 16));
        inputPanel.setBackground(new Color(240, 240, 240));

        inputField = new JTextField();
        JButton sendButton = new JButton("Send");

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> handleGuess());
        inputField.addActionListener(e -> handleGuess());

        setVisible(true);
    }

    private void handleGuess() {
        String guess = inputField.getText().trim().toUpperCase();
        if (guess.length() != targetWord.length()) {
            JOptionPane.showMessageDialog(this, "Guess must be " + targetWord.length() + " letters.");
            return;
        }

        if (currentRow >= SIZE) {
            JOptionPane.showMessageDialog(this, "No more guesses!");
            return;
        }

        // Color logic (Wordle-like)
        for (int i = 0; i < guess.length(); i++) {
            letters[currentRow][i].setText(String.valueOf(guess.charAt(i)));

            if (guess.charAt(i) == targetWord.charAt(i)) {
                squares[currentRow][i].setBackground(GREEN);
            } else if (targetWord.contains(String.valueOf(guess.charAt(i)))) {
                squares[currentRow][i].setBackground(YELLOW);
            } else {
                squares[currentRow][i].setBackground(GRAY);
            }
        }

        if (guess.equals(targetWord)) {
            JOptionPane.showMessageDialog(this, "🎉 You solved it!");
        }

        currentRow++;
        inputField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CrossWordle1());
    }
}
