package animations;

import biuoop.DrawSurface;

public interface Animation {
    // Method to process one frame of the animation
    void doOneFrame(DrawSurface d);
    // Method to check if the animation should stop
    boolean shouldStop();
}
