package com.simon.gui.rental;
import com.simon.entity.Rental;
import com.simon.service.InventoryService;
import com.simon.service.MemberService;
import javafx.scene.Parent;
import com.simon.service.RentalService;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RentalMenu {

    public static Parent display(RentalService rentalService, MemberService memberService, InventoryService inventoryService )  {

        VBox rentalMenu = new VBox( 10 );

        HBox buttonsAndSearchField = new HBox( 10 );

        Button addRental = new Button( "Add Rental" );
        addRental.setOnAction( e -> {
            RentalPopUp.display( rentalService, memberService, inventoryService );
        } );

        buttonsAndSearchField.getChildren().addAll( addRental );

        rentalMenu.getChildren().addAll( buttonsAndSearchField );

        return rentalMenu;
    }
}
