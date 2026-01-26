package indicator;

import sprites.Sprite;

// A Sprite that displays the level name at the top of the screen.
// Constructor takes: String levelName.
// drawOn: Draws the text "Level Name: [name]" at x=550, y=15 in black color (font size 15).
public class LevelNameIndicator implements Sprite {
    private String levelName;

    public LevelNameIndicator(String levelName) {
        this.levelName = levelName;
    }

    public void drawOn(biuoop.DrawSurface d) {
        d.setColor(java.awt.Color.BLACK);
        d.drawText(550, 15, "Level Name: " + this.levelName, 15);
    }

    @Override
    public void timePassed() {
    }

}
