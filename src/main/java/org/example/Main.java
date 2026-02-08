package org.example;

import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main {
    private static String lastAdvice = "";
    private static String lastGameData = "";

    public static void main(String[] args) {
        Launcher.main(args);
    }

    public static void runGameLoop() throws InterruptedException {
        System.out.println("🚀 Radiant Engine Initialized...");
        Overlay.launchOverlay();
        Thread.sleep(2000);
        TextToSpeech.speak("Radiant Engine Online.");

        ScreenCapture capturer = new ScreenCapture();

        while (true) {
            BufferedImage image = capturer.capture();
            String gameData = ScreenScanner.getGameVariables(image);

            // 🎯 STATE CLASSIFIER: Injects round context
            String phase = gameData.contains("SPIKE_PLANTED: true") ? "COMBAT_POST_PLANT" : "SETUP_PHASE";

            File imageFile = new File("game_state.png");
            capturer.saveImage(image, "game_state.png");
            Thread.sleep(600);

            // 🧠 BRAIN CALL
            String strategy = GeminiBot.getStrategy(imageFile, gameData + " | PHASE: " + phase);
            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            System.out.println("[" + time + "] 🔍 RAW AI OUTPUT: " + strategy);

            // 🎯 AGGRESSIVE VIDEO FILTER
            if (strategy.toUpperCase().contains("COACH:")) {
                String adviceOnly = strategy.split("\\|")[0].trim().toLowerCase();

                // 🛑 AGGRESSIVE FUZZY MATCH: Checks first 20 characters
                boolean isRepeat = adviceOnly.length() > 20 && lastAdvice.length() > 20 &&
                        adviceOnly.substring(0, 20).equals(lastAdvice.substring(0, 20));

                // 🛑 KEYWORD BLOCK: Prevents repetitive talk about the same area
                boolean sameArea = (adviceOnly.contains("b main") && lastAdvice.contains("b main")) ||
                        (adviceOnly.contains("b short") && lastAdvice.contains("b short"));

                // 🛑 TRIPLE-LOCK: Logic triggers on New Advice OR Game State Change
                if ((!isRepeat && !sameArea) || !gameData.equals(lastGameData)) {

                    System.out.println("[" + time + "] 👑 IMMORTAL IGL: " + strategy);
                    Overlay.updateText("[" + time + "] " + strategy.toUpperCase());
                    TextToSpeech.speak(strategy);

                    lastAdvice = adviceOnly;
                    lastGameData = gameData;

                    // 🛑 EXTENDED STABILITY LOCK: 20 Seconds for Demo
                    Thread.sleep(20000);
                } else {
                    System.out.println("[" + time + "] 🛡️ REPEAT BLOCKED: " + adviceOnly);
                    Thread.sleep(4000);
                }
            } else if (strategy.equals("RATE_LIMIT")) {
                System.out.println("[" + time + "] ⚠️ API THROTTLED. WAITING 20s...");
                Thread.sleep(20000);
            } else {
                Thread.sleep(4000);
            }
        }
    }
}