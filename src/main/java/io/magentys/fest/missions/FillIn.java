package io.magentys.fest.missions;

import io.magentys.Agent;
import io.magentys.Mission;
import io.magentys.fest.MemoryKeys;
import io.magentys.fest.screens.SwingScreenElement;
import org.assertj.swing.fixture.FrameFixture;


import static com.google.common.base.Preconditions.checkNotNull;
import static io.magentys.fest.missions.ConcreteTypeMatcher.matcherFor;

public class FillIn implements Mission<Agent> {

    private final SwingScreenElement swingScreenElement;
    private final String value;

    public FillIn(final SwingScreenElement swingScreenElement, final String value) {
        checkNotNull("element was empty", swingScreenElement);
        this.swingScreenElement = swingScreenElement;
        this.value = value;
    }

    public static FillIn fillIn(final SwingScreenElement swingScreenElement, final String value) {
        return new FillIn(swingScreenElement, value);
    }

    @Override
    public Agent accomplishAs(Agent agent) {
        FrameFixture window = agent.recalls(MemoryKeys.Screens.MAIN_WINDOW, FrameFixture.class);
        if(swingScreenElement.getName() != null) {
            window.textBox(swingScreenElement.getName()).enterText(value);
        } else {
            window.textBox(matcherFor(swingScreenElement)).enterText(value);
        }
        return agent;
    }
}
