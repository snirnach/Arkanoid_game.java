package game;

import animations.Animation;
import animations.AnimationRunner;
import animations.CountdownAnimation;
import animations.PauseScreen;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;
import collision.Collidable;
import geometry.Velocity;
import hit.BallRemover;
import hit.BlockRemover;
import indicator.LevelNameIndicator;
import indicator.ScoreIndicator;
import indicator.ScoreTrackingListener;
import sprites.Ball;
import sprites.Block;
import hit.Counter;
import java.util.Random;
import java.awt.Color;
import geometry.Point;
import geometry.Rectangle;
import sprites.Sprite;
import sprites.SpriteCollection;
import sprites.Paddle;

public class GameLevel implements Animation {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Sleeper sleeper;
    private Counter remainingBlocks;
    private Counter remainingBalls;
    private Counter score;
    private AnimationRunner runner;
    private boolean running;
    private LevelInformation levelInfo;
    private KeyboardSensor keyboard;

    public GameLevel(LevelInformation levelInfo, KeyboardSensor keyboard,
                     AnimationRunner runner, Counter score) {
        this.levelInfo = levelInfo;
        this.keyboard = keyboard;
        this.runner = runner;
        this.score = score;
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
    }


    public void addCollidable(Collidable c){
        environment.addCollidable(c);
    }
    public void addSprite(Sprite s){
        sprites.addSprite(s);
    }

    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    // Initialize a new game: create the Blocks and sprites.Ball (and sprites.Paddle)
    // and add them to the game.
    public void initialize(){
        this.sprites.addSprite(this.levelInfo.getBackground());

        this.remainingBlocks = new Counter();
        this.remainingBalls = new Counter();

        BlockRemover blockRemover = new BlockRemover(this, this.remainingBlocks);
        BallRemover ballRemover = new BallRemover(this, this.remainingBalls);
        ScoreTrackingListener scoreListener = new ScoreTrackingListener(this.score);

        Block topBorder = new Block(new Rectangle(new Point(0,0), 800, 20), Color.GRAY);
        Block leftBorder = new Block(new Rectangle(new Point(0, 20), 20, 580), Color.GRAY);
        Block rightBorder = new Block(new Rectangle(new Point(780, 20), 20, 580), Color.GRAY);
        Block deathRegion = new Block(new Rectangle(new Point(0, 600), 800, 20), Color.BLACK);
        topBorder.addToGame(this);
        leftBorder.addToGame(this);
        rightBorder.addToGame(this);
        deathRegion.addToGame(this);
        deathRegion.addHitListener(ballRemover);

        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score);
        sprites.addSprite(scoreIndicator);
        LevelNameIndicator nameInd = new LevelNameIndicator(this.levelInfo.levelName());
        this.sprites.addSprite(nameInd);

        for (Block block : this.levelInfo.blocks()) {
            block.addToGame(this);
            block.addHitListener(blockRemover);
            block.addHitListener(scoreListener);
        }

        this.remainingBlocks.increase(this.levelInfo.numberOfBlocksToRemove());

       createBalls();
        this.remainingBalls.increase(this.levelInfo.numberOfBalls());

        Paddle paddle = new Paddle(this.keyboard, this.levelInfo.paddleWidth(), this.levelInfo.paddleSpeed());
        paddle.addToGame(this);
    }

    private void createBlockPattern(BlockRemover remover, ScoreTrackingListener scoreListener) {
        for (int y = 100; y < 250; y += 15 ){
            Color randomColor = getRandomColor();
                for (int x = 100; x < 700; x += 40) {
                Block block = new Block(new Rectangle(new Point(x, y), 40, 15), randomColor);
                block.addToGame(this);
                block.addHitListener(scoreListener);
                block.addHitListener(remover);
                this.remainingBlocks.increase(1);
            }
        }

    }

    public static Color getRandomColor() {
        Random rand = new Random();
        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);
        return new Color(r, g, b);
    }

    // Run the game  start the animation loop.
    public void run() {
        if (this.remainingBalls.getValue() == 0) {

            createBalls();
            this.remainingBalls.increase(this.levelInfo.numberOfBalls());
        }
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));

        this.running = true;

        this.runner.run(this);
    }

    public void createBalls(){
        for (Velocity v : this.levelInfo.initialBallVelocities()) {
            Ball ball = new Ball(400, 560, 5, java.awt.Color.WHITE);
            ball.setVelocity(v);
            ball.setGameEnvironment(this.environment);
            ball.addToGame(this);
        }
    }


    @Override
    public void doOneFrame(DrawSurface d) {
        // draw and update sprites
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();

        // Check if 'p' is pressed.
        // If so, create a new PauseScreen and run it using the runner.
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new PauseScreen(this.keyboard));
        }

        // stop conditions: replace previous 'break' with setting running = false
        if (this.remainingBlocks.getValue() == 0) {
            this.score.increase(100);
            this.running = false;
        }
        if (this.remainingBalls.getValue() == 0) {
            this.running = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    public Counter getRemainingBalls() {
        return remainingBalls;
    }
    public Counter getRemainingBlocks() {
        return remainingBlocks;
    }

}