package hu.lakospeter.macremotecontrol.actions;


import hu.lakospeter.macremotecontrol.Controller;

public abstract class AAction {
    protected Controller controller;

    public AAction(final Controller controller) {
        this.controller = controller;
    }

    public abstract void doAction();
}
