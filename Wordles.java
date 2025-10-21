import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.*;

/**
 * This is the main code which renders the grid and checks the given answers by the user.
 */

public class Wordles { 

    // This is an array containing the words that can be the answers to the wordle.
    private static final String[] wordlist5 = {"ARRAY", "LOGIC", "CACHE", "QUERY", "STACK", "INPUT", "PIXEL", "PATCH", "CRASH", "SHELL"};
    private static final String[] wordlist4 = {"CODE", "DATA", "BYTE", "NODE", "LOOP", "HASH", "BING", "PORT", "DUMP", "BOOT"};
    private static final String[] wordlist6 = {"BINARY", "BUFFER", "CURSOR", "SYNTAX", "PACKET", "SCRIPT", "SERVER", "BACKUP", "DRIVER", "PYTHON"};

    private static final int MAX_GRID_DIM = 6;

    // This cets the values for the number of rows and colomns in the wordle.
    private int wordLength;
    private int maxCols;
    private int maxRows; 
    private String targetWord;

    // GUI Components
    // Change array size to match the maximum possible guesses/word length
    private final JPanel[][] squares = new JPanel[MAX_GRID_DIM][MAX_GRID_DIM]; 
    private final JLabel[][] letters = new JLabel[MAX_GRID_DIM][MAX_GRID_DIM];
    
    // This makes these fields accesible through the entire program
    private JTextField inputField; 
    private JLabel titleLabel; 
    private JFrame frame; 
    
    private int guesses = MAX_GRID_DIM;
    private int currentRow = 0;

    // Custom Colors for Wordle Feedback 
    // The colors are defined so they can be used in guess()
    private static final Color GREEN = new Color(106, 170, 100); // Correct letter, correct position
    private static final Color YELLOW = new Color(200, 180, 88); // Correct letter, wrong position
    private static final Color GRAY = new Color(120, 125, 125);  // Letter not in word
    private static final Color WHITE = Color.WHITE;

    /**
     * This part of the code selects a random word from the arraylist to be the
     * answer to the wordle.
     */
     
    public Wordles(int wordLength) {
        this.wordLength = wordLength;
        this.maxCols = wordLength;
        this.maxRows = (wordLength == 6) ? 6 : 5;
        this.guesses = maxRows;
        
        targetWord = selectRandomWord(wordLength);
        createAndShowGUI();
    }

    private String selectRandomWord(int length) {
        String[] wordlist;
        if (length == 4) {
            wordlist = wordlist4;
        } else if (length == 6) {
            wordlist = wordlist6;
        } else {
            wordlist = wordlist5;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(wordlist.length);
        return wordlist[randomIndex];
    }

    /**
     * This creates the layout of the game window.
     * For example: Here we have set the colors, the sizes of frames and panels, the locations of 
     * the frames and panels etc.
     */

    public void createAndShowGUI() { 
        frame = new JFrame(wordLength + " letter wordle" + targetWord); // get rid of targetword at end 
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
        gridPanel.setLayout(new GridLayout(maxRows, maxCols, 6, 6)); 
        gridPanel.setBackground(Color.DARK_GRAY);
        gridPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        for (int r = 0; r < maxRows; r++) {
            for (int c = 0; c < maxCols; c++) {
                squares[r][c] = new JPanel();
                squares[r][c].setBackground(WHITE);
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

    /**
     * This method compares the chosen targetword to the chosen word by the user 
     * and then accordingly assigns either grey, yellow or red to the squares in which
     * the letters reside.
     */

    public void guess() {
        // Use frame for JOptionPane parent component
        if (currentRow >= maxRows) {
            JOptionPane.showMessageDialog(frame, "No more guesses! The word was " + targetWord);
            return;
        }
        
        String guess = inputField.getText().trim().toUpperCase();

        if (guess.length() != wordLength) {
            JOptionPane.showMessageDialog(frame, "Guess must be " + wordLength + " letters.");
            return;
        }
        
        // Here we implement ordle Color Logic 
        // Use a flag for robust duplicate handling
        boolean[] targetUsed = new boolean[wordLength];
        
        // 1. First Pass: Check for GREEN
        for (int i = 0; i < wordLength; i++) {
            letters[currentRow][i].setText(String.valueOf(guess.charAt(i)));
            
            if (guess.charAt(i) == targetWord.charAt(i)) {
                squares[currentRow][i].setBackground(GREEN);
                letters[currentRow][i].setForeground(Color.WHITE);
                targetUsed[i] = true;
            } else {

                squares[currentRow][i].setBackground(WHITE); 
                letters[currentRow][i].setForeground(Color.BLACK);
            }
        }

        // 2. Second Pass: Check for YELLOW and GRAY
        for (int i = 0; i < wordLength; i++) {
            // Only process non-green squares
            if (squares[currentRow][i].getBackground() != GREEN) {
                char charGuess = guess.charAt(i);
                boolean foundYellow = false;
                
                // Check if the character exists elsewhere in the target word
                for (int j = 0; j < wordLength; j++) {
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

        // Output if the user quessed the word or did not solve it.
        
        if (guess.equals(targetWord)) {
            guesses--;
            titleLabel.setText("Number of guesses left: " + guesses);
            JOptionPane.showMessageDialog(frame, "🎉 You solved it!");
            currentRow = maxRows; // End the game
        } else {
            currentRow++;
            guesses--;
            titleLabel.setText("Number of guesses left: " + guesses);
            
            if (currentRow >= maxRows) {
                JOptionPane.showMessageDialog(frame, "😢 Game Over! The word was " + targetWord);
            }
        }

        inputField.setText("");
        inputField.requestFocusInWindow();
    }

    public static void StartGame(int wordLength) {
        // Correct way to start the Swing application
        SwingUtilities.invokeLater(() -> {
            new Wordles(wordLength); // The constructor calls createAndShowGUI()
        });
    }

    public static void main(String[] args) {
        StartScreen.main(args);;
    }
}