package io.magentys.fest.missions;

import io.magentys.Agent;
import io.magentys.Mission;
import io.magentys.exceptions.ScreenException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TakeScreenShot implements Mission<Agent> {

    private final String filename;
    private final String format;

    public TakeScreenShot(final String filename, final String format) {
        this.filename = filename;
        this.format = format;
    }

    @Override
    public Agent accomplishAs(Agent agent) {
        try {
            Robot robot = new Robot();
            String fileName = filename + "." + format;
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
            ImageIO.write(screenFullImage, format, new File(fileName));
        } catch (IOException | AWTException e) {
            throw new ScreenException(e.getMessage());
        }
        return agent;
    }
}
