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
            // "Robot" is a Java tool that can control mouse/keyboard and screen
            this.robot = new Robot();
            // Get the size of your main monitor
            this.screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes a screenshot of the entire screen.
     */
    public BufferedImage capture() {
        return robot.createScreenCapture(screenRect);
    }

    /**
     * Saves the image to a file (so we can test if it works)
     */
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