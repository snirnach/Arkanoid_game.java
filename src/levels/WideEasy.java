package levels;

import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import biuoop.DrawSurface;
import game.LevelInformation;

// Level Name: "Wide Easy"
// Background: White. Draw a sun with rays in the background.
// Number of balls: 10. Velocities: Spread them in a wide angle arc.
// Paddle: Speed 5, Width 600 (very wide, covers most of the screen).
// Blocks: A single row of 15 blocks at y=250.
// Colors: Red, Orange, Yellow, Green, Blue, Pink, Cyan (repeating pairs).
// Number of blocks to remove: 15.
public class WideEasy implements LevelInformation {
    // Implement methods...
    @Override
    public int numberOfBalls() {
        return 10;
    }

    @Override
    public List<geometry.Velocity> initialBallVelocities() {
        List<geometry.Velocity> velocities = new ArrayList<>();
        int angleStart = 300;
        int angleIncrement = 12;
        for (int i = 0; i < numberOfBalls(); i++) {
            velocities.add(geometry.Velocity.fromAngleAndSpeed(angleStart + i * angleIncrement, 5));
        }
        return velocities;
    }

    @Override
    public int paddleSpeed() {
        return 5;
    }

    @Override
    public int paddleWidth() {
        return 600;
    }

    @Override
    public String levelName() {
        return "Wide Easy";
    }

    @Override
    public sprites.Sprite getBackground() {
        return new sprites.Sprite() {
            @Override
            public void drawOn(DrawSurface d) {
                d.setColor(Color.WHITE);
                d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
                // Draw sun with rays
                d.setColor(Color.YELLOW);
                int centerX = 100;
                int centerY = 100;
                d.fillCircle(centerX, centerY, 50);
                for (int i = 0; i < 12; i++) {
                    double angle = i * (Math.PI / 6);
                    int xEnd = (int) (centerX + Math.cos(angle) * 100);
                    int yEnd = (int) (centerY + Math.sin(angle) * 100);
                    d.drawLine(centerX, centerY, xEnd, yEnd);
                }
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
        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.PINK, Color.CYAN};
        int blockWidth = 50;
        int blockHeight = 20;
        for (int i = 0; i < 15; i++) {
            Color color = colors[i % colors.length];
            sprites.Block block = new sprites.Block(new geometry.Rectangle(new geometry.Point(25 + i * blockWidth, 250), blockWidth, blockHeight), color);
            blocks.add(block);
        }
        return blocks;
    }
    @Override
    public int numberOfBlocksToRemove() {
        return 15;
    }

}
