package wordle;

import java.awt.*;
import java.util.*;
import java.util.function.IntConsumer;
import javax.swing.*;

public class DifficultyButtonFactory {
    
    public static JButton createDifficultyButton(int wordLength, IntConsumer action) {

        String difficulty;
        String lengthText = wordLength + " Letters";
        Color color;

        if (wordLength == 4) {
            difficulty = "EASY";
            color = WordleConstants.GREEN;
        } else if (wordLength == 6) {
            difficulty = "HARD";
            color = WordleConstants.RED;
        } else {
            difficulty = "MEDIUM";
            color = WordleConstants.YELLOW;
        }

        JButton button = new JButton();
        button.setBackground(color);
        button.setPreferredSize(new Dimension(250, 60));
        button.setMaximumSize(new Dimension(250, 60));

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        JLabel difficultyLabel = new JLabel(difficulty);
        difficultyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lengthLabel = new JLabel(lengthText);
        lengthLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        lengthLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 

        contentPanel.add(difficultyLabel);
        contentPanel.add(lengthLabel);
    
        button.setLayout(new GridBagLayout()); 
        button.add(contentPanel);

        button.addActionListener(e -> action.accept(wordLength)); 

        return button;
    }

}