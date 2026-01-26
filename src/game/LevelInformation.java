package game;
// Interface defining the data required for a level:
// int numberOfBalls();
// List<Velocity> initialBallVelocities();
// int paddleSpeed();
// int paddleWidth();
// String levelName();
// Sprite getBackground();
// List<Block> blocks();
// int numberOfBlocksToRemove();

public interface LevelInformation {
    int numberOfBalls();
    java.util.List<geometry.Velocity> initialBallVelocities();
    int paddleSpeed();
    int paddleWidth();
    String levelName();
    sprites.Sprite getBackground();
    java.util.List<sprites.Block> blocks();
    int numberOfBlocksToRemove();
}
