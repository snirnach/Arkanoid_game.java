package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import hit.Counter;

public class GameOver implements Animation {
    private Counter score;

    public GameOver(Counter score) {
        this.score = score;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.setColor(java.awt.Color.BLACK);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

        d.setColor(java.awt.Color.RED);
        d.drawText(200, d.getHeight() / 2, "Game Over. Your Score is " + score.getValue(), 32);

    }

    @Override
    public boolean shouldStop() { return false;}
}
