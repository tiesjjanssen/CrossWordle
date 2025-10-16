import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.ActionListener;

public class Wordle { // ONLY ONE CLASS DEFINITION

    // --- Wordle CONSTANTS (SIZE=5 for grid, but targetWord is 4) ---
    private static final int MAX_ROWS = 4; // Grid size (number of guesses)
    private static final int MAX_COLS = 4; // Grid size (word length)
    
    // The fixed answer is "JAVA", which is 4 letters, conflicting with MAX_COLS=5
    private static final String targetWord = "JAVA"; 
    private static final int WORD_LENGTH = targetWord.length(); // Use target word length

    // --- GUI Components ---
    // Change array size to match the maximum possible guesses/word length
    private final JPanel[][] squares = new JPanel[MAX_ROWS][MAX_COLS]; 
    private final JLabel[][] letters = new JLabel[MAX_ROWS][MAX_COLS];
    
    // MAKE THESE FIELDS ACCESSIBLE TO ALL METHODS
    private JTextField inputField; 
    private JLabel titleLabel; 
    private JFrame frame; // Reference to the main frame for dialogs
    
    // --- Game State ---
    private int guesses = MAX_ROWS;
    private int currentRow = 0;

    // --- Custom Colors for Wordle Feedback ---
    // DEFINE COLORS so they can be used in guess()
    private static final Color GREEN = new Color(106, 170, 100);    // Correct letter, correct position
    private static final Color YELLOW = new Color(201, 180, 88);   // Correct letter, wrong position
    private static final Color GRAY = new Color(120, 124, 126);    // Letter not in word
    private static final Color BG_DEFAULT = Color.WHITE;

    public Wordle() {
        // The constructor now calls the GUI setup method
        createAndShowGUI();
    }

    public void createAndShowGUI() { // Renamed from wordle() for clarity
        frame = new JFrame("CrossWordle @Ties & Caitlyn (Answer: " + targetWord + ")");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(512, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // --- Title box at the top ---
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(220, 220, 220));
        titlePanel.setBorder(new EmptyBorder(5, 0, 5, 0));

        // Use the class variable titleLabel
        titleLabel = new JLabel("Number of guesses left: " + guesses); 
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titlePanel.add(titleLabel);

        // --- Grid panel ---
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(MAX_ROWS, MAX_COLS, 6, 6)); 
        gridPanel.setBackground(Color.DARK_GRAY);
        gridPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        for (int r = 0; r < MAX_ROWS; r++) {
            for (int c = 0; c < MAX_COLS; c++) {
                squares[r][c] = new JPanel();
                squares[r][c].setBackground(BG_DEFAULT);
                squares[r][c].setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
                squares[r][c].setLayout(new GridBagLayout()); 

                letters[r][c] = new JLabel("", SwingConstants.CENTER);
                letters[r][c].setFont(new Font("Arial", Font.BOLD, 48)); 
                letters[r][c].setForeground(Color.BLACK);
                squares[r][c].add(letters[r][c]);

                gridPanel.add(squares[r][c]);
            }
        }

        // --- Input panel at the bottom ---
        JPanel inputPanel = new JPanel(new BorderLayout(8, 8));
        inputPanel.setBorder(new EmptyBorder(10, 16, 10, 16));
        inputPanel.setBackground(new Color(240, 240, 240));

        // Use the class variable inputField
        inputField = new JTextField(); 
        inputField.setFont(new Font("Arial", Font.PLAIN, 18));
        JButton sendButton = new JButton("Send Guess");
        
        // Attach the guess method to the button and the text field (Enter key)
        ActionListener guessAction = e -> guess();
        sendButton.addActionListener(guessAction);
        inputField.addActionListener(guessAction);


        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // --- Add all to frame ---
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void guess() {
        // Use frame for JOptionPane parent component
        if (currentRow >= MAX_ROWS) {
            JOptionPane.showMessageDialog(frame, "No more guesses! The word was " + targetWord);
            return;
        }
        
        String guess = inputField.getText().trim().toUpperCase();

        if (guess.length() != WORD_LENGTH) {
            JOptionPane.showMessageDialog(frame, "Guess must be " + WORD_LENGTH + " letters.");
            return;
        }
        
        // --- Wordle Color Logic ---
        
        // Use a flag for robust duplicate handling
        boolean[] targetUsed = new boolean[WORD_LENGTH];
        
        // 1. First Pass: Check for GREEN
        for (int i = 0; i < WORD_LENGTH; i++) {
            letters[currentRow][i].setText(String.valueOf(guess.charAt(i)));
            
            if (guess.charAt(i) == targetWord.charAt(i)) {
                squares[currentRow][i].setBackground(GREEN);
                letters[currentRow][i].setForeground(Color.WHITE);
                targetUsed[i] = true;
            } else {
                 // Set to default for now
                squares[currentRow][i].setBackground(BG_DEFAULT); 
                letters[currentRow][i].setForeground(Color.BLACK);
            }
        }

        // 2. Second Pass: Check for YELLOW and GRAY
        for (int i = 0; i < WORD_LENGTH; i++) {
            // Only process non-green squares
            if (squares[currentRow][i].getBackground() != GREEN) {
                char charGuess = guess.charAt(i);
                boolean foundYellow = false;
                
                // Check if the character exists elsewhere in the target word
                for(int j = 0; j < WORD_LENGTH; j++) {
                    if (!targetUsed[j] && charGuess == targetWord.charAt(j)) {
                        squares[currentRow][i].setBackground(YELLOW);
                        letters[currentRow][i].setForeground(Color.WHITE);
                        targetUsed[j] = true; // Mark this target letter as used
                        foundYellow = true;
                        break;
                    }
                }
                
                // If not found (and not green), it must be gray
                if (!foundYellow) {
                    squares[currentRow][i].setBackground(GRAY);
                    letters[currentRow][i].setForeground(Color.WHITE);
                }
            }
        }

        // --- Update Game State ---
        
        if (guess.equals(targetWord)) {
            JOptionPane.showMessageDialog(frame, "🎉 You solved it!");
            currentRow = MAX_ROWS; // End the game
        } else {
            currentRow++;
            guesses--;
            titleLabel.setText("Number of guesses left: " + guesses);
            
            if (currentRow >= MAX_ROWS) {
                 JOptionPane.showMessageDialog(frame, "😢 Game Over! The word was " + targetWord);
            }
        }

        inputField.setText("");
        inputField.requestFocusInWindow();
    }

    public static void main(String[] args) {
        // Correct way to start the Swing application
        SwingUtilities.invokeLater(() -> {
            new Wordle(); // The constructor calls createAndShowGUI()
        });
    }
}