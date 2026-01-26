package levels;

import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import biuoop.DrawSurface;
import game.LevelInformation;

// Level Name: "Final Four"
// Background: Light Blue (Color(0, 100, 200)). Draw rain/lines in background.
// Number of balls: 3.
// Paddle: Speed 5, Width 80.
// Blocks: 7 rows of blocks (colors: Grey, Red, Yellow, Green, White, Pink, Cyan).
// Each row has 15 blocks (covering the entire width).
// Number of blocks to remove: 105.
public class FinalFour implements LevelInformation {
    // Implement methods...
    @Override
    public int numberOfBalls() {
        return 3;
    }
    @Override
    public List<geometry.Velocity> initialBallVelocities() {
        List<geometry.Velocity> velocities = new ArrayList<>();
        velocities.add(geometry.Velocity.fromAngleAndSpeed(330, 5));
        velocities.add(geometry.Velocity.fromAngleAndSpeed(0, 5));
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
        return "Final Four";
    }
    @Override
    public sprites.Sprite getBackground() {
        return new sprites.Sprite() {
            @Override
            public void drawOn(DrawSurface d) {
                d.setColor(new Color(0, 100, 200));
                d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
                // Draw rain/lines in background
                d.setColor(Color.WHITE);
                for (int i = 0; i < d.getWidth(); i += 10) {
                    d.drawLine(i, 0, i - 5, d.getHeight());
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
        Color[] colors = {Color.GRAY, Color.RED, Color.YELLOW, Color.GREEN, Color.WHITE, Color.PINK, Color.CYAN};
        int blockWidth = 52;
        int blockHeight = 20;
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 15; col++) {
                int x = col * blockWidth;
                int y = 100 + row * blockHeight;
                sprites.Block block = new sprites.Block(new geometry.Rectangle(new geometry.Point(x, y), blockWidth, blockHeight), colors[row]);
                blocks.add(block);
            }
        }
        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 105;
    }
}
