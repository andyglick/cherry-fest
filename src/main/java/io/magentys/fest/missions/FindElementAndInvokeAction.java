package io.magentys.fest.missions;

import io.magentys.Agent;
import io.magentys.fest.MemoryKeys;
import io.magentys.fest.screens.SwingScreenElement;
import org.assertj.swing.core.ComponentFoundCondition;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.timing.Pause;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.magentys.fest.missions.ConcreteTypeMatcher.matcherFor;

public class FindElementAndInvokeAction extends FindElement {

    private final String methodToInvoke;
    private final Object[] arguments;

    public FindElementAndInvokeAction(SwingScreenElement swingScreenElement, String methodToInvoke, Object... arguments) {
        super(swingScreenElement);
        checkNotNull(methodToInvoke);
        this.methodToInvoke = methodToInvoke;
        this.arguments = arguments;
    }

    @Override
    public Agent accomplishAs(Agent agent) {
        Pause.pause(
                new ComponentFoundCondition(waitMessage(swingScreenElement),
                        agent.recalls(MemoryKeys.Screens.ROBOT, Robot.class).finder(),
                        matcherFor(swingScreenElement, methodToInvoke, arguments)), 60000);
        return agent;
    }

}
