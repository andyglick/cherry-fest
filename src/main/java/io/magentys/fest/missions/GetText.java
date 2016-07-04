package io.magentys.fest.missions;

import io.magentys.Agent;
import io.magentys.Mission;
import io.magentys.fest.screens.SwingScreenElement;

import static io.magentys.fest.missions.FindElementInvokeAndActionWithResult.findElementInvokeAndGet;

public class GetText implements Mission<String> {

    private SwingScreenElement element;

    public GetText(final SwingScreenElement element){
        this.element = element;
    }

    public static GetText getText(final SwingScreenElement element) { return new GetText(element); }

    @Override
    public String accomplishAs(Agent agent) {


        return findElementInvokeAndGet(element, "getText", String.class).accomplishAs(agent);
    }
}
