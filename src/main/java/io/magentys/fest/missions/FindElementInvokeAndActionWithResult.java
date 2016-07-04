package io.magentys.fest.missions;

import io.magentys.Agent;
import io.magentys.Mission;
import io.magentys.fest.MemoryKeys;
import io.magentys.fest.screens.SwingScreenElement;
import org.assertj.swing.core.ComponentFoundCondition;
import org.assertj.swing.core.Robot;
import org.assertj.swing.timing.Pause;


import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.magentys.fest.missions.ConcreteTypeMatcher.matcherFor;

public class FindElementInvokeAndActionWithResult<T> implements Mission<T> {


    private final String methodToInvoke;
    private final Object[] arguments;
    private final Class<T> resultClass;
    private final SwingScreenElement swingScreenElement;

    public FindElementInvokeAndActionWithResult(final SwingScreenElement swingScreenElement,
                                                final String methodToInvoke,
                                                final Class<T> resultClass,
                                                final Object... arguments) {
        checkNotNull(methodToInvoke);
        this.swingScreenElement = swingScreenElement;
        this.methodToInvoke = methodToInvoke;
        this.arguments = arguments;
        this.resultClass = resultClass;
    }

    public static <RESULT> FindElementInvokeAndActionWithResult<RESULT> findElementInvokeAndGet(final SwingScreenElement swingScreenElement,
                                                                                                final String methodToInvoke,
                                                                                                final Class<RESULT> resultClass,
                                                                                                final Object... arguments){
        return new FindElementInvokeAndActionWithResult(swingScreenElement, methodToInvoke, resultClass, arguments);
    }

    @Override

    public T accomplishAs(Agent agent) {
        String key = UUID.randomUUID().toString();
        Pause.pause(
                new ComponentFoundCondition(swingScreenElement.getAlias(),
                        agent.recalls(MemoryKeys.Screens.ROBOT, Robot.class).finder(),
                        matcherFor(swingScreenElement, agent, methodToInvoke, key, resultClass, arguments)), 60000);
        return agent.recalls(key,resultClass);
    }


}
