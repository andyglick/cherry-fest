package io.magentys;

import io.magentys.fest.locators.FindBy;
import io.magentys.fest.screens.SwingScreen;
import io.magentys.fest.screens.SwingScreenElement;
import io.magentys.fest.screens.SwingScreenFactory;
import io.magentys.screens.annotations.Alias;

import javax.swing.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SwingScreenFactoryTest {

    @org.junit.Test
    public void shouldInstantiateSwingScreensWithElements() throws Exception {
        LoginScreen loginScreen = new SwingScreenFactory().init(new LoginScreen());
        assertThat(loginScreen.operatorID.getAlias(), is("Operator ID"));
        assertThat(loginScreen.operatorID.getMemoryKey(), is("operatorID"));
        assertThat(loginScreen.operatorID.getComponentClass().getCanonicalName(), is(JLabel.class.getCanonicalName()));
    }

    public class LoginScreen extends SwingScreen {

        @Alias("Operator ID")
        @FindBy(clazz = JLabel.class, attributes = "text=Operator ID,name=operateID", rememberAs = "operatorID")
        public SwingScreenElement operatorID;



    }
}

