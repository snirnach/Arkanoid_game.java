package indicator;

import biuoop.DrawSurface;
import game.GameLevel;
import hit.Counter;
import sprites.Sprite;

import java.awt.Color;

public class LivesIndicator implements Sprite {
    private Counter lives;

    public LivesIndicator(Counter lives) {
        this.lives = lives;
    }

    @Override
    public void drawOn(DrawSurface d) {

        d.setColor(Color.BLACK);
        d.drawText(100, 17, "Lives: " + this.lives.getValue(), 15);
    }

    @Override
    public void timePassed() { }

    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
