package levels;

import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import biuoop.DrawSurface;
import game.LevelInformation;

// Level Name: "Green 3"
// Background: Green (Color(0, 100, 0)). Draw a building/tower structure in the background.
// Number of balls: 2.
// Paddle: Speed 5, Width 80.
// Blocks: 5 rows of blocks (colors: Grey, Red, Yellow, Blue, White).
// Row 1: 10 blocks, Row 2: 9 blocks, Row 3: 8 blocks... down to 6 blocks.
// Number of blocks to remove: 40.


public class Green3 implements LevelInformation {
    @Override
    public int numberOfBalls() {
        return 2;
    }
    @Override
    public List<geometry.Velocity> initialBallVelocities() {
        List<geometry.Velocity> velocities = new ArrayList<>();
        velocities.add(geometry.Velocity.fromAngleAndSpeed(330, 5));
        velocities.add(geometry.Velocity.fromAngleAndSpeed(30, 5));
        return velocities;
    }
    @Override
    public int paddleSpeed() {
        return 5;
    }
    @Override
    public int paddleWidth() {
        return 80;
    }
    @Override
    public String levelName() {
        return "Green 3";
    }
    @Override
    public sprites.Sprite getBackground() {
        return new sprites.Sprite() {
            @Override
            public void drawOn(DrawSurface d) {
                // solid green background
                d.setColor(new Color(0, 100, 0));
                d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

                // --- Left building / tower ---
                int baseX = 50;
                int baseY = 200;
                int baseW = 150;
                int baseH = 360;
                d.setColor(Color.BLACK);
                d.fillRectangle(baseX, baseY, baseW, baseH);
                d.setColor(Color.BLACK);
                d.drawRectangle(baseX, baseY, baseW, baseH);

                // windows grid (white squares)
                d.setColor(Color.WHITE);
                int cols = 4;
                int rows = 8;
                int winW = 20;
                int winH = 20;
                int gapX = 15;
                int gapY = 18;
                int winStartX = baseX + 15;
                int winStartY = baseY + 15;
                for (int r = 0; r < rows; r++) {
                    for (int c = 0; c < cols; c++) {
                        int wx = winStartX + c * (winW + gapX);
                        int wy = winStartY + r * (winH + gapY);
                        d.fillRectangle(wx, wy, winW, winH);
                        d.setColor(Color.DARK_GRAY);
                        d.drawRectangle(wx, wy, winW, winH);
                        d.setColor(Color.WHITE);
                    }
                }

                // antenna pole (thin grey) and glowing red light at tip
                int poleW = 2;
                int poleH = 60;
                int poleX = baseX + baseW / 2 - poleW / 2;
                int poleY = baseY - poleH;
                d.setColor(Color.LIGHT_GRAY);
                d.fillRectangle(poleX, poleY, poleW, poleH);
                // glowing red light (concentric circles)
                int tipX = poleX + poleW / 2;
                int tipY = poleY;
                d.setColor(new Color(255, 80, 80));
                d.fillCircle(tipX, tipY, 10); // outer glow
                d.setColor(Color.RED);
                d.fillCircle(tipX, tipY, 6);  // inner bright light
                d.setColor(Color.BLACK);
                d.drawCircle(tipX, tipY, 10);
                d.drawCircle(tipX, tipY, 6);

            }

            @Override
            public void timePassed() {
                // No animation needed for the background
            }
        };
    }
    @Override
    public List<sprites.Block> blocks() {
        List<sprites.Block> blocks = new ArrayList<>();
        Color[] colors = {Color.GRAY, Color.RED, Color.YELLOW, Color.BLUE, Color.WHITE};
        int yStart = 100;
        ;
        for (int row = 0; row < 5; row++) {
            int blocksInRow = 10 - row;
            for (int i = 0; i < blocksInRow; i++) {
                int x = 725 - (i * 50);
                int y = yStart + (row * 20);
                sprites.Block block = new sprites.Block(new geometry.Rectangle(new geometry.Point(x, y), 50, 20), colors[row]);
                blocks.add(block);
            }
        }
        return blocks;
    }
    @Override
    public int numberOfBlocksToRemove() {
        return 40;
    }

}
