package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ScreenCapture {

    private Robot robot;
    private Rectangle screenRect;

    public ScreenCapture() {
        try {
            this.robot = new Robot();

            // ✅ Explicit full-screen rectangle (clear + hackathon safe)
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            this.screenRect = new Rectangle(0, 0, screenSize.width, screenSize.height);

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /** Takes a screenshot */
    public BufferedImage capture() {
        return robot.createScreenCapture(screenRect);
    }

    /** Saves screenshot to file */
    public void saveImage(BufferedImage image, String filename) {
        try {
            File outputfile = new File(filename);
            ImageIO.write(image, "png", outputfile);
            System.out.println("✅ Screenshot saved: " + outputfile.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("❌ Error saving image: " + e.getMessage());
        }
    }
}
