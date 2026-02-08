package org.example;

import java.awt.image.BufferedImage;
import java.awt.Color;

public class ScreenScanner {

    /** =========================
     * 🧩 STEP 1: Agent Detection
     * ========================= */
    public static String detectAgent(BufferedImage img) {
        if (img == null) return "UNKNOWN_AGENT";

        // Manual override from Launcher property
        String override = System.getProperty("agent", "AUTO");
        if (!override.equals("AUTO")) return override;

        // HUD ability color sampling (bottom-center)
        int x = (int)(img.getWidth() * 0.62);
        int y = (int)(img.getHeight() * 0.92);

        Color abilityColor = new Color(img.getRGB(x, y));

        // Sage: Teal/Greenish-blue
        if (abilityColor.getGreen() > 180 && abilityColor.getBlue() > 150) {
            return "SAGE";
        }

        // Neon example (yellow-blue)
        if (abilityColor.getRed() > 200 && abilityColor.getGreen() > 150) {
            return "NEON";
        }

        return "UNKNOWN_AGENT";
    }

    /** =========================
     * 🧩 STEP 2: Phase Detection (REGION SCAN)
     * ========================= */
    public static String detectPhase(BufferedImage img) {
        if (img == null) return "UNKNOWN_PHASE";

        int w = img.getWidth();
        int h = img.getHeight();
        int redCount = 0;

        // Scan top-center HUD region for spike icon
        for (int x = w / 2 - 20; x < w / 2 + 20; x++) {
            for (int y = 20; y < 60; y++) {
                Color c = new Color(img.getRGB(x, y));

                if (c.getRed() > 200 && c.getGreen() < 80 && c.getBlue() < 80) {
                    redCount++;
                }
            }
        }

        // Debug print (optional, comment out after testing)
        // System.out.println("Spike red pixel count = " + redCount);

        if (redCount > 50) {
            return "POST_PLANT";
        }

        return "COMBAT";
    }

    /** =========================
     * Helper: Spike Visibility
     * ========================= */
    public static boolean isSpikeVisible(BufferedImage img) {
        return detectPhase(img).equals("POST_PLANT");
    }
}
