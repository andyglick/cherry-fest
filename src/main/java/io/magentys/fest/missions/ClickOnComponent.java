package io.magentys.fest.missions;

import io.magentys.Agent;
import io.magentys.Mission;
import io.magentys.fest.MemoryKeys;
import io.magentys.fest.screens.SwingScreenElement;
import org.assertj.swing.core.Robot;

import static io.magentys.fest.missions.ConcreteTypeMatcher.matcherFor;

public class ClickOnComponent implements Mission<Agent> {

    private SwingScreenElement element;

    protected ClickOnComponent(SwingScreenElement element) {
        this.element = element;
    }

    public static ClickOnComponent clickOn(final SwingScreenElement element) {
        return new ClickOnComponent(element);
    }

    public Agent accomplishAs(Agent agent) {
        Robot robot = agent.recalls(MemoryKeys.Screens.ROBOT, Robot.class);
        robot.click(robot.finder().find(matcherFor(element)));
        return agent;
    }
}
