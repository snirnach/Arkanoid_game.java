package Menu;
import animations.Animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.util.ArrayList;
import java.util.List;

public class MenuAnimation<T> implements Menu<T> {
    private String menuTitle;
    private KeyboardSensor keyboardSensor;
    private T status = null;
    private boolean stop = false;
    private List<selection> selections;
    //A List<Selection> to store the options.
    //
    //Inner Class: Define a private class Selection inside MenuAnimation with fields: String key, String message, T returnVal.
    private class selection {
        private String key;
        private String message;
        private T returnVal;

        public selection(String key, String message, T returnVal) {
            this.key = key;
            this.message = message;
            this.returnVal = returnVal;
        }
    }

    public MenuAnimation(String menuTitle, KeyboardSensor keyboardSensor) {
        this.menuTitle = menuTitle;
        this.keyboardSensor = keyboardSensor;
        this.status = status;
        this.stop = stop;
        this.selections = new ArrayList<selection>();
    }

    @Override
    public void addSelection(String key, String message, T returnVal) {
        selection newSelection = new selection(key, message, returnVal);
        this.selections.add(newSelection);
    }

    @Override
    public T getStatus() {
        return this.status;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.drawText(100, 50, this.menuTitle, 32);
        int yPosition = 100;
        for (selection sel : this.selections) {
            d.drawText(100, yPosition, sel.key + " - " + sel.message, 24);
            yPosition += 40;
            if (this.keyboardSensor.isPressed(sel.key)) {
                this.status = sel.returnVal;
                this.stop = true;
            }
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

    public void reset() {
        this.stop = false;
        this.status = null;
    }
}
