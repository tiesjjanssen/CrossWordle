package wordle;

import java.awt.*;
import java.util.*;
import java.util.function.IntConsumer;
import javax.swing.*;
import javax.swing.border.*;

public class StartScreen {
    private JFrame frame;
    
    StartScreen() {
        createAndShowGUI();
    }
        
    public static void showStartScreen() {
        SwingUtilities.invokeLater(() -> {
            getInstance().frame.setVisible(true);
        });
    }

    public void createAndShowGUI() {
        frame = new JFrame("Wordle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WordleConstants.WIDTH, WordleConstants.HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(WordleConstants.BG_LIGHT);
        mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel titleLabel = new JLabel("Wordle");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 100));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBackground(Color.LIGHT_GRAY);
        titleLabel.setBorder(new EmptyBorder(2, 2, 2, 2));
        
        mainPanel.add(titleLabel);

        JButton easy = DifficultyButtonFactory.createDifficultyButton(4, this::choice);
        JButton medium = DifficultyButtonFactory.createDifficultyButton(5, this::choice);
        JButton hard = DifficultyButtonFactory.createDifficultyButton(6, this::choice);
        
        mainPanel.add(Box.createVerticalStrut(60));
        mainPanel.add(easy);
        mainPanel.add(Box.createVerticalStrut(20)); 
        mainPanel.add(medium);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(hard);
        
        mainPanel.add(Box.createVerticalGlue()); // Zorgt voor verticale ruimte

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    
    private void choice(int wordLength) {
        frame.dispose(); 
        
        GameLogic.startGame(wordLength);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StartScreen();
        });
    }
}
