package com.simon.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class ShowDarkAlert {
    public static void showDarkAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(null);

        DialogPane dp = alert.getDialogPane();

        Label label = new Label(message);
        label.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 20;");
        label.setWrapText(true);

        dp.setContent(label);
        dp.setStyle("-fx-background-color: #1e1e1e; -fx-border-color: #444444;");

        alert.setOnShown(e -> {
            Stage stage = (Stage) dp.getScene().getWindow();
            dp.getScene().setFill(Color.web("#1e1e1e"));

            dp.lookupAll(".button").forEach(btn ->
                    btn.setStyle("-fx-background-color: #3c3f41; -fx-text-fill: white; -fx-background-radius: 2;")
            );

            dp.lookupAll(".button-bar").forEach(bb -> bb.setStyle("-fx-background-color: transparent;"));
            dp.lookupAll(".header-panel").forEach(hp -> hp.setStyle("-fx-background-color: transparent;"));

            dp.lookupAll(".graphic-container").forEach(gc -> gc.setStyle("-fx-background-color: transparent;"));

            dp.lookupAll(".dialog-pane .graphic").forEach(g -> g.setStyle("-fx-background-color: transparent;"));
        });

        alert.showAndWait();
    }
}