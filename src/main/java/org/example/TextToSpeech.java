package org.example;

import java.io.IOException;

public class TextToSpeech {

    public static void speak(String text) {
        try {
            if (text == null || text.isEmpty()) return;

            // Clean up text to prevent PowerShell errors
            String cleanText = text.replace("\"", "").replace("'", "");

            // Use Windows built-in Text-to-Speech via PowerShell
            String command = "PowerShell -Command \"Add-Type –AssemblyName System.Speech; " +
                    "(New-Object System.Speech.Synthesis.SpeechSynthesizer).Speak('" + cleanText + "');\"";

            Runtime.getRuntime().exec(command);

        } catch (IOException e) {
            System.err.println("❌ Audio Error: " + e.getMessage());
        }
    }
}