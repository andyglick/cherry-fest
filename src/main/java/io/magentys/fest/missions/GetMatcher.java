package io.magentys.fest.missions;

import com.mns.pos.beanstore.fest.screens.SwingScreenElement;
import io.magentys.Agent;
import io.magentys.Mission;
import org.fest.swing.core.ComponentFoundCondition;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.timing.Pause;

import java.awt.*;

public class GetMatcher extends AbstractFinderMission implements Mission<GenericTypeMatcher> {


    public GetMatcher(SwingScreenElement swingScreenElement) {
        super(swingScreenElement);
    }


    @Override
    public GenericTypeMatcher accomplishAs(Agent agent) {
        final GenericTypeMatcher<Component> matcher = new GenericTypeMatcher<Component>(Component.class) {
            @Override protected boolean isMatching(Component component){
                boolean isInstanceOfExpectedClass = component.getClass().equals(swingScreenElement.getComponentClass());
                return swingScreenElement.getAttributes().size() == 0 ? isInstanceOfExpectedClass :isInstanceOfExpectedClass && testAllAttributesOf(component, swingScreenElement);
            }
        };

        Pause.pause(new ComponentFoundCondition(waitMessage(swingScreenElement),  agent.recalls("mainWindow", FrameFixture.class).robot.finder(), matcher), 60000);

        return matcher;
    }



}
