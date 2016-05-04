package io.magentys.fest.missions;

import com.mns.pos.beanstore.fest.screens.SwingScreenElement;
import io.magentys.Agent;
import io.magentys.exceptions.ScreenException;
import org.fest.swing.core.ComponentFoundCondition;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.timing.Pause;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkNotNull;

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
    public Component accomplishAs(Agent agent) {
        final Component[] result = new Component[1];
        final GenericTypeMatcher<Component> matcher = new GenericTypeMatcher<Component>(Component.class) {
            @Override protected boolean isMatching(Component component){
                boolean isInstanceOfExpectedClass = component.getClass().equals(swingScreenElement.getComponentClass());

                //TODO fix me
                boolean matched = swingScreenElement.getAttributes().size() == 0 ? isInstanceOfExpectedClass : isInstanceOfExpectedClass && testAllAttributesOf(component, swingScreenElement);
                if(matched) {
                    result[0] = component;
                    Method[] allMethods = component.getClass().getMethods();
                    Optional<Method> methodOptional = Stream.of(allMethods).filter(method -> method.getName().equalsIgnoreCase(methodToInvoke)).findFirst();
                    if(methodOptional.isPresent()) {
                        try {
                            if(arguments != null && arguments.length > 0)  methodOptional.get().invoke(component, arguments);
                            else methodOptional.get().invoke(component);
                        } catch (IllegalAccessException|InvocationTargetException e) {
                            throw new ScreenException("Error upon invoking " + methodToInvoke + " on " + swingScreenElement.getAlias());
                        }
                    } else {
                        throw new ScreenException("No method " + methodToInvoke + " found on " + swingScreenElement.getAlias());
                    }
                }

                return matched;
            }
        };

        Pause.pause(new ComponentFoundCondition(waitMessage(swingScreenElement),  agent.recalls("mainWindow", FrameFixture.class).robot.finder(), matcher), 60000);

        return result[0];
    }


}
