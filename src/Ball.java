import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import javax.swing.*;
import java.awt.*;

public class Ball implements Sprite {
    private int radius;
    private java.awt.Color color;
    private Point location;
    private Velocity velocity;
    private GameEnvironment gameEnvironment;


    public Ball(Point center, int r, java.awt.Color color){
        this.location = center;
        this.radius = r;
        this.color = color;
    }

    public Ball(int xCenter, int yCenter, int r, java.awt.Color color){
        this.location = new Point(xCenter, yCenter);
        this.radius = r;
        this.color = color;
    }

    // accessors
    public int getX(){
        return (int) location.getX();
    }
    public void setX(int x){
        location.setX(x);
    }
    public int getY(){
        return (int) location.getY();
    }
    public void setY(int y){
        location.setY(y);
    }
    public int getSize(){
        return radius;
    }
    public java.awt.Color getColor(){
        return color;
    }

    public void setGameEnvironment(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }


    public void addToGame(Game game) {
        game.addSprite(this);
    }
    // draw the ball on the given DrawSurface
    @Override
    public void drawOn(DrawSurface surface){
        surface.setColor(color);
        surface.fillCircle(getX(), getY(), getSize());
    }

    @Override
    public void timePassed() {
        moveOneStep();
    }

    public void setVelocity(Velocity v){
       this.velocity = v;
    }
    public void setVelocity(double dx, double dy){
        this.velocity = new Velocity(dx, dy);
    }
    public Velocity getVelocity(){
        return velocity;
    }

    public void moveOneStep() {
        Line trajectory = new Line(this.location, this.velocity.applyToPoint(this.location));

        CollisionInfo collisionInfo = this.gameEnvironment.getClosestCollision(trajectory);

        if (collisionInfo != null) {
            Point collisionPoint = collisionInfo.collisionPoint();
            Collidable object = collisionInfo.collisionObject();
            Velocity currentV = this.velocity;
            Velocity newV = object.hit(collisionPoint, currentV);

            double epsilon = 0.0001;
            double newX = collisionPoint.getX();
            double newY = collisionPoint.getY();

            if (newV.getDx() != currentV.getDx()) {
                if (velocity.getDx() > 0) {
                    newX = newX - epsilon;
                } else if (velocity.getDx() < 0) {
                    newX = newX + epsilon;
                }
            }

            if (newV.getDy() != currentV.getDy()) {
                if (velocity.getDy() > 0) {
                    newY = newY - epsilon;
                } else if (velocity.getDy() < 0) {
                    newY = newY + epsilon;
                }
            }

            this.location = new Point(newX, newY);
            this.setVelocity(newV);

        } else {
            this.location = this.velocity.applyToPoint(this.location);

    }
    }

}
