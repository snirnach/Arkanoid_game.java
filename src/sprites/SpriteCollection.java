package sprites;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

public class SpriteCollection {
    private ArrayList<Sprite> sprites;

    public SpriteCollection() {
        sprites = new ArrayList<>();
    }
    public void addSprite(Sprite s){
        sprites.add(s);
    }


    // call timePassed() on all sprites.
    public void notifyAllTimePassed() {
        List<Sprite> spritesCopy = new ArrayList<>(this.sprites);

        for (Sprite s : spritesCopy) {
            s.timePassed();
        }
    }

    // call drawOn(d) on all sprites.
    public void drawAllOn(DrawSurface d){
        for (Sprite s : sprites){
            s.drawOn(d);
        }
    }

    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }
}