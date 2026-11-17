# IGL-AI // Radiant Engine 🎯

> **The First Real-Time Multimodal AI Coach for Valorant** > *Built for the Google Gemini 3 Global Hackathon*

![Version](https://img.shields.io/badge/version-1.0-blue) ![Java](https://img.shields.io/badge/Java-21-orange) ![AI](https://img.shields.io/badge/Powered%20By-Gemini%203%20Flash-purple)

## 📖 Overview

**IGL-AI** (In-Game Leader AI) is a real-time tactical assistant that "watches" your Valorant gameplay and provides live, radiant-level coaching strategies. 

Unlike static overlays, IGL-AI uses **Google Gemini 3's multimodal vision capabilities** to analyze the game state (Agent, Spike Status, Phase, and Position) and generate concise, tactical audio commands instantly. It acts as a grounded "Backseat Gamer" that actually helps you win.

## 🎥 Live Demo

[![IGL-AI: Real-Time Gemini 3 Tactical Coach (Hackathon Demo)](https://img.youtube.com/vi/YOUR_VIDEO_ID/hqdefault.jpg)](https://www.youtube.com/watch?v=58_eMDFLQtk)

*Click the thumbnail above to watch the full demo with live audio coaching on YouTube.*

## ⚡ Key Features

- **🧠 Multimodal Perception**: Captures your screen in real-time and uses Gemini 3 Flash to "see" the battlefield.
- **🗣️ Live Audio Coaching**: Converts tactical text strategies into speech using system TTS (PowerShell integration).
- **🛡️ Hallucination Filters**: Custom Java logic gates prevent the AI from inventing fake game states (e.g., stopping "Defuse" calls when you are Attacking).
- **👁️ Computer Vision Sensors**:
  - **Hybrid Agent Detection**: Includes a "Neural Link" Launcher to manually select your agent (Jett, Reyna, Omen, etc.) or uses experimental Computer Vision to auto-detect agents like Sage/Neon.
  - **Phase Detection**: automatically detects if the Spike is planted or if the round is in the combat phase.
- **💻 Cyberpunk Launcher**: A custom JavaFX control panel for immersive startup and configuration.
- **🖥️ Transparent Overlay**: Displays the AI's "Thought Process" and confidence levels directly on top of your game.

## 🛠️ Tech Stack

- **Language**: Java 21
- **Framework**: JavaFX (UI & Overlay)
- **AI Model**: Google Gemini 3 Flash Preview (via REST API)
- **JSON Parsing**: `org.json`
- **System Integration**: `java.awt.Robot` (Screen Capture) & PowerShell (TTS)

## 🚀 Installation & Setup

### Prerequisites
1.  **Java Development Kit (JDK) 21** installed.
2.  **Maven** (for dependency management).
3.  A **Google AI Studio API Key**.

### Step 1: Clone the Repository
```bash
git clone [https://github.com/mufasa334/IGL-AI.git](https://github.com/mufasa334/IGL-AI.git)
cd IGL-AI
```
Step 2: Set your API Key
Security Warning: NEVER share your API key or commit it to GitHub. Set it as an Environment Variable on your machine:

Windows (PowerShell):

PowerShell
$env:GEMINI_API_KEY="AIzaSy...YourKeyHere"
Mac/Linux:

Bash
export GEMINI_API_KEY="AIzaSy...YourKeyHere"
Step 3: Dependencies
Ensure your pom.xml includes the org.json and javafx dependencies. If you are using IntelliJ, simply reload the Maven project.

🎮 How to Run
Open the project in IntelliJ IDEA.

Locate src/main/java/org/example/Launcher.java.

Right-click Launcher.java and select Run 'Launcher.main()'.

The Neural Link Launcher will appear:

Select your Agent (e.g., SAGE) or leave on AUTO.

Click INITIALIZE SYSTEM.

Alt-Tab into Valorant.

The AI will begin coaching you automatically!

⚠️ Troubleshooting
"API Key Missing": Make sure you set the environment variable correctly in your Run Configuration or System Settings.

Overlay not showing: Ensure you run Valorant in Windowed Fullscreen mode so the JavaFX overlay can sit on top.

Auto-Detect failing: The "AUTO" agent feature relies on specific HUD coordinates (1080p). If it fails, use the Dropdown in the Launcher to manually select your agent.

Audio issues: The system uses PowerShell for TTS. Ensure your Windows volume is up.

⚖️ Legal & Disclaimer
Riot Games Policy:

IGL-AI is not endorsed by Riot Games and does not reflect the views or opinions of Riot Games or anyone officially involved in producing or managing Riot Games properties. Riot Games and all associated properties are trademarks or registered trademarks of Riot Games, Inc.

Fair Play: This tool is a Proof of Concept for the Gemini 3 Hackathon. It reads the screen visually (like a human) and does not hook into game memory or read network packets. However, use in competitive ranked matches is at your own risk.

🏆 Hackathon Context
This project explores the Multimodal Grounding capabilities of Gemini 3. By combining specific pixel-color sensors (Java) with general visual reasoning (Gemini), we solved the common "LLM Hallucination" problem in fast-paced gaming environments.

Created for the Gemini 3 Global Challenge.
