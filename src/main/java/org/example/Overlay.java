package org.example;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Overlay {
    private static Label statusLabel;
    private static Stage overlayStage;

    public static void launchOverlay() {
        Platform.runLater(() -> {
            overlayStage = new Stage();
            statusLabel = new Label("IGL-AI: INITIALIZING...");
            statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            statusLabel.setTextFill(Color.RED);

            VBox root = new VBox(statusLabel);
            root.setAlignment(Pos.TOP_CENTER);
            root.setStyle("-fx-background-color: transparent;");

            Scene scene = new Scene(root, 1000, 100);
            scene.setFill(null); // Transparent background

            overlayStage.initStyle(StageStyle.TRANSPARENT);
            overlayStage.setAlwaysOnTop(true);
            overlayStage.setX(460); // Position at top-center
            overlayStage.setY(50);
            overlayStage.setScene(scene);
            overlayStage.show();
        });
    }

    public static void updateText(String text) {
        Platform.runLater(() -> {
            if (statusLabel != null) {
                statusLabel.setText(text);
            }
        });
    }
}