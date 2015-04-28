package hu.lakospeter.macremotecontrol.actions.keyboardactions;


import hu.lakospeter.macremotecontrol.Controller;
import hu.lakospeter.macremotecontrol.actions.AAction;

import java.awt.event.KeyEvent;

public class KeyboardLeftAction extends AAction {

    public KeyboardLeftAction(final Controller controller) {
        super(controller);
    }

    @Override
    public void doAction() {
        controller.pressKey(KeyEvent.VK_LEFT);
    }
}
