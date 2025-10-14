import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class CrossWordle {
    private static final int SIZE = 8;
    private JPanel[][] squares = new JPanel[SIZE][SIZE];

    private int guesses = 20;

    public void renderFrame() {
        JFrame frame = new JFrame("CrossWordle @Ties & Caitlyn");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(512, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // --- Title box at the top ---
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(220, 220, 220));
        titlePanel.setBorder(new EmptyBorder(5, 0, 5, 0));

        JLabel titleLabel = new JLabel("Number of guesses left: " + guesses);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titlePanel.add(titleLabel);

        // --- Grid panel ---
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(SIZE, SIZE, 1, 1)); // gaps for visible grid lines
        gridPanel.setBackground(Color.GRAY);
        gridPanel.setBorder(new EmptyBorder(16, 16, 16, 16));

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                squares[r][c] = new JPanel();
                squares[r][c].setBackground(Color.WHITE);
                squares[r][c].setBorder(new LineBorder(Color.BLACK));
                gridPanel.add(squares[r][c]);
            }
        }

        // --- Input panel at the bottom ---
        JPanel inputPanel = new JPanel(new BorderLayout(8, 8));
        inputPanel.setBorder(new EmptyBorder(10, 16, 10, 16));
        inputPanel.setBackground(new Color(240, 240, 240));

        JTextField inputField = new JTextField();
        JButton sendButton = new JButton("Send");

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // Example action for send button
        sendButton.addActionListener(e -> {
            String text = inputField.getText().trim();
            if (!text.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "You entered: " + text);
                inputField.setText("");
            }
        });

        // --- Add all to frame ---
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new CrossWordle().renderFrame();
    }

}

