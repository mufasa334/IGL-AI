package org.example;

import java.awt.image.BufferedImage;
import java.io.File;
import javafx.application.Platform;

public class Main {

    /** 🧩 STEP 5: Entry Point Fix */
    public static void main(String[] args) {
        // This launches your Neural Link Cyberpunk UI first
        javafx.application.Application.launch(Launcher.class, args);
    }

    public static void runGameLoop() throws InterruptedException {

        System.out.println("🚀 Radiant Engine Online...");
        ScreenCapture capturer = new ScreenCapture();
        Overlay.launchOverlay();

        while (true) {
            try {
                BufferedImage image = capturer.capture();
                if (image == null) continue;

                String agent = ScreenScanner.detectAgent(image);
                String phase = ScreenScanner.detectPhase(image);

                File imageFile = new File("game_state.png");
                capturer.saveImage(image, "game_state.png");

                System.out.println("Detected Agent=" + agent + " Phase=" + phase);

                // Get AI strategy
                String strategy = GeminiBot.getStrategy(imageFile, agent, phase);
                System.out.println("AI RAW OUTPUT: " + strategy);

                if (strategy == null || strategy.isEmpty()) {
                    strategy = "COACH: " + agent + " | Hold angle | Playing safe fallback | CONFIDENCE: LOW";
                }

                // 🚫 Remove hallucinated ult/team info
                String lower = strategy.toLowerCase();
                if (lower.contains("ultimate") || lower.contains("3v") || lower.contains("4v") || lower.contains("numbers")) {
                    strategy = "COACH: " + agent + " | Hold angles and play time | Spike planted, avoid overpeeking | CONFIDENCE: HIGH";
                }

                Overlay.updateText(strategy);
                TextToSpeech.speak(strategy);

                Thread.sleep(5000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
