package hu.lakospeter.macremotecontrol.actions.mouseactions;


import hu.lakospeter.macremotecontrol.Controller;
import hu.lakospeter.macremotecontrol.actions.AAction;

public class MouseLeftClickAction extends AAction {

    public MouseLeftClickAction(final Controller controller) {
        super(controller);
    }

    @Override
    public void doAction() {
        controller.clickMouse(Controller.MouseButton.LEFT);
    }
}
