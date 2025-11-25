import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.*;
import java.util.Random;

public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Sleeper sleeper;

    public Game() {
        initialize();
    }

    public void addCollidable(Collidable c){
        environment.addCollidable(c);
    }
    public void addSprite(Sprite s){
        sprites.addSprite(s);
    }

    // Initialize a new game: create the Blocks and Ball (and Paddle)
    // and add them to the game.
    public void initialize(){
        this.gui = new GUI("Arkanoid Handout", 800, 600);
        this.environment = new GameEnvironment();
        this.sprites = new SpriteCollection();
        this.sleeper = new Sleeper();
        biuoop.KeyboardSensor keyboard = gui.getKeyboardSensor();

        Block topBorder = new Block(new Rectangle(new Point(0,0), 800, 20), Color.GRAY);
        Block leftBorder = new Block(new Rectangle(new Point(0, 20), 20, 580), Color.GRAY);
        Block rightBorder = new Block(new Rectangle(new Point(780, 20), 20, 580), Color.GRAY);
        Block downBorder = new Block(new Rectangle(new Point(0, 580), 800,20), Color.GRAY);
        topBorder.addToGame(this);
        leftBorder.addToGame(this);
        rightBorder.addToGame(this);
        downBorder.addToGame(this);

        createBlockPattern();

        Ball ball = new Ball(400, 300, 5, java.awt.Color.WHITE);
        ball.setVelocity(2, -2);
        ball.setGameEnvironment(this.environment);
        ball.addToGame(this);

        Paddle paddle = new Paddle(keyboard);
        paddle.addToGame(this);


    }

    private void createBlockPattern() {
        for (int y = 100; y < 250; y += 15 ){
            Color randomColor = getRandomColor();
                for (int x = 100; x < 700; x += 40) {
                Block block = new Block(new Rectangle(new Point(x, y), 40, 15), randomColor);
                block.addToGame(this);
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
        while (true) {
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
    }
}