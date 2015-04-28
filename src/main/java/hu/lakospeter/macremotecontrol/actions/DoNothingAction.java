package hu.lakospeter.macremotecontrol.actions;


import hu.lakospeter.macremotecontrol.Controller;

public class DoNothingAction extends AAction {

    public DoNothingAction(final Controller controller) {
        super(controller);
    }

    @Override
    public void doAction() {
        // DO NOTHING
    }
}
