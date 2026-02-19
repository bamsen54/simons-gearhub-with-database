package com.simon.gui;

import com.simon.service.IncomeService;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class IncomeMenu {

    public static Parent display(IncomeService incomeService) {


        BigDecimal total = incomeService.getTotalIncome();
        Label label = new Label("Total Income: " + total.toString() );
        label.setStyle("-fx-font-size: 20px; -fx-padding: 20;");

        label.setStyle( "-fx-font-size: 20px; -fx-text-fill: white;" );

        StackPane root = new StackPane(label);
        Scene scene = new Scene(root, 300, 150);

       return root;
    }
}
