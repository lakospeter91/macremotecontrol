package hu.lakospeter.macremotecontrol.actions.keyboardactions;


import hu.lakospeter.macremotecontrol.Controller;
import hu.lakospeter.macremotecontrol.actions.AAction;

import java.awt.event.KeyEvent;

public class KeyboardEscapeAction extends AAction {

    public KeyboardEscapeAction(Controller controller) {
        super(controller);
    }

    @Override
    public void doAction() {
        controller.pressKey(KeyEvent.VK_ESCAPE);
    }
}
