package hu.lakospeter.macremotecontrol.actions.mouseactions;


import hu.lakospeter.macremotecontrol.Controller;
import hu.lakospeter.macremotecontrol.MouseButton;
import hu.lakospeter.macremotecontrol.actions.AAction;

public class MouseRightClickAction extends AAction {

    public MouseRightClickAction(final Controller controller) {
        super(controller);
    }

    @Override
    public void doAction() {
        controller.clickMouse(MouseButton.RIGHT);
    }

}
