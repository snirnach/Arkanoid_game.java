package animations;
// This class handles the game loop.
// It takes an Animation object and runs it using the GUI and Sleeper to maintain 60 FPS.

import biuoop.DrawSurface;

public class AnimationRunner {

    private biuoop.GUI gui;
    private biuoop.Sleeper sleeper;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECONDS_PER_FRAME = 1000 / FRAMES_PER_SECOND;

    public AnimationRunner(biuoop.GUI gui) {
        this.gui = gui;
        this.sleeper = new biuoop.Sleeper();
    }

    public void run(Animation animation){
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing

            // Create a new DrawSurface
            DrawSurface d = gui.getDrawSurface();

            // Let the animation do one frame
            animation.doOneFrame(d);

            // Show the drawn surface on the GUI
            gui.show(d);

            // Timing to maintain consistent frame rate
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = MILLISECONDS_PER_FRAME - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

}
