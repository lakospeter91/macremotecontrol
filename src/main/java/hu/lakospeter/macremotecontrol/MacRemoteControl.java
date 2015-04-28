package hu.lakospeter.macremotecontrol;

import hu.lakospeter.appleremote4j.AppleRemoteEvent;
import hu.lakospeter.appleremote4j.AppleRemoteListener;
import hu.lakospeter.macremotecontrol.actions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Remotely controls your Mac computer.
 */
public class MacRemoteControl implements AppleRemoteListener {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigFileReader.class);
    // TODO add ability to select this config file from a file chooser
    private static final String CONFIG_FILE_SYSTEM_PROPERTY = "hu.lakospeter.macremotecontrol.configFile";
    private static final String DEFAULT_CONFIG_FILE_PATH_AND_NAME = "config/default.json";
    private final Map<RemoteControlAction, AAction> actions;

    private MacRemoteControl() {
        final Controller controller = new Controller(this);
        final String configFilePathAndName = getConfigFilePathAndName();
        final ConfigFileReader configFileReader = ConfigFileReader.getInstance(configFilePathAndName, controller);
        actions = configFileReader.getActions();
    }

    private String getConfigFilePathAndName() {
        String configFilePathAndName = System.getProperty(CONFIG_FILE_SYSTEM_PROPERTY);
        if (configFilePathAndName == null || configFilePathAndName.length() == 0) {
            LOG.warn("Invalid config file path specified. Using " + DEFAULT_CONFIG_FILE_PATH_AND_NAME);
            return DEFAULT_CONFIG_FILE_PATH_AND_NAME;
        }
        return configFilePathAndName;
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
