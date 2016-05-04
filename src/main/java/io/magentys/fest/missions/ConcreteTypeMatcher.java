package io.magentys.fest.missions;

import org.fest.swing.core.GenericTypeMatcher;

import java.awt.*;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ConcreteTypeMatcher<T extends Component> extends GenericTypeMatcher<T> {
    private Predicate[] predicates;
    Class<T> type;
    Boolean condition = true;

    public ConcreteTypeMatcher(Class<T> supportedType, Predicate... predicates) {
        super(supportedType);
        this.type = supportedType;
        this.predicates = predicates;
    }

    public ConcreteTypeMatcher(Class<T> supportedType, Boolean condition){
        super(supportedType);
        this.condition = condition;
    }

    public static <T extends Component> ConcreteTypeMatcher<T> matcherOf(Class<T> supportedType, Predicate... predicates){
        return new ConcreteTypeMatcher<>(supportedType, predicates);
    }

    public static <T extends Component> ConcreteTypeMatcher<T> matcherOf(Class<T> supportedType, Boolean condition){
        return new ConcreteTypeMatcher<>(supportedType, condition);
    }

    @Override
    protected boolean isMatching(T component) {
        if(predicates == null || predicates.length == 0 ) {
            return type.isInstance(component) && condition;
        }
        return type.isInstance(component) && testAllPredicatesFor(component);
    }

    protected boolean testAllPredicatesFor(T component){
        Optional<Boolean> predicatesResult = Stream.of(predicates).map(predicate -> predicate.test(component)).reduce((a, b) -> a && b);
        return predicatesResult.isPresent() && predicatesResult.get();
    }
}
