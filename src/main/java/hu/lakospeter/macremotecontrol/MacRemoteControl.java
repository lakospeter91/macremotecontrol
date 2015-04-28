package hu.lakospeter.macremotecontrol;

import hu.lakospeter.appleremote4j.AppleRemoteEvent;
import hu.lakospeter.appleremote4j.AppleRemoteListener;
import hu.lakospeter.macremotecontrol.actions.*;
import hu.lakospeter.macremotecontrol.actions.mouseactions.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Remotely controls your Mac computer.
 */
public class MacRemoteControl implements AppleRemoteListener {

    private final Controller controller;
    private final Map<RemoteControlAction, AAction> actions = new HashMap<>();

    private MacRemoteControl() {
        controller = new Controller(this);
        addActions();
    }

    private enum RemoteControlAction {
        VOLUME_UP_PRESSED,
        VOLUME_UP_HOLD_STARTED,
        VOLUME_UP_HOLD_STOPPED,
        VOLUME_DOWN_PRESSED,
        VOLUME_DOWN_HOLD_STARTED,
        VOLUME_DOWN_HOLD_STOPPED,
        PREVIOUS_PRESSED,
        PREVIOUS_HOLD_STARTED,
        PREVIOUS_HOLD_STOPPED,
        NEXT_PRESSED,
        NEXT_HOLD_STARTED,
        NEXT_HOLD_STOPPED,
        PLAY_PAUSE_PRESSED,
        PLAY_PAUSE_HELD,
        MENU_PRESSED,
        MENU_HELD,
        SELECT_PRESSED
    }

    private void addActions() {
        // TODO read these from config files
        actions.put(RemoteControlAction.VOLUME_UP_PRESSED, new MouseNudgeUpAction(controller));
        actions.put(RemoteControlAction.VOLUME_UP_HOLD_STARTED, new MouseMoveUpAction(controller));
        actions.put(RemoteControlAction.VOLUME_UP_HOLD_STOPPED, new MouseMoveStopAction(controller));
        actions.put(RemoteControlAction.VOLUME_DOWN_PRESSED, new MouseNudgeDownAction(controller));
        actions.put(RemoteControlAction.VOLUME_DOWN_HOLD_STARTED, new MouseMoveDownAction(controller));
        actions.put(RemoteControlAction.VOLUME_DOWN_HOLD_STOPPED, new MouseMoveStopAction(controller));
        actions.put(RemoteControlAction.PREVIOUS_PRESSED, new MouseNudgeLeftAction(controller));
        actions.put(RemoteControlAction.PREVIOUS_HOLD_STARTED, new MouseMoveLeftAction(controller));
        actions.put(RemoteControlAction.PREVIOUS_HOLD_STOPPED, new MouseMoveStopAction(controller));
        actions.put(RemoteControlAction.NEXT_PRESSED, new MouseNudgeRightAction(controller));
        actions.put(RemoteControlAction.NEXT_HOLD_STARTED, new MouseMoveRightAction(controller));
        actions.put(RemoteControlAction.NEXT_HOLD_STOPPED, new MouseMoveStopAction(controller));
        actions.put(RemoteControlAction.PLAY_PAUSE_PRESSED, new MouseLeftClickAction(controller));
        actions.put(RemoteControlAction.PLAY_PAUSE_HELD, new QuitAction(controller));
        actions.put(RemoteControlAction.MENU_PRESSED, new MouseRightClickAction(controller));
        actions.put(RemoteControlAction.MENU_HELD, new DoNothingAction(controller));
        actions.put(RemoteControlAction.SELECT_PRESSED, new MouseLeftClickAction(controller));
    }

    @Override
    public void volumeUpPressed(AppleRemoteEvent e) {
        actions.get(RemoteControlAction.VOLUME_UP_PRESSED).doAction();
    }

    @Override
    public void volumeUpHoldStarted(AppleRemoteEvent e) {
        actions.get(RemoteControlAction.VOLUME_UP_HOLD_STARTED).doAction();
    }

    @Override
    public void volumeUpHoldStopped(AppleRemoteEvent e) {
        actions.get(RemoteControlAction.VOLUME_UP_HOLD_STOPPED).doAction();
    }

    @Override
    public void volumeDownPressed(AppleRemoteEvent e) {
        actions.get(RemoteControlAction.VOLUME_DOWN_PRESSED).doAction();
    }

    @Override
    public void volumeDownHoldStarted(AppleRemoteEvent e) {
        actions.get(RemoteControlAction.VOLUME_DOWN_HOLD_STARTED).doAction();
    }

    @Override
    public void volumeDownHoldStopped(AppleRemoteEvent e) {
        actions.get(RemoteControlAction.VOLUME_DOWN_HOLD_STOPPED).doAction();
    }

    @Override
    public void previousPressed(AppleRemoteEvent e) {
        actions.get(RemoteControlAction.PREVIOUS_PRESSED).doAction();
    }

    @Override
    public void previousHoldStarted(AppleRemoteEvent e) {
        actions.get(RemoteControlAction.PREVIOUS_HOLD_STARTED).doAction();
    }

    @Override
    public void previousHoldStopped(AppleRemoteEvent e) {
        actions.get(RemoteControlAction.PREVIOUS_HOLD_STOPPED).doAction();
    }

    @Override
    public void nextPressed(AppleRemoteEvent e) {
        actions.get(RemoteControlAction.NEXT_PRESSED).doAction();
    }

    @Override
    public void nextHoldStarted(AppleRemoteEvent e) {
        actions.get(RemoteControlAction.NEXT_HOLD_STARTED).doAction();
    }

    @Override
    public void nextHoldStopped(AppleRemoteEvent e) {
        actions.get(RemoteControlAction.NEXT_HOLD_STOPPED).doAction();
    }

    @Override
    public void playPausePressed(AppleRemoteEvent e) {
        actions.get(RemoteControlAction.PLAY_PAUSE_PRESSED).doAction();
    }

    @Override
    public void playPauseHeld(AppleRemoteEvent e) {
        actions.get(RemoteControlAction.PLAY_PAUSE_HELD).doAction();
    }

    @Override
    public void menuPressed(AppleRemoteEvent e) {
        actions.get(RemoteControlAction.MENU_PRESSED).doAction();
    }

    @Override
    public void menuHeld(AppleRemoteEvent e) {
        actions.get(RemoteControlAction.MENU_HELD).doAction();
    }

    @Override
    public void selectPressed(AppleRemoteEvent e) {
        actions.get(RemoteControlAction.SELECT_PRESSED).doAction();
    }

    public static void main(String[] args) {
        new MacRemoteControl();
    }
}
