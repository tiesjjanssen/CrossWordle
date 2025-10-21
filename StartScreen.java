import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;

public class StartScreen {
    private static final int Width = 512;
    private static final int Height = 600;

    private JFrame frame;
    
    public StartScreen() {
        createAndShowGUI();
    }

    public void createAndShowGUI() {
        frame = new JFrame("Wordle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Width, Height);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(220, 220, 220));
        mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel titleLabel = new JLabel("Wordle");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 100));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBackground(Color.LIGHT_GRAY);
        titleLabel.setBorder(new EmptyBorder(2, 2, 2, 2));
        
        mainPanel.add(titleLabel);

        JButton easy = createDifficultyButton("EASY", "EASY \n 4 letters", Color.GREEN, 4);
        JButton medium = createDifficultyButton("MEDIUM", "MEDIUM \n 5 letters", Color.YELLOW, 5);
        JButton hard = createDifficultyButton("HARD", "HARD \n 6 letters", Color.RED, 6);
        
        mainPanel.add(easy);
        mainPanel.add(Box.createVerticalStrut(20)); // Ruimte tussen knoppen
        mainPanel.add(medium);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(hard);
        
        mainPanel.add(Box.createVerticalGlue()); // Zorgt voor verticale ruimte

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JButton createDifficultyButton(String difficulty, String lengthText, Color color, int wordLength) {
        JButton button = new JButton();
        button.setBackground(color);
        button.setPreferredSize(new Dimension(250, 60));
        button.setMaximumSize(new Dimension(250, 60));

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        contentPanel.setOpaque(false);

        JLabel difficultyLabel = new JLabel();
        difficultyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lengthLabel = new JLabel(lengthText);
        lengthLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        lengthLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 

        contentPanel.add(difficultyLabel);
        contentPanel.add(lengthLabel);
    
        button.setLayout(new GridBagLayout()); 
        button.add(contentPanel);

        button.addActionListener(e -> choice(wordLength)); 

        return button;
    }

    public void choice(int selectedWordLength) {
        frame.dispose();
        Wordles.StartGame(selectedWordLength);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StartScreen();
        });
}
}