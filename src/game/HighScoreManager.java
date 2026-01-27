package game;

import java.io.*;

public class HighScoreManager {
    private static final String FILE_NAME = "highscores.txt";
    private static final String PREFIX = "The highest score so far is: ";

    public static int getHighScore() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return 0;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            if (line == null) {
                return 0;
            }
            int colon = line.lastIndexOf(':');
            if (colon < 0) {
                return 0;
            }
            String numStr = line.substring(colon + 1).trim();
            return Integer.parseInt(numStr);
        } catch (IOException e) {
            System.err.println("Error reading `highscores.txt`: " + e.getMessage());
            return 0;
        } catch (NumberFormatException e) {
            System.err.println("Malformed score in `highscores.txt`: " + e.getMessage());
            return 0;
        }
    }

    public static void updateHighScore(int newScore) {
        int currentHighScore = getHighScore();
        if (newScore > currentHighScore) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
                pw.println(PREFIX + newScore);
            } catch (IOException e) {
                System.err.println("Error writing to `highscores.txt`: " + e.getMessage());
            }
        }
    }
}
