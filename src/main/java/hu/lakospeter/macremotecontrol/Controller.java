package hu.lakospeter.macremotecontrol;


import hu.lakospeter.appleremote4j.AppleRemote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.concurrent.atomic.AtomicBoolean;

public class Controller {
    private final AppleRemote appleRemote;
    private Robot robot;
    private final int screenWidth;
    private final int screenHeight;
    private AtomicBoolean shouldMoveMouse = new AtomicBoolean(false);
    private final int mouseNudgeStep;
    private static final int MOUSE_NUDGE_CONSTANT = 200;
    private static final int MOUSE_MOVE_DELAY = 10; // milliseconds
    private static final Logger LOG = LoggerFactory.getLogger(MacRemoteControl.class);

    public Controller(final MacRemoteControl macRemoteControl) {
        final GraphicsDevice graphicsDevice =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        screenWidth = graphicsDevice.getDisplayMode().getWidth();
        screenHeight = graphicsDevice.getDisplayMode().getHeight();

        mouseNudgeStep = screenWidth / MOUSE_NUDGE_CONSTANT; // move more pixels at once on a higher resolution screen

        appleRemote = new AppleRemote();
        appleRemote.addAppleRemoteListener(macRemoteControl);

        try {
            robot = new Robot();
        } catch (AWTException ex) {
            LOG.error("Could not start AWT robot.", ex);
        }

        if (robot == null) {
            quit();
        }
    }

    private Point getMousePosition() {
        final PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        return pointerInfo.getLocation();
    }

    public void nudgeMouse(final Direction direction, final int distance) {
        final Point currentPosition = getMousePosition();
        final int currentPositionX = currentPosition.x;
        final int currentPositionY = currentPosition.y;

        if (direction == Direction.UP) {
            if (currentPositionY - distance >= 0) {
                robot.mouseMove(currentPositionX, currentPositionY - distance);
            } else {
                robot.mouseMove(currentPositionX, 0);
            }
        } else if (direction == Direction.DOWN) {
            if (currentPositionY + distance <= screenHeight) {
                robot.mouseMove(currentPositionX, currentPositionY + distance);
            } else {
                robot.mouseMove(currentPositionX, screenHeight);
            }
        } else if (direction == Direction.LEFT) {
            if (currentPositionX - distance >= 0) {
                robot.mouseMove(currentPositionX - distance, currentPositionY);
            } else {
                robot.mouseMove(0, currentPositionY);
            }
        } else if (direction == Direction.RIGHT) {
            if (currentPositionX + distance <= screenWidth) {
                robot.mouseMove(currentPositionX + distance, currentPositionY);
            } else {
                robot.mouseMove(screenWidth, currentPositionY);
            }
        }
    }

    public void nudgeMouse(final Direction direction) {
        nudgeMouse(direction, mouseNudgeStep);
    }

    public void moveMouse(final Direction direction, final int delay) {
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

    public void moveMouse(final Direction direction) {
        moveMouse(direction, MOUSE_MOVE_DELAY);
    }

    public synchronized void startMoveMouse() {
        shouldMoveMouse.set(true);
    }

    public synchronized void stopMoveMouse() {
        shouldMoveMouse.set(false);
    }

    public void clickMouse(final MouseButton mouseButton) {
        if (mouseButton == MouseButton.LEFT) {
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        } else if (mouseButton == MouseButton.RIGHT) {
            robot.mousePress(InputEvent.BUTTON3_MASK);
            robot.mouseRelease(InputEvent.BUTTON3_MASK);
        }
    }

    public void pressKey(final int keyCode) {
        robot.keyPress(keyCode);
    }

    public void quit() {
        appleRemote.stopRunning();
    }
}
