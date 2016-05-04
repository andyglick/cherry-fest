package io.magentys.fest.missions;

import io.magentys.Agent;
import io.magentys.Mission;
import io.magentys.fest.SwingAppDriver;
import org.fest.swing.core.ComponentFoundCondition;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.timing.Pause;

import javax.swing.*;


public class FindFrame implements Mission<GenericTypeMatcher<JFrame>> {


    public final GenericTypeMatcher<JFrame> frameMatcher = new GenericTypeMatcher<JFrame>(JFrame.class) {

        @Override
        protected boolean isMatching(JFrame frame) {
            return frame.getClass() == clazz;
        }
    };

    private final Class<? extends JFrame> clazz;
    private final String rememberAsKey;


    public FindFrame(Class<? extends JFrame> clazz, String rememberAs) {
        this.clazz = clazz;
        this.rememberAsKey = rememberAs;
    }

    public GenericTypeMatcher<JFrame> accomplishAs(Agent agent) {
        SwingAppDriver swingAppDriver = agent.usingThe(SwingAppDriver.class);
        Pause.pause(new ComponentFoundCondition("Waiting for Frame to load", swingAppDriver.robot().finder(),  new GenericTypeMatcher<JFrame>(JFrame.class) {
            @Override
            protected boolean isMatching(JFrame frame) {
                return frame.getClass() == clazz;
            }
        }), swingAppDriver.getDefaultWaitingTimeout());
        FrameFixture window = WindowFinder.findFrame(frameMatcher).using(swingAppDriver.robot());
        agent.keepsInMind(rememberAsKey,window);
        return frameMatcher;
    }


}
