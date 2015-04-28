package hu.lakospeter.macremotecontrol.actions.mouseactions;


import hu.lakospeter.macremotecontrol.Controller;
import hu.lakospeter.macremotecontrol.Direction;
import hu.lakospeter.macremotecontrol.actions.AAction;

public class MouseNudgeUpAction extends AAction {

    public MouseNudgeUpAction(final Controller controller) {
        super(controller);
    }

    @Override
    public void doAction() {
        controller.nudgeMouse(Direction.UP);
    }
}
