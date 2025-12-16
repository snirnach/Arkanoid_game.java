package animations;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import sprites.Ball;
import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;

import java.util.Random;

import geometry.Point;
import geometry.Velocity;
import sprites.Ball;
import java.awt.Color;
import java.util.Random;

public class MultipleFramesBouncingBallsAnimation {
    public static void main(String[] args) {
        int w = 800;
        int h = 600;
        GUI gui = new GUI("multiple frames balls", w, h);
        Sleeper sleeper = new Sleeper();
        Random rand = new Random();

        Ball[] balls = new Ball[args.length];
        double x,y;

        for (int i = 0; i < args.length; i++) {
            int radius = Integer.parseInt(args[i]);

            if (i < args.length / 2) {
                x = radius + rand.nextInt(500 - 2 * radius) + 50 + radius;
                y = radius + rand.nextInt(500 - 2 * radius) + 50 + radius;
            }
            else {
                x = radius + rand.nextInt(600 - 2 * radius) + 450 + radius;
                y = radius + rand.nextInt(600 - 2 * radius) + 450 + radius;
            }
            Ball ball = new Ball(new Point(x, y), radius, MultipleBouncingBallsAnimation.getRandomColor());

            int speed = (radius > 50) ? 1 : (50 / radius);
            ball.setVelocity(Velocity.fromAngleAndSpeed(rand.nextInt(360), speed));

            balls[i] = ball;
        }

        while (true) {
            DrawSurface d = gui.getDrawSurface();
            d.setColor(Color.GRAY);
            d.fillRectangle(50, 50,450 , 450);
            d.setColor(Color.YELLOW);
            d.fillRectangle(450, 450, 150, 150);

            for (int i = 0; i < args.length; i++) {
                if (i < args.length / 2) {
                    balls[i].moveOneStep();
                    balls[i].drawOn(d);
                }
                else {
                    balls[i].moveOneStep();
                    balls[i].drawOn(d);
                }
            }
            gui.show(d);
            sleeper.sleepFor(50);
        }
    }
}
