package io.magentys.fest.missions;

import io.magentys.fest.screens.SwingScreenElement;

public abstract class AbstractFinderMission {

    protected SwingScreenElement swingScreenElement;

    public AbstractFinderMission(SwingScreenElement swingScreenElement) {
        this.swingScreenElement = swingScreenElement;
    }

    protected AbstractFinderMission() {
    }




    protected String waitMessage(final SwingScreenElement swingScreenElement){
        if(swingScreenElement.getAlias() != null){
            return "Waiting for " + swingScreenElement.getAlias();
        }
        return "Waiting for element";
    }



}
