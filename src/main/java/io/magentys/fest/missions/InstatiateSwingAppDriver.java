package io.magentys.fest.missions;

import io.magentys.Agent;
import io.magentys.Mission;
import io.magentys.fest.SwingAppDriver;

/**
 * Created by kostasmamalis on 12/09/2016.
 */
public class InstatiateSwingAppDriver implements Mission<Agent> {

    private boolean inEDT = false;

    public InstatiateSwingAppDriver() {
    }

    public InstatiateSwingAppDriver(boolean inEDT) {
        this.inEDT = inEDT;
    }

    public static InstatiateSwingAppDriver initSwingAppDriver(boolean inEDT) {
        return new InstatiateSwingAppDriver(inEDT);
    }

    public static InstatiateSwingAppDriver initSwingAppDriver() {
        return new InstatiateSwingAppDriver();
    }



    @Override
    public Agent accomplishAs(Agent agent) {
        SwingAppDriver swingAppDriver = new SwingAppDriver();
        agent.obtains(swingAppDriver);
        return agent;
    }
}
