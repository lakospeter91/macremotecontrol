package hu.lakospeter.macremotecontrol.actions.mouseactions;


import hu.lakospeter.macremotecontrol.Controller;
import hu.lakospeter.macremotecontrol.actions.AAction;

public class MouseMoveStopAction extends AAction {

    public MouseMoveStopAction(final Controller controller) {
        super(controller);
    }

    @Override
    public void doAction() {
        controller.stopMoveMouse();
    }
}
