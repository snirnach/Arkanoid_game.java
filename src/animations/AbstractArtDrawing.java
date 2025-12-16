package animations;

import biuoop.GUI;
import biuoop.DrawSurface;
import geometry.Line;
import geometry.Point;

import java.util.Random;
import java.awt.Color;

public class AbstractArtDrawing {
    public void drawLines() {
        GUI gui = new GUI("Abstract lines", 400, 300);
        DrawSurface d = gui.getDrawSurface();

        Line[] lines = new Line[10];

        for (int i = 0; i < 10; ++i) {
            lines[i] = generateRandomLine();
            drawLine(lines[i], d);
            Point mid = lines[i].middle();
            d.setColor(Color.BLUE);
            d.fillCircle((int) mid.getX(), (int) mid.getY(), 3);
        }

        d.setColor(Color.RED);
        for (int i = 0; i < 10; i++) {
            for (int j = i + 1; j < 10; j++) {
                if (lines[i].isIntersecting(lines[j])) {
                    Point inter = lines[i].intersectionWith(lines[j]);
                    if (inter != null) {
                        d.fillCircle((int) inter.getX(), (int) inter.getY(), 3);
                    }
                }
            }
        }

        gui.show(d);

    }

    private Line generateRandomLine() {
        Random rand = new Random();
        int x1 = rand.nextInt(400);
        int y1 = rand.nextInt(300);
        int x2 = rand.nextInt(400);
        int y2 = rand.nextInt(300);
        return new Line(x1, y1, x2, y2);
    }

    private void drawLine(Line l, DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawLine((int) l.start().getX(), (int) l.start().getY(),
                (int) l.end().getX(),   (int) l.end().getY());
    }





    public static void main(String[] args) {
        AbstractArtDrawing example = new AbstractArtDrawing();
        example.drawLines();
    }
}
