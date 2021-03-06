package io.magentys.fest.screens;

import io.magentys.fest.i18n.Translator;
import io.magentys.fest.locators.AttributeValuePair;
import io.magentys.fest.locators.FindBy;
import io.magentys.screens.Screen;
import io.magentys.screens.ScreenFactory;
import io.magentys.screens.annotations.Alias;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SwingScreenFactory extends ScreenFactory {

    Translator translator;

    public SwingScreenFactory(){}

    public SwingScreenFactory(final Translator translator){
        this.translator = translator;
    }


    @Override
    protected <T extends Screen> void instantiateAndRemember(T screen, Field field) throws IllegalAccessException {
        String aliasValue = field.isAnnotationPresent(Alias.class) ? field.getAnnotation(Alias.class).value() : null;

        Class<? extends Component> clazz = null;
        String memoryKey = null;
        Set<AttributeValuePair> pairs = new HashSet<>();

        if (field.isAnnotationPresent(FindBy.class)){
            FindBy findBy = field.getAnnotation(FindBy.class);
            memoryKey = findBy.rememberAs();
            clazz = field.isAnnotationPresent(FindBy.class) ? field.getAnnotation(FindBy.class).clazz() : null;
            if(!"".equals(findBy.attributes())){
                String[] stringPairs = findBy.attributes().split(",");
                pairs = Stream.of(stringPairs)
                        .map(stringPair -> AttributeValuePair.from(stringPair.split("=")))
                        .map(avp -> {
                            if(isTextAttribute(avp)) {
                                String translation = translator.translate(avp.getValue());
                                if(translation == null || "".equals(translation)) translation = avp.getValue();
                                return AttributeValuePair.from(avp.getAttribute(), translation);
                            }
                            else {
                                return avp;
                            }
                        })
                        .collect(Collectors.toSet());
            }


        }

        if (isElement(field)) {
            SwingScreenElement screenElement = new SwingScreenElement().withAlias(aliasValue);
            if(clazz != null) screenElement.withComponentClass(clazz);
            if(memoryKey != null) screenElement.withMemoryKey(memoryKey);
            if(pairs.size() != 0) screenElement.withAttributes(pairs);
            field.set(screen, screenElement);
            screen.addScreenElement(screenElement);
        }

    }

    private boolean isTextAttribute(AttributeValuePair avp) {
        return avp.getAttribute().startsWith("text") && translator != null;
    }


}

