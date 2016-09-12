package io.magentys.fest.missions;

import io.magentys.Agent;
import io.magentys.Mission;
import io.magentys.fest.screens.SwingScreenElement;

import static io.magentys.fest.SwingAppMissions.waitForElement;

public class IsAvailable implements Mission<Boolean> {

    protected final SwingScreenElement element;
    protected final Integer timeout;

    public IsAvailable(SwingScreenElement element) {
        this.element = element;
        this.timeout = 1000;
    }

    public IsAvailable(SwingScreenElement element, Integer timeout) {
        this.element = element;
        this.timeout = timeout;
    }

    public Boolean accomplishAs(Agent agent) {
        try {
            agent.performs(waitForElement(this.element, this.timeout));
            return true;
        } catch (org.assertj.swing.exception.WaitTimedOutError e) {
            return false;
        }

    }
}
