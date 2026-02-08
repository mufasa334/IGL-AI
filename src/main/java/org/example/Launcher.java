package org.example;

import javafx.scene.control.ComboBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Launcher extends Application {

    private ProgressBar progressBar;
    private TextArea consoleLog;
    private Stage mainStage;
    private ComboBox<String> agentSelector; // ✅ Agent dropdown

    @Override
    public void start(Stage primaryStage) {
        this.mainStage = primaryStage;

        Label title = new Label("IGL-AI // NEURAL LINK");
        title.setTextFill(Color.web("#ff4655"));
        title.setFont(Font.font("Impact", 36));
        title.setEffect(new DropShadow(20, Color.RED));

        VBox header = new VBox(5, title);
        header.setAlignment(Pos.CENTER);

        VBox settingsBox = new VBox(15);
        settingsBox.setPadding(new Insets(20));
        settingsBox.setStyle("-fx-border-color: #444; -fx-background-color: rgba(28, 37, 46, 0.8);");

        // ✅ AGENT SELECTOR UI
        agentSelector = new ComboBox<>();
        agentSelector.getItems().addAll("AUTO", "SAGE", "JETT", "REYNA", "OMEN");
        agentSelector.setValue("AUTO");

        settingsBox.getChildren().addAll(new Label("Agent Override:"), agentSelector);

        consoleLog = new TextArea("> SYSTEM IDLE...\n");
        consoleLog.setEditable(false);
        consoleLog.setPrefHeight(150);
        consoleLog.setStyle("-fx-control-inner-background: black; -fx-text-fill: #00ff00; -fx-font-family: 'Consolas';");

        HBox mainContent = new HBox(20, settingsBox, consoleLog);
        mainContent.setAlignment(Pos.CENTER);

        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(600);
        progressBar.setVisible(false);

        Button launchBtn = new Button("INITIALIZE SYSTEM");
        launchBtn.setStyle("-fx-background-color: #ff4655; -fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");

        launchBtn.setOnAction(e -> startInjectionSequence(launchBtn));

        VBox root = new VBox(30, header, mainContent, progressBar, launchBtn);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #0f1923; -fx-border-color: #ff4655; -fx-border-width: 2;");

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 750, 600));
        primaryStage.show();
    }

    private void startInjectionSequence(Button btn) {
        btn.setDisable(true);
        progressBar.setVisible(true);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> consoleLog.appendText("> Initializing Kernel...\n")),
                new KeyFrame(Duration.seconds(1.0), e -> {
                    progressBar.setProgress(0.5);
                    consoleLog.appendText("> Connecting to Neural Cloud...\n");
                }),
                new KeyFrame(Duration.seconds(2.0), e -> {
                    progressBar.setProgress(1.0);
                    consoleLog.appendText("> Neural Link Established.\n");

                    mainStage.close();
                    startMainSystem();
                })
        );
        timeline.play();
    }

    private void startMainSystem() {
        new Thread(() -> {
            try {
                // ✅ Pass agent override to Main
                System.setProperty("agent", agentSelector.getValue());
                Main.runGameLoop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
