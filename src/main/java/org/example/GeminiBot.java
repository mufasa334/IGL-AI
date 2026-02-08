package org.example;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class GeminiBot {

    // ⚠️ ENSURE YOUR API KEY IS ACTIVE
    private static final String API_KEY = "AIzaSyBsEZH1rPecRg2LmBSZjZVKDd0yolaTGDA";
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-lite:generateContent?key=" + API_KEY;

    public static String getStrategy(File imageFile, String gameData) {
        try {
            if (!imageFile.exists()) return "Error: File missing";

            // 🎯 NEW: Extract 'phase' from the incoming gameData string
            // This pulls 'COMBAT_POST_PLANT' or 'SETUP_PHASE' so the prompt works.
            String phase = gameData.contains("PHASE: ") ? gameData.split("PHASE: ")[1] : "UNKNOWN";

            byte[] fileContent = compressImage(imageFile);
            String encodedImage = Base64.getEncoder().encodeToString(fileContent);

            // 🧠 IMMORTAL COACH V6: PRIORITY BRANCHING LOGIC
            String rawPrompt = "You are an Immortal Valorant Coach.\n" +
                    "CURRENT PHASE: " + phase + "\n" +
                    "STRICT OPERATING RULES:\n" +
                    "1. IF PHASE IS 'COMBAT_POST_PLANT': DO NOT suggest placing utility. Focus ONLY on crosshair placement, off-angles, and playing for time.\n" +
                    "2. IF PHASE IS 'SETUP_PHASE': Focus on pixel-perfect Tripwires and Cages.\n" +
                    "3. PROACTIVE WARNING: Always warn the player about the most likely 'Contact Zone' (e.g., Garden or Window).\n" +
                    "\n" +
                    "STRICT FORMAT: 'COACH: [ACTION] | [PHASE-SPECIFIC REASONING]'.";

            String jsonInputString = "{"
                    + "\"contents\": [{"
                    + "\"role\": \"user\","
                    + "\"parts\": ["
                    + "{\"text\": \"" + rawPrompt.replace("\n", "\\n") + "\"},"
                    + "{\"inline_data\": {\"mime_type\":\"image/jpeg\",\"data\": \"" + encodedImage + "\"}}"
                    + "]"
                    + "}],"
                    + "\"generationConfig\": {"
                    + "  \"temperature\": 0.1,"
                    + "  \"topP\": 0.1,"
                    + "  \"maxOutputTokens\": 120"
                    + "}"
                    + "}";

            URL url = new URL(API_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int status = con.getResponseCode();
            if (status == 429) return "RATE_LIMIT";
            if (status != 200) return "Error " + status;

            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) response.append(line.trim());

                String raw = response.toString();
                if (raw.contains("\"text\": \"")) {
                    int start = raw.indexOf("\"text\": \"") + 9;
                    int end = raw.indexOf("\"", start);
                    return raw.substring(start, end).replace("\\n", " ").replace("*", "").trim();
                }
                return "STANDBY: Analyzing State...";
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private static byte[] compressImage(File file) throws Exception {
        BufferedImage image = ImageIO.read(file);
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        javax.imageio.ImageWriter writer = javax.imageio.ImageIO.getImageWritersByFormatName("jpg").next();
        javax.imageio.ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(javax.imageio.ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.9f); // High-Def Scan

        writer.setOutput(javax.imageio.ImageIO.createImageOutputStream(os));
        writer.write(null, new javax.imageio.IIOImage(image, null, null), param);

        writer.dispose();
        return os.toByteArray();
    }
}