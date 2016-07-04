package io.magentys.fest.missions;

import io.magentys.Agent;
import io.magentys.Mission;
import io.magentys.fest.MemoryKeys;
import io.magentys.fest.screens.SwingScreenElement;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;

import static io.magentys.fest.missions.ConcreteTypeMatcher.matcherFor;

public class GetButtonText implements Mission<String> {

    private SwingScreenElement element;

    public GetButtonText(final SwingScreenElement element) {
        this.element = element;
    }

    public static GetButtonText getButtonText(final SwingScreenElement element) {
        return new GetButtonText(element);
    }

    @Override
    public String accomplishAs(Agent agent) {
        FrameFixture window = agent.recalls(MemoryKeys.Screens.MAIN_WINDOW, FrameFixture.class);
        JButtonFixture button = window.button(matcherFor(element));
        return button.text();

    }
}

