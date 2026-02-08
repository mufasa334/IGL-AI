package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ScreenScanner {
    // Standard 1080p HUD Coordinates
    private static final Rectangle SPIKE_ZONE = new Rectangle(910, 20, 100, 60);
    private static final Rectangle MINI_MAP_ZONE = new Rectangle(25, 25, 250, 250); // 🎯 The "Visual Zoom" Area

    public static String getGameVariables(BufferedImage screen) {
        boolean spike = isSpikePlanted(screen);

        // Save a zoomed-in version of the minimap for the AI to see clearly
        try {
            BufferedImage mapCrop = screen.getSubimage(MINI_MAP_ZONE.x, MINI_MAP_ZONE.y, MINI_MAP_ZONE.width, MINI_MAP_ZONE.height);
            ImageIO.write(mapCrop, "jpg", new File("minimap_zoom.jpg"));
        } catch (Exception e) { e.printStackTrace(); }

        return "SPIKE_PLANTED: " + spike + " | AGENT: CYPHER | MAP: BIND";
    }

    private static boolean isSpikePlanted(BufferedImage screen) {
        BufferedImage crop = screen.getSubimage(SPIKE_ZONE.x, SPIKE_ZONE.y, SPIKE_ZONE.width, SPIKE_ZONE.height);
        int redCount = 0;
        for (int y = 0; y < crop.getHeight(); y++) {
            for (int x = 0; x < crop.getWidth(); x++) {
                Color c = new Color(crop.getRGB(x, y));
                if (c.getRed() > 180 && c.getGreen() < 80) redCount++;
            }
        }
        return redCount > 400;
    }
}