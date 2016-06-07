package io.magentys.fest;


import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.edt.GuiTask;


public class SwingAppDriver {

    private Robot robot;
    private boolean isRunning;
    private Long defaultWaitingTimeout = 60000L;

    public void setRunningTo(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public Robot robot() {
        return robot;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public SwingAppDriver init(GuiTask guiTask){
        GuiActionRunner.execute(guiTask);
        robot = BasicRobot.robotWithCurrentAwtHierarchy();
        return this;
    }

    public Long getDefaultWaitingTimeout() {
        return defaultWaitingTimeout;
    }

    public void setDefaultWaitingTimeout(Long defaultWaitingTimeout) {
        this.defaultWaitingTimeout = defaultWaitingTimeout;
    }
}
