package com.simon.gui.inventory;

import com.simon.service.InventoryService;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class InventoryMenu {

    public static Parent display(InventoryService inventoryService) {

        VBox inventoryMenu = new VBox();
        inventoryMenu.setAlignment(Pos.TOP_CENTER );

        inventoryMenu.getChildren().addAll( new Label("Inventory Menu") );

        return inventoryMenu;

    }
}
