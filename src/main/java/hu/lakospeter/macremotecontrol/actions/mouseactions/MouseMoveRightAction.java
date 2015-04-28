package hu.lakospeter.macremotecontrol.actions.mouseactions;


import hu.lakospeter.macremotecontrol.Controller;
import hu.lakospeter.macremotecontrol.actions.AAction;

public class MouseMoveRightAction extends AAction {

    public MouseMoveRightAction(final Controller controller) {
        super(controller);
    }

    @Override
    public void doAction() {
        controller.startMoveMouse();
        controller.moveMouse(Controller.Direction.RIGHT);
    }
}
