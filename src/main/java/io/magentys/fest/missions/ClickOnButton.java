package io.magentys.fest.missions;

import io.magentys.Agent;
import io.magentys.Mission;
import io.magentys.fest.screens.SwingScreenElement;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JButtonFixture;

import static io.magentys.fest.SwingAppMissions.getMatcher;


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
        FrameFixture window = agent.recalls("main.window", FrameFixture.class);
        if(label != null){
            window.button(label).click();
        } else {
            JButtonFixture button = window.button(getMatcher(element).accomplishAs(agent));
            button.click();
        }
        return agent;
    }
}
