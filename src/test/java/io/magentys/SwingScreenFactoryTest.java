package io.magentys;

import io.magentys.fest.i18n.Translator;
import io.magentys.fest.locators.AttributeValuePair;
import io.magentys.fest.locators.FindBy;
import io.magentys.fest.screens.SwingScreen;
import io.magentys.fest.screens.SwingScreenElement;
import io.magentys.fest.screens.SwingScreenFactory;
import io.magentys.screens.annotations.Alias;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import static org.mockito.Mockito.*;

public class SwingScreenFactoryTest {

    Translator translator = mock(Translator.class);

    @Before
    public void setup(){
        when(translator.translate("Operator ID")).thenReturn("Operater");
    }

    @Test
    public void shouldInstantiateSwingScreensWithElements() throws Exception {
        LoginScreen loginScreen = new SwingScreenFactory().init(new LoginScreen());
        assertThat(loginScreen.operatorID.getAlias(), is("Operator ID"));
        assertThat(loginScreen.operatorID.getMemoryKey(), is("operatorID"));
        assertThat(loginScreen.operatorID.getComponentClass().getCanonicalName(), is(JLabel.class.getCanonicalName()));
    }

    @Test
    public void shouldTranslateAsInstructed() throws Exception {
        SwingScreenFactory i18nScreenFactory = new SwingScreenFactory(translator);
        LoginScreen loginScreen = i18nScreenFactory.init(new LoginScreen());
        AttributeValuePair attributeValuePair = (AttributeValuePair) loginScreen.operatorID.getAttributes().stream().filter(avp -> ( (AttributeValuePair) avp).getAttribute().startsWith("text")).findFirst().get();
        assertThat(attributeValuePair.getValue(), is("Operater"));
    }

    public class LoginScreen extends SwingScreen {

        @Alias("Operator ID")
        @FindBy(clazz = JLabel.class, attributes = "text=Operator ID,name=operateID", rememberAs = "operatorID")
        public SwingScreenElement operatorID;
    }


}

