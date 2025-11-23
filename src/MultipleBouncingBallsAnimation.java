import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.awt.Color;
import java.util.Random;

public class MultipleBouncingBallsAnimation {
    public static void main(String[] args) {
        int w = 400;
        int h = 400;

        GUI gui = new GUI("multiple balls", w, h);
        Sleeper sleeper = new Sleeper();
        Random rand = new Random();

        Ball[] balls = new Ball[args.length];

        for (int i = 0; i < args.length; i++) {
            int radius = Integer.parseInt(args[i]);
            double x = radius + rand.nextInt(w - 2 * radius);
            double y = radius + rand.nextInt(h - 2 * radius);

            Ball ball = new Ball(new Point(x, y), radius, getRandomColor());

            int speed = (radius > 50) ? 1 : (50 / radius);
            ball.setVelocity(Velocity.fromAngleAndSpeed(rand.nextInt(360), speed));

            balls[i] = ball;
        }

        while (true) {
            DrawSurface d = gui.getDrawSurface();
            for (Ball ball : balls) {
                ball.moveOneStep(0, 0, w, h);
                ball.drawOn(d);
            }
            gui.show(d);
            sleeper.sleepFor(50);
        }
    }


    public static Color getRandomColor() {
        Random rand = new Random();
        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);
        return new Color(r, g, b);
    }
}
