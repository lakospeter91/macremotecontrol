package hu.lakospeter.macremotecontrol;

import hu.lakospeter.appleremote4j.AppleRemoteEvent;
import hu.lakospeter.appleremote4j.AppleRemoteListener;
import hu.lakospeter.macremotecontrol.actions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Remotely controls your Mac computer.
 */
public class MacRemoteControl implements AppleRemoteListener {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigFileReader.class);
    // TODO add ability to select this config file from a file chooser
    private static final String CONFIG_FILE_NAME = "config.json";
    private static final String CONFIG_FILE_PATH_AND_NAME = System.getProperty("user.home")
                                                    + "/Library/Application Support/hu.lakospeter.macremotecontrol/"
                                                    + CONFIG_FILE_NAME;
    private Map<RemoteControlAction, AAction> actions;

    private MacRemoteControl() {
        final Controller controller = new Controller(this);
        try {
            createConfigFileIfNotExist();
            final ConfigFileReader configFileReader = ConfigFileReader.getInstance(CONFIG_FILE_PATH_AND_NAME, controller);
            actions = configFileReader.getActions();
        } catch (Exception ex) {
            LOG.error("Could not create the config file.");
        }
    }

    public void createConfigFileIfNotExist() throws Exception {
        final File configFile = new File(CONFIG_FILE_PATH_AND_NAME);
        if (!configFile.exists() || configFile.isDirectory()) {
            configFile.getParentFile().mkdirs();
            configFile.createNewFile();
            exportDefaultConfigFileFromJar();
        }
    }

    private void exportDefaultConfigFileFromJar() throws Exception {
        final InputStream inputStream = MacRemoteControl.class.getResourceAsStream("/" + CONFIG_FILE_NAME);
        if(inputStream == null) {
            throw new Exception("Could not find config file in the macremotecontrol jar file.");
        }
        final OutputStream outputStream = new FileOutputStream(CONFIG_FILE_PATH_AND_NAME);

        int readBytes;
        byte[] buffer = new byte[4096];
        while ((readBytes = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, readBytes);
        }

        inputStream.close();
        outputStream.close();
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
