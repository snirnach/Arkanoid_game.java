import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.awt.Color;

public class CollisionTest {
    public static void main(String[] args) {
        // יצירת המסך
        GUI gui = new GUI("Collision Test", 800, 600);
        Sleeper sleeper = new Sleeper();

        // יצירת סביבת המשחק
        GameEnvironment environment = new GameEnvironment();

        // יצירת בלוקים (קירות)
        // שים לב: כאן אני מניח של-Rectangle יש בנאי (Point, width, height)
        Block topBlock = new Block(new Rectangle(new Point(0, 0), 800, 20), Color.GRAY);
        Block bottomBlock = new Block(new Rectangle(new Point(0, 580), 800, 20), Color.GRAY);
        Block leftBlock = new Block(new Rectangle(new Point(0, 20), 20, 560), Color.GRAY);
        Block rightBlock = new Block(new Rectangle(new Point(780, 20), 20, 560), Color.GRAY);

        // בלוק באמצע
        Block middleBlock = new Block(new Rectangle(new Point(400, 300), 100, 30), Color.CYAN);

        // הוספה לסביבה (כדי שהכדור יידע להתנגש בהם)
        environment.addCollidable(topBlock);
        environment.addCollidable(bottomBlock);
        environment.addCollidable(leftBlock);
        environment.addCollidable(rightBlock);
        environment.addCollidable(middleBlock);

        // יצירת הכדור
        Ball ball = new Ball(100, 100, 5, Color.RED);
        ball.setVelocity(6, 6);
        ball.setGameEnvironment(environment); // חיבור הכדור לסביבה

        // לולאת המשחק
        while (true) {
            ball.moveOneStep();

            DrawSurface d = gui.getDrawSurface();

            // ציור הבלוקים
            topBlock.drawOn(d);
            bottomBlock.drawOn(d);
            leftBlock.drawOn(d);
            rightBlock.drawOn(d);
            middleBlock.drawOn(d);

            // ציור הכדור
            ball.drawOn(d);

            gui.show(d);
            sleeper.sleepFor(20);
        }
    }
}
