package game;

import animations.*;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import hit.Counter;
import indicator.LivesIndicator;

import java.util.List;

public class GameFlow {
    private AnimationRunner runner;
    private KeyboardSensor keyboard;
    private biuoop.GUI gui;
    private Counter score;
    private Counter lives;

    public GameFlow(AnimationRunner ar, KeyboardSensor ks, biuoop.GUI gui) {
        this.runner = ar;
        this.keyboard = ks;
        this.gui = gui;
        this.score = new Counter(); // Score is kept across levels!
        this.lives = new Counter();
        this.lives.increase(3);
    }

    public void runLevels(List<LevelInformation> levels) {
        for (LevelInformation levelInfo : levels) {

            GameLevel level = new GameLevel(levelInfo, this.keyboard, this.runner, this.score);
            level.initialize();

            LivesIndicator livesInd = new LivesIndicator(this.lives);
            livesInd.addToGame(level);

            while (level.getRemainingBlocks().getValue() > 0 && this.lives.getValue() > 0) {
                level.run();
                if (level.getRemainingBlocks().getValue() > 0) {
                    this.lives.decrease(1);

                    if (this.lives.getValue() == 0) {
                        break;
                    }
                }
            }

            if (this.lives.getValue() == 0) {
                Animation gameOver = new KeyPressStoppableAnimation(
                        this.keyboard, KeyboardSensor.SPACE_KEY, new GameOver(this.score));
                this.runner.run(gameOver);
                this.gui.close();
                return;
            }

        }

        Animation youWin = new KeyPressStoppableAnimation(
                this.keyboard, KeyboardSensor.SPACE_KEY, new YouWin(this.score));
        this.runner.run(youWin);
        this.gui.close();
    }

}