package io.magentys.fest.screens;

import io.magentys.screens.Screen;
import io.magentys.screens.annotations.ScreenElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by db2admin on 19/04/2016.
 */
public class SwingScreen implements Screen {

    private List<ScreenElement> screenElements = new ArrayList<ScreenElement>();

    /**
     * Getter method for screenElements for customized usage
     * @return all elements in screen
     */
    public List<ScreenElement> screenElements() {
        return screenElements;
    }

    public Screen addScreenElements(ScreenElement... elements){
        for(ScreenElement screenElement : elements){
            if(!this.screenElements.contains(screenElement)){
                this.screenElements.add(screenElement);
            }
        }
        return this;
    }

    public ScreenElement getScreenElementWithAlias(String alias) {
        for(ScreenElement swingElement : screenElements){
            if(alias.equalsIgnoreCase(swingElement.getAlias())) return swingElement;
        }
        throw new RuntimeException("element not found for getAlias '" + alias + "'. Available ones are: " + screenElements);
    }

    public void addScreenElement(ScreenElement screenElement) {
        screenElements.add(screenElement);
    }
}
