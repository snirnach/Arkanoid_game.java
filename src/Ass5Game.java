import Menu.MenuAnimation;
import Menu.Task;
import animations.Animation;
import animations.HighScoresAnimation;
import animations.KeyPressStoppableAnimation;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import animations.AnimationRunner;
import game.*;
import java.io.*;
import java.util.List;
import java.util.Objects;

public class Ass5Game {

    public static void main(String[] args) {
        String levelDefinitionsPath = (args.length > 0) ? args[0] : "level_definitions.txt";

        GUI gui = new GUI("Arkanoid", 800, 600);
        AnimationRunner runner = new AnimationRunner(gui);
        KeyboardSensor keyboard = gui.getKeyboardSensor();

        MenuAnimation<Task<Void>> menu = new MenuAnimation<Task<Void>>("Arkanoid Menu", keyboard);

        menu.addSelection("s", "Start Game", new Task<Void>() {
            @Override
            public Void run() {
                LevelSpecificationReader reader = new LevelSpecificationReader();
                List<LevelInformation> levels = null;

                try {
                    Reader fileReader = new InputStreamReader(
                            Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResourceAsStream(levelDefinitionsPath)));

                    levels = reader.fromReader(fileReader);

                } catch (Exception e) {
                    System.err.println("Error reading level definitions file: " + e.getMessage());
                    return null;
                }

                if (levels != null && !levels.isEmpty()) {
                    GameFlow game = new GameFlow(runner, keyboard, gui);
                    game.runLevels(levels);
                }

                return null;
            }
        });

        menu.addSelection("h", "High Scores", new Task<Void>() {
            @Override
            public Void run() {
                int highScore = HighScoreManager.getHighScore();
                Animation scoresAnim = new HighScoresAnimation(highScore, "space", keyboard);
                runner.run(new KeyPressStoppableAnimation(keyboard, "space", scoresAnim));
                return null;
            }
        });

        menu.addSelection("q", "Quit", new Task<Void>() {
            @Override
            public Void run() {
                System.exit(0);
                return null;
            }
        });

        while (true) {
            runner.run(menu);
            Task<Void> task = menu.getStatus();
            if (task != null) {
                task.run();
            }
            menu.reset();
        }
    }
}