import biuoop.DrawSurface;

import java.awt.*;

public class Block implements Collidable, Sprite {
    private Rectangle collisionRectangle;
    private Color color;

    public Block(Rectangle rect, Color color) {
        collisionRectangle = rect;
        this.color = color;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
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

        return new Velocity(newDx, newDy);
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
