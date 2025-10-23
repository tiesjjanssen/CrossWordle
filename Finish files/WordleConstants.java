package wordle;

import java.awt.Color;

public class WordleConstants { 

    // This is an array containing the words that can be the answers to the wordle.
    public static final String[] wordlist5 = {"ARRAY", "LOGIC", "CACHE", "QUERY", "STACK", "INPUT", "PIXEL", "PATCH", "CRASH", "SHELL"};
    public static final String[] wordlist4 = {"CODE", "DATA", "BYTE", "NODE", "LOOP", "HASH", "BING", "PORT", "DUMP", "BOOT"};
    public static final String[] wordlist6 = {"BINARY", "BUFFER", "CURSOR", "SYNTAX", "PACKET", "SCRIPT", "SERVER", "BACKUP", "DRIVER", "PYTHON"};

    public static final int MAX_GRID_DIM = 6;
    public static final int WIDTH = 512;
    public static final int HEIGHT = 600;

    // Custom Colors for Wordle Feedback 
    // The colors are defined so they can be used in guess()
    public static final Color GREEN = new Color(106, 170, 100); // Correct letter, correct position
    public static final Color YELLOW = new Color(200, 180, 88); // Correct letter, wrong position
    public static final Color GRAY = new Color(120, 125, 125);  // Letter not in word
    public static final Color BG_LIGHT = new Color(220, 220, 220); // For startScreen
    public static final Color RED = Color.RED;
    public static final Color WHITE = Color.WHITE;
    public static final Color BLACK = Color.BLACK;
    public static final Color BG_DARK = Color.DARK_GRAY;
}
