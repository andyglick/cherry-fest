package io.magentys.fest.locators;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class AttributeValuePair {

    private final String attribute;
    private final String value;

    public AttributeValuePair(final String attribute, final String value){
        checkNotNull(attribute != null, "attribute was null");
        checkNotNull(value != null, "value was null");
        this.attribute = attribute;
        this.value = value;
    }

    public static AttributeValuePair from(final String attribute, final String value){
        return new AttributeValuePair(attribute, value);
    }

    public String getAttribute() {
        return attribute;
    }

    public String getValue() {
        return value;
    }


    public static AttributeValuePair from(String[] stringPair) {
        checkArgument(stringPair != null && stringPair.length == 2);
        return new AttributeValuePair(stringPair[0],stringPair[1]);
    }

    public String toString(){
        return attribute + "=" + value;
    }
}
