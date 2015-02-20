package hu.lakospeter.macremotecontrol;

import hu.lakospeter.appleremote4j.AppleRemote;
import hu.lakospeter.appleremote4j.AppleRemoteEvent;
import hu.lakospeter.appleremote4j.AppleRemoteListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Remotely controls your Mac computer.
 */
public class MacRemoteControl implements AppleRemoteListener {

    private final AppleRemote appleRemote;
    private Robot robot;
    private final int screenWidth;
    private final int screenHeight;
    private AtomicBoolean shouldMoveMouse = new AtomicBoolean(false);
    private final int mouseNudgeStep;
    private static final int MOUSE_NUDGE_CONSTANT = 200;
    private static final int MOUSE_MOVE_DELAY = 10; // milliseconds
    private static final Logger LOG = LoggerFactory.getLogger(MacRemoteControl.class);

    private enum DIRECTION {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private enum MOUSEBUTTON {
        LEFT,
        RIGHT
    }

    private MacRemoteControl() {
        final GraphicsDevice graphicsDevice =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        screenWidth = graphicsDevice.getDisplayMode().getWidth();
        screenHeight = graphicsDevice.getDisplayMode().getHeight();

        mouseNudgeStep = screenWidth / MOUSE_NUDGE_CONSTANT; // move more pixels at once on a higher resolution screen

        appleRemote = new AppleRemote();
        appleRemote.addAppleRemoteListener(this);

        try {
            robot = new Robot();
        } catch (AWTException ex) {
            LOG.error("Could not start AWT robot.", ex);
        }

        if (robot == null) {
            appleRemote.stopRunning();
        }
    }

    private Point getMousePosition() {
        final PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        return pointerInfo.getLocation();
    }

    private void nudgeMouse(final DIRECTION direction, final int distance) {
        final Point currentPosition = getMousePosition();
        final int currentPositionX = currentPosition.x;
        final int currentPositionY = currentPosition.y;

        if (direction == DIRECTION.UP) {
            if (currentPositionY - distance >= 0) {
                robot.mouseMove(currentPositionX, currentPositionY - distance);
            } else {
                robot.mouseMove(currentPositionX, 0);
            }
        } else if (direction == DIRECTION.DOWN) {
            if (currentPositionY + distance <= screenHeight) {
                robot.mouseMove(currentPositionX, currentPositionY + distance);
            } else {
                robot.mouseMove(currentPositionX, screenHeight);
            }
        } else if (direction == DIRECTION.LEFT) {
            if (currentPositionX - distance >= 0) {
                robot.mouseMove(currentPositionX - distance, currentPositionY);
            } else {
                robot.mouseMove(0, currentPositionY);
            }
        } else if (direction == DIRECTION.RIGHT) {
            if (currentPositionX + distance <= screenWidth) {
                robot.mouseMove(currentPositionX + distance, currentPositionY);
            } else {
                robot.mouseMove(screenWidth, currentPositionY);
            }
        }
    }

    private void nudgeMouse(final DIRECTION direction) {
        nudgeMouse(direction, mouseNudgeStep);
    }

    private void moveMouse(final DIRECTION direction, final int delay) {
        new Thread() {
            @Override
            public void run() {
                while (shouldMoveMouse.get()) {
                    nudgeMouse(direction);

                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException ex) {
                        LOG.error("Thread interrupted.", ex);
                    }
                }
            }
        }.start();
    }

    private void moveMouse(final DIRECTION direction) {
        moveMouse(direction, MOUSE_MOVE_DELAY);
    }

    private void clickMouse(final MOUSEBUTTON mouseButton) {
        if (mouseButton == MOUSEBUTTON.LEFT) {
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        } else if (mouseButton == MOUSEBUTTON.RIGHT) {
            robot.mousePress(InputEvent.BUTTON3_MASK);
            robot.mouseRelease(InputEvent.BUTTON3_MASK);
        }
    }

    @Override
    public void volumeUpPressed(AppleRemoteEvent e) {
        nudgeMouse(DIRECTION.UP);
    }

    @Override
    public void volumeUpHoldStarted(AppleRemoteEvent e) {
        shouldMoveMouse.set(true);
        moveMouse(DIRECTION.UP);
    }

    @Override
    public void volumeUpHoldStopped(AppleRemoteEvent e) {
        shouldMoveMouse.set(false);
    }

    @Override
    public void volumeDownPressed(AppleRemoteEvent e) {
        nudgeMouse(DIRECTION.DOWN);
    }

    @Override
    public void volumeDownHoldStarted(AppleRemoteEvent e) {
        shouldMoveMouse.set(true);
        moveMouse(DIRECTION.DOWN);
    }

    @Override
    public void volumeDownHoldStopped(AppleRemoteEvent e) {
        shouldMoveMouse.set(false);
    }

    @Override
    public void previousPressed(AppleRemoteEvent e) {
        nudgeMouse(DIRECTION.LEFT);
    }

    @Override
    public void previousHoldStarted(AppleRemoteEvent e) {
        shouldMoveMouse.set(true);
        moveMouse(DIRECTION.LEFT);
    }

    @Override
    public void previousHoldStopped(AppleRemoteEvent e) {
        shouldMoveMouse.set(false);
    }

    @Override
    public void nextPressed(AppleRemoteEvent e) {
        nudgeMouse(DIRECTION.RIGHT);
    }

    @Override
    public void nextHoldStarted(AppleRemoteEvent e) {
        shouldMoveMouse.set(true);
        moveMouse(DIRECTION.RIGHT);
    }

    @Override
    public void nextHoldStopped(AppleRemoteEvent e) {
        shouldMoveMouse.set(false);
    }

    @Override
    public void playPausePressed(AppleRemoteEvent e) {
        clickMouse(MOUSEBUTTON.LEFT);
    }

    @Override
    public void playPauseHeld(AppleRemoteEvent e) {
        appleRemote.stopRunning();
    }

    @Override
    public void menuPressed(AppleRemoteEvent e) {
        clickMouse(MOUSEBUTTON.RIGHT);
    }

    @Override
    public void menuHeld(AppleRemoteEvent e) {

    }

    @Override
    public void selectPressed(AppleRemoteEvent e) {
        clickMouse(MOUSEBUTTON.LEFT);
    }

    public static void main(String[] args) {
        new MacRemoteControl();
    }
}
