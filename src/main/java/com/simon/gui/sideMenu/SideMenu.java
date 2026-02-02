package com.simon.gui.sideMenu;

import com.simon.gui.MemberMenu;
import com.simon.gui.util.CssUtil; // Se till att sökvägen till din CssUtil stämmer
import com.simon.repo.MemberRepo;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SideMenu {

    public static Parent get( BorderPane root, MemberRepo memberRepo ) {
        // Skapa behållaren för menyn
        VBox sideMenu = new VBox();
        sideMenu.setId("side-menu");

        // Centrerar knappen både vertikalt och horisontellt
        sideMenu.setAlignment(Pos.CENTER);
        sideMenu.setPrefWidth(250);
        sideMenu.setSpacing(20);

        // Skapa knappen
        Button memberButton    = new Button("Members" );
        Button inventoryButton = new Button("Iventory" );

        memberButton.setOnAction( e -> {
            root.setCenter(MemberMenu.display( memberRepo ) );
        } );

        // Koppla CSS-klassen från side-menu.css
        memberButton.getStyleClass().add("menu-button");
        inventoryButton.getStyleClass().add("menu-button");

        // Lägg till knappen i VBoxen
        sideMenu.getChildren().addAll( memberButton, inventoryButton );

        // Använd din CssUtil för att ladda filen
        // Den letar nu i src/main/resources/side-menu.css
        CssUtil.setCss(sideMenu, "/side-menu.css");

        return sideMenu;
    }
}