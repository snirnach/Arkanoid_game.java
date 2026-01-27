package sprites;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;

import collision.Collidable;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

import game.GameLevel;


public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle rect;
    private Color color;
    private double speed;

    public Paddle(biuoop.KeyboardSensor keyboard, int paddleWidth, double paddleSpeed){
        this.keyboard = keyboard;
        int paddleX = (800 - paddleWidth) / 2;
        rect = new Rectangle(new Point(paddleX,565),paddleWidth, 15);
        color = Color.ORANGE;
        speed = paddleSpeed;
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

    // sprites.Sprite
    public void timePassed(){
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY) && rect.getUpperLeft().getX() - speed >= 20) {
            moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY) && rect.getUpperLeft().getX() + rect.getWidth() + speed <= 780) {
            moveRight();
        }
    }

    // Collidable
    public Rectangle getCollisionRectangle(){
        return rect;
    }

    public Velocity hit(Ball hitter,Point collisionPoint, Velocity currentVelocity){
        double speed = currentVelocity.getSpeed();
        double regionWidth = rect.getWidth() / 5;
        double relativeX = collisionPoint.getX() - rect.getUpperLeft().getX();
        double eps = 0.0001;


        if (currentVelocity.getDy() > 0) {

            if (relativeX <= regionWidth) {
                return Velocity.fromAngleAndSpeed(300, speed);
            }

            else if (relativeX <= 2 * regionWidth) {
                return Velocity.fromAngleAndSpeed(330, speed);
            }

            else if (relativeX <= 3 * regionWidth) {
                return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
            }

            else if (relativeX <= 4 * regionWidth) {
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
    public void addToGame(GameLevel g){
        g.addSprite(this);
        g.addCollidable(this);
    }
}
