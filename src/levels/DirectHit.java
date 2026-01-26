package levels;

import game.LevelInformation;

// This level has 1 ball.
// The background is BLACK with a target symbol drawn in the center.
// There is exactly 1 block in the center of the screen (color RED).
// The ball flies straight up.
public class DirectHit implements LevelInformation {
    @Override
    public int numberOfBalls() {
        return 1;
    }

    @Override
    public java.util.List<geometry.Velocity> initialBallVelocities() {
        java.util.List<geometry.Velocity> velocities = new java.util.ArrayList<>();
        velocities.add(geometry.Velocity.fromAngleAndSpeed(0, 5));
        return velocities;
    }

    @Override
    public int paddleSpeed() {
        return 10;
    }

    @Override
    public int paddleWidth() {
        return 100;
    }

    @Override
    public String levelName() {
        return "Direct Hit";
    }

    @Override
    public sprites.Sprite getBackground() {
        return new sprites.Sprite() {;
            @Override
            public void drawOn(biuoop.DrawSurface d) {
                d.setColor(java.awt.Color.BLACK);
                d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

                d.setColor(java.awt.Color.BLUE);
                int centerX = 400;
                int centerY = 225;
                for (int radius = 30; radius <= 90; radius += 30) {
                    d.drawCircle(centerX, centerY, radius);
                }
                d.drawLine(centerX - 100, centerY, centerX + 100, centerY);
                d.drawLine(centerX, centerY - 100, centerX, centerY + 100);
            }

            @Override
            public void timePassed() {
                // No animation needed for the background
            }
        };
    }

    @Override
    public java.util.List<sprites.Block> blocks() {
        java.util.List<sprites.Block> blocks = new java.util.ArrayList<>();
        sprites.Block block = new sprites.Block(new geometry.Rectangle(new geometry.Point(385, 210), 30, 30), java.awt.Color.RED);
        blocks.add(block);
        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 1;
    }


}

