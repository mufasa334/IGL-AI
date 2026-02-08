package org.example;

import java.io.IOException;

public class TextToSpeech {
    private static boolean speaking = false;

    public static synchronized void speak(String text) {
        if (speaking || text == null || text.isEmpty()) return;
        speaking = true;

        new Thread(() -> {
            try {
                String clean = text.replace("\"", "").replace("'", "");
                String cmd = "PowerShell -Command \"Add-Type -AssemblyName System.Speech; " +
                        "(New-Object System.Speech.Synthesis.SpeechSynthesizer).Speak('" + clean + "');\"";
                Runtime.getRuntime().exec(cmd).waitFor();
            } catch (Exception ignored) {}
            speaking = false;
        }).start();
    }
}
