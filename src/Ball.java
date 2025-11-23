import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import javax.swing.*;
import java.awt.*;

public class Ball {
    private int radius;
    private java.awt.Color color;
    private Point location;
    private Velocity velocity;


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

    // draw the ball on the given DrawSurface
    public void drawOn(DrawSurface surface){
        surface.setColor(color);
        surface.fillCircle(getX(), getY(), getSize());
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

    public void moveOneStep(int sw, int sh, int w, int h) {
        this.location = this.getVelocity().applyToPoint(this.location);

        Velocity currVel = getVelocity();
       if (location.getX() - radius <= sw){
           setVelocity(-currVel.getDx(), currVel.getDy());
           currVel = getVelocity();
           location.setX(radius + sw);
       }
       else if (location.getX() + radius >= w){
           setVelocity(-currVel.getDx(), currVel.getDy());
           currVel = getVelocity();
           location.setX(w - radius);
       }

       if (location.getY() - radius <= sh){
           setVelocity(currVel.getDx(), -currVel.getDy());
           location.setY(radius + sh);
       }
       else if (location.getY() + radius >= h){
           setVelocity(currVel.getDx(), -currVel.getDy());
           location.setY(h - radius);
       }
    }


    public static void main(String[] args) {
        Point start = new Point(0, 0);
        drawAnimation(start, 15, 10);
    }

    static void drawAnimation(Point start, double dx, double dy) {
        int width = 200;
        int height = 200;
        GUI gui = new GUI("title",width,height);
        Sleeper sleeper = new Sleeper();
        Ball ball = new Ball((int) start.getX(), (int) start.getY(), 30, java.awt.Color.BLACK);
        ball.setVelocity(dx, dy);
        while (true) {
            ball.moveOneStep(0, 0, width,height);
            DrawSurface d = gui.getDrawSurface();
            ball.drawOn(d);
            gui.show(d);
            sleeper.sleepFor(50);  // wait for 50 milliseconds.
        }
    }
}
