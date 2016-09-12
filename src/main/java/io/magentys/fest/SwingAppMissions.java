package io.magentys.fest;


import io.magentys.fest.missions.*;
import io.magentys.fest.screens.SwingScreenElement;

import javax.swing.*;

public class SwingAppMissions {

    public static FindFrame getFrame(Class<? extends JFrame> clazz, String rememberAsKey) {
        return new FindFrame(clazz, rememberAsKey);
    }

    public static FindElement findElement(final SwingScreenElement swingScreenElement) {
        return new FindElement(swingScreenElement);
    }

    public static WaitForElement waitForElement(final SwingScreenElement swingScreenElement) {
        return new WaitForElement(swingScreenElement);
    }

    public static WaitForElement waitForElement(final SwingScreenElement swingScreenElement, Integer timeout) {
        return new WaitForElement(swingScreenElement, timeout);
    }

    public static WaitFor waitFor(final Long timeout) {
        return new WaitFor(timeout);
    }

    public static FindElementAndInvokeAction findElementAndInvoke(final SwingScreenElement swingScreenElement, String invokeAction, Object... arguments) {
        return new FindElementAndInvokeAction(swingScreenElement, invokeAction, arguments);
    }

    public static TakeScreenShot takeScreenShotAndSaveAs(final String filename, final String format){
        return new TakeScreenShot(filename,format);
    }

    public static VerifyDriverState swingDriverRunningState() {
        return new VerifyDriverState();
    }


    public static ClickOnButton clickOnButton(final SwingScreenElement swingScreenElement){
        return new ClickOnButton(swingScreenElement);
    }

    public static ClickOnButton clickOnButton(final String label){
        return new ClickOnButton(label);
    }

}
