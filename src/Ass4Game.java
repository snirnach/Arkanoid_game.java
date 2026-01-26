import animations.AnimationRunner;
import levels.DirectHit;
import game.GameFlow;
import game.LevelInformation;
import levels.FinalFour;
import levels.Green3;
import levels.WideEasy;
import biuoop.GUI;
import java.util.ArrayList;
import java.util.List;

public class Ass4Game {
    public static void main(String[] args) {
        //  Create a list of levels
        List<LevelInformation> levels = new ArrayList<>();

        if (args.length == 0) {
            levels.add(new DirectHit());
            levels.add(new WideEasy());
            levels.add(new Green3());
            levels.add(new FinalFour());
        } else {
            for (String arg : args) {
                try {
                    int levelNum = Integer.parseInt(arg);

                    switch (arg) {
                        case "1":
                            levels.add(new DirectHit());
                            break;
                        case "2":
                            levels.add(new WideEasy());
                            break;
                        case "3":
                            levels.add(new Green3());
                            break;
                        case "4":
                            levels.add(new FinalFour());
                            break;
                        default:
                            // Ignore invalid level numbers
                            break;
                    }

                } catch (NumberFormatException e) {
                    // Ignore non-integer arguments
                    continue;
                }
            }

        }
        if (levels.isEmpty()) {
            return;
        }

        //  Create GUI and Runner
        biuoop.GUI gui = new biuoop.GUI("Arkanoid", 800, 600);
        AnimationRunner runner = new AnimationRunner(gui);

        //  Create the GameFlow
        GameFlow flow = new GameFlow(runner, gui.getKeyboardSensor(), gui);


        // Run the levels
        flow.runLevels(levels);
    }
}
