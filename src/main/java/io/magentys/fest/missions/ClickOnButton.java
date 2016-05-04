package io.magentys.fest.missions;

import io.magentys.Agent;
import io.magentys.Mission;
import io.magentys.fest.MemoryKeys;
import io.magentys.fest.screens.SwingScreenElement;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JButtonFixture;

import static io.magentys.fest.missions.ConcreteTypeMatcher.matcherFor;


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
            JButtonFixture button = window.button(matcherFor(element));
            button.click();
        }
        return agent;
    }
}
