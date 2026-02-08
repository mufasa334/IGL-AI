package org.example;

import java.io.*;
import java.net.*;
import java.util.Base64;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import org.json.JSONObject;
import org.json.JSONArray;

public class GeminiBot {

    private static final String API_KEY = System.getenv("GEMINI_API_KEY");

    public static String getStrategy(File imageFile, String agent, String phase) {
        if (API_KEY == null) return "COACH: ERROR | API KEY MISSING";

        try {
            // ✅ FIX 1: Use Gemini 3 for Hackathon points (Dec 2025 release)
            String fullUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3-flash-preview:generateContent?key=" + API_KEY;

            String prompt = String.format("""
            You are a professional Valorant IGL coach.

GAME STATE (DO NOT GUESS):
AGENT = %s
PHASE = %s

RULES:
1. NEVER guess agent or phase.
2. If PHASE = POST_PLANT -> NEVER mention planting the spike.
3. Do NOT mention team numbers unless provided.
4. Do NOT mention ultimate unless HUD confirms it.
5. If unsure, OMIT the detail.

COMMAND TYPES:
- Hold angle
- Swing with teammate
- Use wall/slow/smoke
- Play time
- Peek for info

FORMAT:
COACH: <AGENT> | <COMMAND> | <SHORT REASON> | CONFIDENCE: HIGH/MEDIUM/LOW

Do NOT invent game state.
Keep output under 25 words.
""", agent, phase);

            byte[] imgBytes = resizeAndCompress(imageFile);
            String base64 = Base64.getEncoder().encodeToString(imgBytes);

            // Build JSON using org.json (Cleaner & Safer)
            JSONObject root = new JSONObject();
            JSONArray contents = new JSONArray();
            JSONObject content = new JSONObject();
            JSONArray parts = new JSONArray();

            parts.put(new JSONObject().put("text", prompt));
            parts.put(new JSONObject().put("inline_data",
                    new JSONObject()
                            .put("mime_type", "image/jpeg")
                            .put("data", base64)));

            content.put("parts", parts);
            contents.put(content);
            root.put("contents", contents);

            URL url = new URI(fullUrl).toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()) {
                os.write(root.toString().getBytes("utf-8"));
            }

            int code = con.getResponseCode();
            InputStream stream = (code >= 400) ? con.getErrorStream() : con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) response.append(line);

            if (code >= 400) {
                System.out.println("❌ GEMINI ERROR BODY:");
                System.out.println(response);
                return "COACH: ERROR | Gemini API " + code;
            }

            JSONObject json = new JSONObject(response.toString());
            return json.getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text")
                    .replace("\n", " ")
                    .trim();

        } catch (Exception e) {
            e.printStackTrace();
            return "COACH: ERROR | " + e.getMessage();
        }
    }

    // Resize screenshot to avoid Gemini payload limit
    private static byte[] resizeAndCompress(File file) throws Exception {
        BufferedImage original = ImageIO.read(file);

        // ✅ FIX 2: 360p is too blurry for UI text. 720p is safer for reading HUD.
        int w = 1280, h = 720;

        BufferedImage resized = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resized.createGraphics();
        g.drawImage(original, 0, 0, w, h, null);
        g.dispose();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(resized, "jpg", os);
        return os.toByteArray();
    }
}