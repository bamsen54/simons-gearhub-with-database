package com.simon.gui.inventory;

import com.simon.gui.util.CssUtil;
import com.simon.service.InventoryService;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class InventoryMenu {

    public static Parent display(InventoryService inventoryService) {

        VBox inventoryMenu = new VBox();
        VBox.setVgrow(inventoryMenu, Priority.ALWAYS);

        CssUtil.setCss( inventoryMenu, "inventory.css" );

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        VBox.setVgrow(tabPane, Priority.ALWAYS);

        Tab bikeTab = new Tab("Bikes");
        bikeTab.setContent(BikeTable.getBikeView(inventoryService));

        Tab kayakTab = new Tab("Kayaks");
        kayakTab.setContent(KayakTable.getKayakView(inventoryService));

        Tab tentTab = new Tab("Tents");
        tentTab.setContent(TentTable.getTentView(inventoryService));

        tabPane.getTabs().addAll(bikeTab, kayakTab, tentTab);

        inventoryMenu.getChildren().add(tabPane);

        return inventoryMenu;
    }
}