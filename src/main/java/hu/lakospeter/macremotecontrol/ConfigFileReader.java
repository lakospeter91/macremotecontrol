package hu.lakospeter.macremotecontrol;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import hu.lakospeter.macremotecontrol.actions.AAction;
import hu.lakospeter.macremotecontrol.actions.DoNothingAction;
import hu.lakospeter.macremotecontrol.actions.QuitAction;
import hu.lakospeter.macremotecontrol.actions.keyboardactions.*;
import hu.lakospeter.macremotecontrol.actions.mouseactions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigFileReader {
    private static ConfigFileReader instance = null;
    private final String configFilePathAndName;
    private final Controller controller;

    private final static Logger LOG = LoggerFactory.getLogger(ConfigFileReader.class);

    public static ConfigFileReader getInstance(final String configFilePathAndName, final Controller controller) {
        if (instance == null) {
            instance = new ConfigFileReader(configFilePathAndName, controller);
        }
        return instance;
    }

    private ConfigFileReader(final String configFilePathAndName, final Controller controller) {
        this.configFilePathAndName = configFilePathAndName;
        this.controller = controller;
    }

    private String readConfigFile() {
        LOG.debug("Reading config file: " + configFilePathAndName);
        try (final BufferedReader br = new BufferedReader(new FileReader(configFilePathAndName))) {
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }

            return sb.toString();
        } catch (IOException ex) {
            LOG.error("Could not open config file: " + configFilePathAndName, ex);
            return null;
        }
    }

    private Map<RemoteControlAction, AAction> getActionsFromJsonText(final String jsonText) {
        if (jsonText == null || jsonText.length() == 0) {
            return null;
        }

        try {

            final Object configObject = JSON.parse(jsonText);
            if (!(configObject instanceof JSONObject)) {
                throw new JSONException("Root is not an object.");
            }
            final JSONObject configJSONObject = (JSONObject) configObject;

            final Map<RemoteControlAction, AAction> actions = new HashMap<>();

            actions.put(RemoteControlAction.VOLUME_UP_PRESSED,
                    getProperty(configJSONObject.getString("volumeUpPressed")));

            actions.put(RemoteControlAction.VOLUME_UP_HOLD_STARTED,
                    getProperty(configJSONObject.getString("volumeUpHoldStarted")));

            actions.put(RemoteControlAction.VOLUME_UP_HOLD_STOPPED,
                    getProperty(configJSONObject.getString("volumeUpHoldStopped")));

            actions.put(RemoteControlAction.VOLUME_DOWN_PRESSED,
                    getProperty(configJSONObject.getString("volumeDownPressed")));

            actions.put(RemoteControlAction.VOLUME_DOWN_HOLD_STARTED,
                    getProperty(configJSONObject.getString("volumeDownHoldStarted")));

            actions.put(RemoteControlAction.VOLUME_DOWN_HOLD_STOPPED,
                    getProperty(configJSONObject.getString("volumeDownHoldStopped")));

            actions.put(RemoteControlAction.PREVIOUS_PRESSED,
                    getProperty(configJSONObject.getString("previousPressed")));

            actions.put(RemoteControlAction.PREVIOUS_HOLD_STARTED,
                    getProperty(configJSONObject.getString("previousHoldStarted")));

            actions.put(RemoteControlAction.PREVIOUS_HOLD_STOPPED,
                    getProperty(configJSONObject.getString("previousHoldStopped")));

            actions.put(RemoteControlAction.NEXT_PRESSED,
                    getProperty(configJSONObject.getString("nextPressed")));

            actions.put(RemoteControlAction.NEXT_HOLD_STARTED,
                    getProperty(configJSONObject.getString("nextHoldStarted")));

            actions.put(RemoteControlAction.NEXT_HOLD_STOPPED,
                    getProperty(configJSONObject.getString("nextHoldStopped")));

            actions.put(RemoteControlAction.PLAY_PAUSE_PRESSED,
                    getProperty(configJSONObject.getString("playPausePressed")));

            actions.put(RemoteControlAction.PLAY_PAUSE_HELD,
                    getProperty(configJSONObject.getString("playPauseHeld")));

            actions.put(RemoteControlAction.MENU_PRESSED,
                    getProperty(configJSONObject.getString("menuPressed")));

            actions.put(RemoteControlAction.MENU_HELD,
                    getProperty(configJSONObject.getString("menuHeld")));

            actions.put(RemoteControlAction.SELECT_PRESSED,
                    getProperty(configJSONObject.getString("selectPressed")));

            return actions;

        } catch (JSONException ex) {
            LOG.error("Error parsing config JSON.", ex);
            return null;
        }
    }

    private AAction getProperty(final String property) {
        if (property == null || property.length() == 0) {
            return new DoNothingAction(controller);
        } else {
            switch (property) {
                case "keyboardDown"     : return new KeyboardDownAction(controller);
                case "keyboardEnter"    : return new KeyboardEnterAction(controller);
                case "keyboardEscape"   : return new KeyboardEscapeAction(controller);
                case "keyboardLeft"     : return new KeyboardLeftAction(controller);
                case "keyboardRight"    : return new KeyboardRightAction(controller);
                case "keyboardSpace"    : return new KeyboardSpaceAction(controller);
                case "keyboardUp"       : return new KeyboardUpAction(controller);
                case "mouseLeftClick"   : return new MouseLeftClickAction(controller);
                case "mouseMoveDown"    : return new MouseMoveDownAction(controller);
                case "mouseMoveLeft"    : return new MouseMoveLeftAction(controller);
                case "mouseMoveRight"   : return new MouseMoveRightAction(controller);
                case "mouseMoveStop"    : return new MouseMoveStopAction(controller);
                case "mouseMoveUp"      : return new MouseMoveUpAction(controller);
                case "mouseNudgeDown"   : return new MouseNudgeDownAction(controller);
                case "mouseNudgeLeft"   : return new MouseNudgeLeftAction(controller);
                case "mouseNudgeRight"  : return new MouseNudgeRightAction(controller);
                case "mouseNudgeUp"     : return new MouseNudgeUpAction(controller);
                case "mouseRightClick"  : return new MouseRightClickAction(controller);
                case "quit"             : return new QuitAction(controller);
                default                 : return new DoNothingAction(controller);
            }
        }
    }

    public Map<RemoteControlAction, AAction> getActions() {
        final String configFileText = readConfigFile();
        return getActionsFromJsonText(configFileText);
    }
}
