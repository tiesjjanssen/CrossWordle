package wordle;

import java.util.Random;

public class WordSelector {

     public static String selectRandomWord(int length) {
        String[] wordlist;
        if (length == 4) {
            wordlist = WordleConstants.wordlist4;
        } else if (length == 6) {
            wordlist = WordleConstants.wordlist6;
        } else {
            wordlist = WordleConstants.wordlist5;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(wordlist.length);
        return wordlist[randomIndex];
    }

}