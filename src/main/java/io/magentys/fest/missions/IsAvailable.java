package io.magentys.fest.missions;

import io.magentys.Agent;
import io.magentys.Mission;
import io.magentys.fest.screens.SwingScreenElement;

import static io.magentys.fest.SwingAppMissions.waitForElement;

public class IsAvailable implements Mission<Boolean> {

    protected final SwingScreenElement element;

    public IsAvailable(SwingScreenElement element) {
        this.element = element;
    }

    public Boolean accomplishAs(Agent agent) {
        try {
            agent.performs(waitForElement(this.element, 1000));
            return true;
        } catch (org.assertj.swing.exception.WaitTimedOutError e) {
            return false;
        }

    }
}
