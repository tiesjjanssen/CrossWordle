package wordle;

import javax.swing.SwingUtilities;

public class WordleApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StartScreen();
        });
    }
}