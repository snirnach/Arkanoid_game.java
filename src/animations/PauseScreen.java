package animations;
import java.awt.Color;

// This animation displays "paused -- press space to continue" on the screen.
// It should stop when the SPACE key is pressed.
// Fields: KeyboardSensor keyboard, boolean stop.

public class PauseScreen  implements Animation{
    private biuoop.KeyboardSensor keyboard;
    private boolean stop;

    public PauseScreen(biuoop.KeyboardSensor keyboard) {
        this.keyboard = keyboard;
        this.stop = false;
    }

    @Override
    public void doOneFrame(biuoop.DrawSurface d) {
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(Color.WHITE);
        d.drawText(200, d.getHeight() / 2, "paused -- press space to continue", 32);
        if (keyboard.isPressed(biuoop.KeyboardSensor.SPACE_KEY)) {
            this.stop = true;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

}
