package game;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import collision.Collidable;
import geometry.Velocity;
import hit.BallRemover;
import hit.BlockRemover;
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

public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Sleeper sleeper;
    private Counter remainingBlocks;
    private Counter remainingBalls;

    public Game() {
        initialize();
        run();
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
        this.gui = new GUI("Arkanoid Handout", 800, 600);
        this.environment = new GameEnvironment();
        this.sprites = new SpriteCollection();
        this.sleeper = new Sleeper();
        biuoop.KeyboardSensor keyboard = gui.getKeyboardSensor();
        this.remainingBlocks = new Counter();
        BlockRemover blockRemover = new BlockRemover(this, this.remainingBlocks);

        Block topBorder = new Block(new Rectangle(new Point(0,0), 800, 20), Color.GRAY);
        Block leftBorder = new Block(new Rectangle(new Point(0, 20), 20, 580), Color.GRAY);
        Block rightBorder = new Block(new Rectangle(new Point(780, 20), 20, 580), Color.GRAY);
        Block deathRegion = new Block(new Rectangle(new Point(0, 600), 800, 20), Color.BLACK);
        topBorder.addToGame(this);
        leftBorder.addToGame(this);
        rightBorder.addToGame(this);
        deathRegion.addToGame(this);

        createBlockPattern(blockRemover);

        this.remainingBalls = new Counter();
        this.remainingBalls.increase(3);

        for (int i = 0; i < 3; i++) {
            Ball ball = new Ball(400, 250 + (40 * i), 5, java.awt.Color.WHITE);


            double angle = 330 + (i * 20);
            Velocity v = Velocity.fromAngleAndSpeed(angle, 2);

            ball.setVelocity(v);
            ball.setGameEnvironment(this.environment);
            ball.addToGame(this);
        }
        BallRemover ballRemover = new BallRemover(this, this.remainingBalls);
        deathRegion.addHitListener(ballRemover);


        Paddle paddle = new Paddle(keyboard);
        paddle.addToGame(this);


    }

    private void createBlockPattern(BlockRemover remover) {
        for (int y = 100; y < 250; y += 15 ){
            Color randomColor = getRandomColor();
                for (int x = 100; x < 700; x += 40) {
                Block block = new Block(new Rectangle(new Point(x, y), 40, 15), randomColor);
                block.addToGame(this);
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

    // Run the game -- start the animation loop.
    public void run() {

        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (this.remainingBlocks.getValue() > 0 && this.remainingBalls.getValue() > 0) {
            long startTime = System.currentTimeMillis(); // timing

            DrawSurface d = gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();

            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
        gui.close();
    }
}