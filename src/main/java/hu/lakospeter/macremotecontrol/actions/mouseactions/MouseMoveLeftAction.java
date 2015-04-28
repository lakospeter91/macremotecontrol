package hu.lakospeter.macremotecontrol.actions.mouseactions;


import hu.lakospeter.macremotecontrol.Controller;
import hu.lakospeter.macremotecontrol.Direction;
import hu.lakospeter.macremotecontrol.actions.AAction;

public class MouseMoveLeftAction extends AAction {

    public MouseMoveLeftAction(final Controller controller) {
        super(controller);
    }

    @Override
    public void doAction() {
        controller.startMoveMouse();
        controller.moveMouse(Direction.LEFT);
    }
}
