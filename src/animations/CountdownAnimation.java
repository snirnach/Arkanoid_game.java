package animations;

import biuoop.DrawSurface;
import sprites.SpriteCollection;
import java.awt.Color;

// The CountdownAnimation displays the given gameScreen (background),
// and on top of it, shows a countdown from countFrom down to 1.
// The total duration is numOfSeconds.
public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private boolean stop;
    private long startTime;
    private boolean isFirstRun;

    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.stop = false;
        this.isFirstRun = true;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        if (this.isFirstRun) {
            this.startTime = System.currentTimeMillis();
            this.isFirstRun = false;
        }

        // Draw the game background
        this.gameScreen.drawAllOn(d);

        // Calculate the time per number
        long currentTime = System.currentTimeMillis();
        long usedTime = currentTime - this.startTime;
        long totalMilliseconds = (long)(this.numOfSeconds * 1000);

        // Calculate which number to display
        double timeLeft = (double)totalMilliseconds - usedTime;
        int currentCount = (int)((timeLeft / totalMilliseconds) * this.countFrom) + 1;

        if (usedTime > totalMilliseconds) {
            this.stop = true;
        }

        // Draw the current number
        d.setColor(java.awt.Color.RED);
        if (!this.stop) {
            d.drawText(d.getWidth() / 2, d.getHeight() / 2, Integer.toString(currentCount), 50);
        }

    }

    // Implement shouldStop()
    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
