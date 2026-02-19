package com.simon.gui.sideMenu;

import com.simon.gui.MemberMenu;
import com.simon.gui.rental.RentalMenu;
import com.simon.gui.inventory.InventoryMenu;
import com.simon.gui.util.CssUtil;
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

import java.util.Arrays;
import java.util.List;

public class SideMenu {

    public static Parent get(BorderPane root, MemberRepo memberRepo, RentalRepo rentalRepo, MemberService memberService, InventoryService inventoryService, RentalService rentalService ) {

        VBox sideMenu = new VBox();
        sideMenu.setId( "side-menu" );
        sideMenu.setAlignment(Pos.CENTER);
        sideMenu.setPrefWidth(250);
        sideMenu.setSpacing(5);

        Button memberButton    = new Button("Members" );
        Button inventoryButton = new Button("Inventory" );
        Button rentalButton    = new Button( "Rental" );

        List<Button> menuButtons = Arrays.asList(memberButton, inventoryButton, rentalButton);

        memberButton.setOnAction( e -> {
            setActive(memberButton, menuButtons);
            root.setCenter( MemberMenu.display( memberService ) );
        } );

        inventoryButton.setOnAction( e -> {
            setActive(inventoryButton, menuButtons);
            root.setCenter( InventoryMenu.display( inventoryService ) );
        } );

        rentalButton.setOnAction( e -> {
            setActive(rentalButton, menuButtons);
            root.setCenter( RentalMenu.display( rentalService, memberService, inventoryService ) );
        } );

        sideMenu.getChildren().addAll( memberButton, inventoryButton, rentalButton );

        memberButton.getStyleClass().add( "menu-button" );
        inventoryButton.getStyleClass().add( "menu-button" );
        rentalButton.getStyleClass().add( "menu-button" );

        setActive(memberButton, menuButtons);

        CssUtil.setCss(sideMenu, "/side-menu.css");

        return sideMenu;
    }

    private static void setActive(Button clickedButton, List<Button> allButtons) {
        for (Button btn : allButtons) {
            btn.getStyleClass().remove("active");
        }
        clickedButton.getStyleClass().add("active");
    }
}