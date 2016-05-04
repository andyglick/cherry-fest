package io.magentys.fest.missions;

import io.magentys.Agent;
import io.magentys.Mission;
import io.magentys.fest.MemoryKeys;
import io.magentys.fest.screens.SwingScreenElement;
import org.fest.swing.core.ComponentFoundCondition;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.timing.Pause;

import javax.swing.*;
import java.awt.*;

import static io.magentys.fest.missions.ConcreteTypeMatcher.matcherFor;

public class FindElement extends AbstractFinderMission implements Mission<Agent> {

    protected final SwingScreenElement swingScreenElement;

    public FindElement(SwingScreenElement swingScreenElement) {
        this.swingScreenElement = swingScreenElement;
    }

    @Override
    public Agent accomplishAs(Agent agent) {
        Pause.pause(
                new ComponentFoundCondition(waitMessage(swingScreenElement),
                        agent.recalls(MemoryKeys.Screens.MAIN_WINDOW, FrameFixture.class).robot.finder(),
                        matcherFor(swingScreenElement)),
                60000);
        return agent;
    }






}
