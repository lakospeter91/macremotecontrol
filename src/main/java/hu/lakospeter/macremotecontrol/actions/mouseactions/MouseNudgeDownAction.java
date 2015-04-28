package hu.lakospeter.macremotecontrol.actions.mouseactions;


import hu.lakospeter.macremotecontrol.Controller;
import hu.lakospeter.macremotecontrol.Direction;
import hu.lakospeter.macremotecontrol.actions.AAction;

public class MouseNudgeDownAction extends AAction {

    public MouseNudgeDownAction(final Controller controller) {
        super(controller);
    }

    @Override
    public void doAction() {
        controller.nudgeMouse(Direction.DOWN);
    }
}
