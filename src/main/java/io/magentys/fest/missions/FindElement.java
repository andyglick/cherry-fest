package io.magentys.fest.missions;

import io.magentys.Agent;
import io.magentys.Mission;
import io.magentys.fest.MemoryKeys;
import io.magentys.fest.screens.SwingScreenElement;
import org.assertj.swing.core.ComponentFoundCondition;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.timing.Pause;


import static io.magentys.fest.missions.ConcreteTypeMatcher.matcherFor;

public class FindElement extends AbstractFinderMission implements Mission<Agent> {

    protected final SwingScreenElement swingScreenElement;

    public FindElement(SwingScreenElement swingScreenElement) {
        this.swingScreenElement = swingScreenElement;
    }

    @Override
    public Agent accomplishAs(Agent agent) {
        final Robot robot = agent.recalls(MemoryKeys.Screens.ROBOT, Robot.class);
        Pause.pause(
                new ComponentFoundCondition(waitMessage(swingScreenElement),
                        robot.finder(),
                        matcherFor(swingScreenElement)),
                60000);
        return agent;
    }






}
