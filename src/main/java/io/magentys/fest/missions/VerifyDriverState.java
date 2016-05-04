package io.magentys.fest.missions;

import io.magentys.Agent;
import io.magentys.Mission;
import io.magentys.fest.SwingAppDriver;

public class VerifyDriverState implements Mission<Boolean> {
    @Override
    public Boolean accomplishAs(Agent agent) {
        return agent.usingThe(SwingAppDriver.class).isRunning();
    }
}
