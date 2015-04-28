package hu.lakospeter.macremotecontrol.actions;


import hu.lakospeter.macremotecontrol.Controller;

public class QuitAction extends AAction {

    public QuitAction(final Controller controller) {
        super(controller);
    }

    @Override
    public void doAction() {
        controller.quit();
    }
}
