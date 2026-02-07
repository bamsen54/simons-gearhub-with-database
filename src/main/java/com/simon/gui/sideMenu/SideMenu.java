package com.simon.gui.sideMenu;

import com.simon.gui.MemberMenu;
import com.simon.gui.rental.RentalMenu;
import com.simon.gui.inventory.InventoryMenu;
import com.simon.gui.util.CssUtil; // Se till att sökvägen till din CssUtil stämmer
import com.simon.repo.MemberRepo;
import com.simon.repo.RentalRepo;
import com.simon.service.InventoryService;
import com.simon.service.MemberService;
import com.simon.service.RentalService;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SideMenu {

    public static Parent get(BorderPane root, MemberRepo memberRepo, RentalRepo rentalRepo, MemberService memberService, InventoryService inventoryService, RentalService rentalService ) {

        VBox sideMenu = new VBox();
        sideMenu.setId( "side-menu" );

        // Centrerar knappen både vertikalt och horisontellt
        sideMenu.setAlignment(Pos.CENTER);
        sideMenu.setPrefWidth(250);
        sideMenu.setSpacing(5);

        // Skapa knappen
        Button memberButton    = new Button("Members" );
        Button inventoryButton = new Button("Inventory" );
        Button rentalButton = new Button( "Rental" );

        memberButton.setOnAction( e -> {
            root.setCenter( MemberMenu.display( memberService ) );
        } );


        inventoryButton.setOnAction( e -> {
            root.setCenter( InventoryMenu.display( inventoryService ) );
        } );

        rentalButton.setOnAction( e -> {
            root.setCenter( RentalMenu.display( rentalService, memberService, inventoryService ) );
        } );

        sideMenu.getChildren().addAll( memberButton, inventoryButton, rentalButton );

        memberButton.getStyleClass().add( "menu-button" );
        inventoryButton.getStyleClass().add( "menu-button" );
        rentalButton.getStyleClass().add( "menu-button" );

        CssUtil.setCss(sideMenu, "/side-menu.css");

        return sideMenu;
    }
}