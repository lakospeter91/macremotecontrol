package hu.lakospeter.macremotecontrol.actions.mouseactions;


import hu.lakospeter.macremotecontrol.Controller;
import hu.lakospeter.macremotecontrol.actions.AAction;

public class MouseNudgeRightAction extends AAction {

    public MouseNudgeRightAction(final Controller controller) {
        super(controller);
    }

    @Override
    public void doAction() {
        controller.nudgeMouse(Controller.Direction.RIGHT);
    }
}
