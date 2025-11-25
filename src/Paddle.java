import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.*;

public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle rect;
    private Color color;
    private double speed;

    public Paddle(biuoop.KeyboardSensor keyboard) {
        this.keyboard = keyboard;
        rect = new Rectangle(new Point(375,565),50, 15);
        color = Color.ORANGE;
        speed = 3;
    }

    public void moveLeft(){
        rect.setLocation(new Point(rect.getUpperLeft().getX() - speed, rect.getUpperLeft().getY()));
    }
    public void moveRight(){
        rect.setLocation(new Point(rect.getUpperLeft().getX() + speed, rect.getUpperLeft().getY()));
    }

    @Override
    public void drawOn(DrawSurface d){
        d.setColor(color);
        d.fillRectangle((int) rect.getUpperLeft().getX(),
                (int) rect.getUpperLeft().getY(),
                (int) rect.getWidth(),
                (int) rect.getHeight());
        d.setColor(Color.BLACK);
        d.drawRectangle((int) rect.getUpperLeft().getX(),
                (int) rect.getUpperLeft().getY(),
                (int) rect.getWidth(),
                (int) rect.getHeight());
    }

    // Sprite
    public void timePassed(){
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY) && rect.getUpperLeft().getX() - speed >= 20) {
            moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY) && rect.getUpperLeft().getX() + 50 + speed <= 780) {
            moveRight();
        }
    }

    // Collidable
    public Rectangle getCollisionRectangle(){
        return rect;
    }
    public Velocity hit(Point collisionPoint, Velocity currentVelocity){
        double speed = currentVelocity.getSpeed();
        double paddleTopY = rect.getUpperLeft().getY();
        double eps = 0.0001; // המרחק לבדיקה

        if (collisionPoint.getY() <= paddleTopY + eps && currentVelocity.getDy() > 0) {

            double relativeX = collisionPoint.getX() - rect.getUpperLeft().getX(); // המרחק היחסי מהקצה השמאלי

            if (relativeX <= 10) {
                return Velocity.fromAngleAndSpeed(300, speed);
            }
            else if (relativeX <= 20) {
                return Velocity.fromAngleAndSpeed(330, speed);
            }
            else if (relativeX <= 30) {
                return Velocity.fromAngleAndSpeed(0, speed); // 0 מעלות = ישר למעלה
            }
            else if (relativeX <= 40) {
                return Velocity.fromAngleAndSpeed(30, speed);
            }
            else {
                return Velocity.fromAngleAndSpeed(60, speed);
            }

        } else {
            return new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }
    }

    // Add this paddle to the game.
    public void addToGame(Game g){
        g.addSprite(this);
        g.addCollidable(this);
    }
}
