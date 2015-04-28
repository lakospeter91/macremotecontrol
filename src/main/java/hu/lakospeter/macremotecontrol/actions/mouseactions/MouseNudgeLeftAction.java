package hu.lakospeter.macremotecontrol.actions.mouseactions;


import hu.lakospeter.macremotecontrol.Controller;
import hu.lakospeter.macremotecontrol.actions.AAction;

public class MouseNudgeLeftAction extends AAction {

    public MouseNudgeLeftAction(final Controller controller) {
        super(controller);
    }

    @Override
    public void doAction() {
        controller.nudgeMouse(Controller.Direction.LEFT);
    }
}
