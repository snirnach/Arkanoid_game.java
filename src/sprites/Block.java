package sprites;
import biuoop.DrawSurface;
import hit.HitListener;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import collision.Collidable;
import geometry.Point;
import geometry.Line;
import geometry.Rectangle;
import geometry.Velocity;

import game.Game;
import hit.HitNotifier;

public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle collisionRectangle;
    private Color color;
    private List<HitListener> hitListeners;

    public Block(Rectangle rect, Color color) {
        collisionRectangle = rect;
        this.color = color;
        this.hitListeners = new ArrayList<>();
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double newDx = currentVelocity.getDx();
        double newDy = currentVelocity.getDy();
        double left   = collisionRectangle.getUpperLeft().getX();
        double right  = left + collisionRectangle.getWidth();
        double top    = collisionRectangle.getUpperLeft().getY();
        double bottom = top + collisionRectangle.getHeight();
        double eps = 0.0001;

        if (Math.abs(collisionPoint.getX() - left) < eps ||
                Math.abs(collisionPoint.getX() - right) < eps) {
            newDx = -newDx;
        }


        if (Math.abs(collisionPoint.getY() - top) < eps ||
                Math.abs(collisionPoint.getY() - bottom) < eps) {
            newDy = -newDy;
        }
        this.notifyHit(hitter);

        return new Velocity(newDx, newDy);
    }

    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    private boolean inLine(Line line, Point slice) {
        return Line.inLine(line.start(), line.end(), slice);
    }

    public void addToGame(Game game) {
        game.addSprite(this);
        game.addCollidable(this);
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) collisionRectangle.getUpperLeft().getX(),
                (int) collisionRectangle.getUpperLeft().getY(),
                (int) collisionRectangle.getWidth(),
                (int) collisionRectangle.getHeight());

        d.setColor(Color.BLACK);
        d.drawRectangle((int) collisionRectangle.getUpperLeft().getX(),
                (int) collisionRectangle.getUpperLeft().getY(),
                (int) collisionRectangle.getWidth(),
                (int) collisionRectangle.getHeight());
    }

    @Override
    public void timePassed() {

    }
}
