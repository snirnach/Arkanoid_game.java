package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

public class HighScoresAnimation implements Animation {
    private KeyboardSensor keyboard;
    private String endKey;
    private int score;
    private boolean stop;

    public HighScoresAnimation(int score, String endKey, KeyboardSensor keyboard) {
        this.score = score;
        this.endKey = endKey;
        this.keyboard = keyboard;
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.setColor(java.awt.Color.BLACK);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(java.awt.Color.WHITE);
        d.drawText(250, 100, "High Scores", 50);
        d.drawText(100, 200, "The highest score so far is: " + this.score, 30);
        d.drawText(150, 400, "Press " + endKey + " to go back", 30);
        if (this.keyboard.isPressed(this.endKey)) {
            this.stop = true;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
