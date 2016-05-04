package io.magentys.fest.missions;

import com.mns.pos.beanstore.fest.SwingAppDriver;
import io.magentys.Agent;
import io.magentys.Mission;

public class VerifyDriverState implements Mission<Boolean> {
    @Override
    public Boolean accomplishAs(Agent agent) {
        return agent.usingThe(SwingAppDriver.class).isRunning();
    }
}
