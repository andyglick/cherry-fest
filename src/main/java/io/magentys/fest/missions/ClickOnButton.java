package io.magentys.fest.missions;

import com.mns.pos.beanstore.MemoryKeys;
import com.mns.pos.beanstore.fest.screens.SwingScreenElement;
import io.magentys.Agent;
import io.magentys.Mission;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JButtonFixture;

import static com.mns.pos.beanstore.fest.SwingAppMissions.getMatcher;

public class ClickOnButton implements Mission<Agent> {

    private String label;
    private SwingScreenElement element;

    public ClickOnButton(SwingScreenElement element) {
        this.element = element;
    }

    public ClickOnButton(String label) {
        this.label = label;
    }

    public Agent accomplishAs(Agent agent) {
        FrameFixture window = agent.recalls(MemoryKeys.Screens.MAIN_WINDOW, FrameFixture.class);
        if(label != null){
            window.button(label).click();
        } else {
            JButtonFixture button = window.button(getMatcher(element).accomplishAs(agent));
            button.click();
        }
        return agent;
    }
}
