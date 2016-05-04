package io.magentys.fest.missions;

import io.magentys.Agent;
import io.magentys.Mission;
import io.magentys.fest.screens.SwingScreenElement;
import org.fest.swing.core.ComponentFoundCondition;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.timing.Pause;

import javax.swing.*;
import java.awt.*;

public class FindElement extends AbstractFinderMission implements Mission<Component> {

    protected final SwingScreenElement swingScreenElement;

    public FindElement(SwingScreenElement swingScreenElement) {
        this.swingScreenElement = swingScreenElement;
    }

    @Override
    public Component accomplishAs(Agent agent) {
        final Component[] result = new Component[1];
        GenericTypeMatcher matcher;

        if(swingScreenElement.getComponentClass().equals(JLabel.class)){

            matcher = new GenericTypeMatcher<JLabel>(JLabel.class) {
                @Override
                protected boolean isMatching(JLabel component) {
                    return testAllAttributesOf(component, swingScreenElement);
                }
            };

        } else {
            matcher = new GenericTypeMatcher<Component>(Component.class) {
                @Override
                protected boolean isMatching(Component component) {
                    boolean isInstanceOfExpectedClass = component.getClass().equals(swingScreenElement.getComponentClass());
                    if (swingScreenElement.getAttributes() == null) {
                        if (isInstanceOfExpectedClass) result[0] = component;
                        return isInstanceOfExpectedClass;
                    }
                    boolean matched = isInstanceOfExpectedClass && testAllAttributesOf(component, swingScreenElement);
                    if (matched) result[0] = component;
                    return matched;
                }
            };
        }

        Pause.pause(new ComponentFoundCondition(waitMessage(swingScreenElement),  agent.recalls("main.window", FrameFixture.class).robot.finder(), matcher), 60000);

        return result[0];
    }





}
