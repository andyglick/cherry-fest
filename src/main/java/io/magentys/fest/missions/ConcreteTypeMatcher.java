package io.magentys.fest.missions;

import io.magentys.exceptions.ScreenException;
import io.magentys.fest.locators.AttributeValuePair;
import io.magentys.fest.screens.SwingScreenElement;
import org.assertj.swing.core.GenericTypeMatcher;


import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ConcreteTypeMatcher<T extends Component> extends GenericTypeMatcher<T> {
    private final SwingScreenElement<T> swingScreenElement;
    private Set<AttributeValuePair> attributes;
    Class<T> type;
    private String methodToInvoke;
    private Object[] arguments;



    public ConcreteTypeMatcher(final SwingScreenElement<T> swingScreenElement){
        super(swingScreenElement.getComponentClass());
        this.swingScreenElement = swingScreenElement;
        this.attributes = swingScreenElement.getAttributes();
        this.type = swingScreenElement.getComponentClass();
    }

    public ConcreteTypeMatcher(SwingScreenElement swingScreenElement, String methodToInvoke, Object[] arguments) {
        this(swingScreenElement);
        this.methodToInvoke = methodToInvoke;
        this.arguments = arguments;
    }


    public static ConcreteTypeMatcher matcherFor(final SwingScreenElement swingScreenElement){
        return new ConcreteTypeMatcher<>(swingScreenElement);
    }

    public static ConcreteTypeMatcher matcherFor(final SwingScreenElement swingScreenElement, String methodToInvoke, Object... arguments){
        return new ConcreteTypeMatcher<>(swingScreenElement, methodToInvoke, arguments);
    }

    @Override
    protected boolean isMatching(T component) {
        boolean result = type.isInstance(component);
        if(!result) return false;
        if(attributes != null && attributes.size() != 0 ) {
            result = testAllAttributesOf(component);
        }
        if(methodToInvoke != null){
            invokeRequiredMethodOn(component);
        }
        return result;
    }

    protected void invokeRequiredMethodOn(T component){
        Method[] allMethods = component.getClass().getMethods();
        Optional<Method> methodOptional = Stream.of(allMethods).filter(method -> method.getName().equalsIgnoreCase(methodToInvoke)).findFirst();
        if(methodOptional.isPresent()) {
            try {
                if(arguments != null && arguments.length > 0)  methodOptional.get().invoke(component, arguments);
                else methodOptional.get().invoke(component);
            } catch (IllegalAccessException|InvocationTargetException e) {
                throw new ScreenException("Error upon invoking " + methodToInvoke + " on " + swingScreenElement.getAlias());
            }
        } else {
            throw new ScreenException("No method " + methodToInvoke + " found on " + swingScreenElement.getAlias());
        }
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

    protected boolean testAllAttributesOf(Component component) {
        Optional<Boolean> result = attributes.stream().map(attribute -> testAttribute(component,attribute)).reduce((a,b) -> a && b);
        return result.isPresent() && result.get();
    }

    /*








    protected String waitMessage(final SwingScreenElement swingScreenElement){
        if(swingScreenElement.getAlias() != null){
            return "Waiting for " + swingScreenElement.getAlias();
        }
        return "Waiting for element";
    }
     */
}
