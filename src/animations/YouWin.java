package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import hit.Counter;

public class YouWin implements Animation {
    private Counter score;

    public YouWin(Counter score) {
        this.score = score;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.setColor(java.awt.Color.WHITE);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

        d.setColor(java.awt.Color.BLUE);
        d.drawText(200, d.getHeight() / 2, "You Win! Your Score is " + score.getValue(), 32);
    }

    @Override
    public boolean shouldStop() { return false; }
}
