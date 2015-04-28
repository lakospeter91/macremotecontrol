package hu.lakospeter.macremotecontrol.actions.keyboardactions;


import hu.lakospeter.macremotecontrol.Controller;
import hu.lakospeter.macremotecontrol.actions.AAction;

import java.awt.event.KeyEvent;

public class KeyboardSpaceAction extends AAction {

    public KeyboardSpaceAction(Controller controller) {
        super(controller);
    }

    @Override
    public void doAction() {
        controller.pressKey(KeyEvent.VK_SPACE);
    }
}
