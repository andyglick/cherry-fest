package io.magentys.fest.missions;

import io.magentys.Agent;
import io.magentys.Mission;

public class WaitFor implements Mission<Agent> {

    protected final Long timeout;

    public WaitFor(Long timeout){
        this.timeout = timeout;
    }

    public static WaitFor waitFor(Long timeout) {
        return new WaitFor(timeout);
    }

    @Override
    public Agent accomplishAs(Agent agent) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return agent;
    }
}

