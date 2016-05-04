package io.magentys.fest.screens;

import io.magentys.fest.locators.AttributeValuePair;
import io.magentys.screens.annotations.ScreenElement;

import java.awt.*;
import java.util.Set;

public class SwingScreenElement<T extends Component> implements ScreenElement {

    private String alias;
    private Class<T> clazz;
    private String memoryKey;
    private String name;
    private String defaultValue;
    private Set<AttributeValuePair> attributePairs;

    public String getAlias() {
        return alias;
    }

    public SwingScreenElement withAlias(String alias) {
        this.alias = alias;
        return this;
    }

    @Override
    public ScreenElement withDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public SwingScreenElement withName(String name) {
        this.name = name;
        return this;
    }

    public SwingScreenElement withMemoryKey(String memoryKey) {
        this.memoryKey = memoryKey;
        return this;
    }

    public SwingScreenElement withComponentClass(Class<T> clazz) {
        this.clazz = clazz;
        return this;
    }

    public SwingScreenElement withAttributes(final Set<AttributeValuePair> attributePairs){
        this.attributePairs = attributePairs;
        return this;
    }

    public Class<T> getComponentClass() {
        return clazz;
    }

    public String getMemoryKey(){
        return memoryKey;
    }

    public String getName() {
        return name;
    }

    public Set<AttributeValuePair> getAttributes() {
        return attributePairs;
    }
}
