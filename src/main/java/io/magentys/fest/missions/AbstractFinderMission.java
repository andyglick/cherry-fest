package io.magentys.fest.missions;

import io.magentys.exceptions.ScreenException;
import io.magentys.fest.locators.AttributeValuePair;
import io.magentys.fest.screens.SwingScreenElement;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class AbstractFinderMission {

    protected SwingScreenElement swingScreenElement;

    public AbstractFinderMission(SwingScreenElement swingScreenElement) {
        this.swingScreenElement = swingScreenElement;
    }

    protected AbstractFinderMission() {
    }

    protected <T extends Component> boolean testAttribute(T component, AttributeValuePair attributeValuePair){
        if("name".equalsIgnoreCase(attributeValuePair.getAttribute())) return attributeValuePair.getValue().equals(component.getName());
        if("visible".equalsIgnoreCase(attributeValuePair.getAttribute())) return Boolean.parseBoolean(attributeValuePair.getValue()) == (component.isVisible());
        if("enabled".equalsIgnoreCase(attributeValuePair.getAttribute())) return Boolean.parseBoolean(attributeValuePair.getValue()) == (component.isEnabled());
        else {
            Method[] allMethods = component.getClass().getMethods();
            String attribute = attributeValuePair.getAttribute();
            Predicate<Object> predicate = object -> object != null;
            String expectedValue = attributeValuePair.getValue();

            if(attributeValuePair.getAttribute().endsWith("-contains")){
                attribute = attributeValuePair.getAttribute().replace("-contains","");
                predicate = predicate.and(object -> String.valueOf(object).contains(expectedValue));
            } else if(attributeValuePair.getAttribute().endsWith("-startsWith")){
                attribute = attributeValuePair.getAttribute().replace("-startsWith","");
                predicate = predicate.and(object -> String.valueOf(object).startsWith(expectedValue));
            } else if(attributeValuePair.getAttribute().endsWith("-endsWith")){
                attribute = attributeValuePair.getAttribute().replace("-endsWith","");
                predicate = predicate.and(object -> String.valueOf(object).endsWith(expectedValue));
            } else {
                predicate = predicate.and(object -> String.valueOf(object).equals(expectedValue));
            }
            final String attributeResult = attribute;
            Optional<Method> methodOptional = Stream.of(allMethods).filter(method -> method.getName().equalsIgnoreCase(attributeResult) || method.getName().equals(toMethodName(attributeResult))).findFirst();
            boolean result  = invokeMethodAndGetResult(component, methodOptional, predicate);
            return result;
        }
    }

    protected String capitalizeFirstLetterOf(String input){
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    protected String toMethodName(String input){
        return "get" + capitalizeFirstLetterOf(input);
    }

    protected boolean invokeMethodAndGetResult(final Component component, Optional<Method> methodOptional, Predicate<Object> predicate) {
        if(methodOptional.isPresent()){
            try {

                Method method = methodOptional.get();
                Object result = method.invoke(component);
                return predicate.test(result);
            } catch (IllegalAccessException |InvocationTargetException e) {
                throw new ScreenException(e.getMessage());
            }
        }
        return false;
    }

    protected boolean testAllAttributesOf(Component component, SwingScreenElement swingScreenElement) {
        Optional<Boolean> result = swingScreenElement.getAttributes().stream().map(attribute -> testAttribute(component,attribute)).reduce((a,b) -> a && b);
        return result.isPresent() && result.get();
    }



    protected String waitMessage(final SwingScreenElement swingScreenElement){
        if(swingScreenElement.getAlias() != null){
            return "Waiting for " + swingScreenElement.getAlias();
        }
        return "Waiting for element";
    }



}
