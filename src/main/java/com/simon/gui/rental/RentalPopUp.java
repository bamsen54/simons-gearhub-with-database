package com.simon.gui.rental;

import com.simon.entity.*;
import com.simon.service.InventoryService;
import com.simon.service.MemberService;
import com.simon.service.RentalService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RentalPopUp {

    public static void display(RentalService rentalService, MemberService memberService, InventoryService inventoryService) {

        ObservableList<Member> memberObservable = FXCollections.observableArrayList();
        memberObservable.setAll(memberService.findAll());

        Stage stage = new Stage();

        GridPane root = new GridPane();
        root.setVgap(10);
        root.setHgap(10);

        Label memberLabel = new Label("Member");
        root.add(memberLabel, 0, 0);

        ComboBox<Member> memberComboBox = new ComboBox<>();
        memberComboBox.setItems(memberObservable);
        memberComboBox.setPrefWidth( 350 );

        if( !memberObservable.isEmpty() )
            memberComboBox.getSelectionModel().select( 0 );

        root.add( memberComboBox, 1, 0 );

        Label rentalTypeLabel = new Label("Rental Type");
        root.add(rentalTypeLabel, 0, 1);

        ObservableList<RentalType> rentalTypesObservable = FXCollections.observableArrayList();
        rentalTypesObservable.addAll(RentalType.values());
        ComboBox<RentalType> rentalTypeComboBox = new ComboBox<>();
        rentalTypeComboBox.setItems(rentalTypesObservable);
        rentalTypeComboBox.setPrefWidth( 350 );
        rentalTypeComboBox.setValue( RentalType.BIKE );
        root.add( rentalTypeComboBox, 1, 1 );


        Label rentalItemLabel = new Label( "Rental Item" );
        root.add( rentalItemLabel, 0, 2 );


        ObservableList<Bike> bikeObservable = FXCollections.observableArrayList();
        bikeObservable.addAll(
            inventoryService.findAll( Bike.class ).stream().filter( b -> b.getStatus().equals( ItemStatus.AVAILABLE ) ).toList()
        );

        ComboBox<Bike> bikeComboBox = new ComboBox<>();
        bikeComboBox.setItems( bikeObservable );
        bikeComboBox.setPrefWidth( 350 );

        if( !bikeObservable.isEmpty() )
            bikeComboBox.getSelectionModel().select(0);

        root.add(bikeComboBox, 1, 2);

        ObservableList<Kayak> kayakObservable = FXCollections.observableArrayList();
        kayakObservable.addAll(
            inventoryService.findAll(Kayak.class).stream().filter( k -> k.getStatus().equals( ItemStatus.AVAILABLE) ).toList()
        );

        ComboBox<Kayak> kayakComboBox = new ComboBox<>();
        kayakComboBox.setItems(kayakObservable);
        kayakComboBox.setPrefWidth( 350 );

        kayakComboBox.getSelectionModel().select(0);
        root.add(kayakComboBox, 1, 2);

        ObservableList<Tent> tentObservable = FXCollections.observableArrayList();
        tentObservable.addAll(
            inventoryService.findAll(Tent.class).stream().filter(t -> t.getStatus().equals(ItemStatus.AVAILABLE)).toList()
        );

        ComboBox<Tent> tentComboBox = new ComboBox<>();
        tentComboBox.setItems(tentObservable);
        tentComboBox.getSelectionModel().select(0);
        root.add(tentComboBox, 1, 2);

        bikeComboBox.setVisible( true );
        kayakComboBox.setVisible( false );
        tentComboBox.setVisible( false );

        rentalTypeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            bikeComboBox.setVisible(false);
            bikeComboBox.setManaged(false);

            kayakComboBox.setVisible(false);
            kayakComboBox.setManaged(false);

            tentComboBox.setVisible(false);
            tentComboBox.setManaged(false);


            if (  RentalType.BIKE.equals( newVal ) ) {
                bikeComboBox.setVisible(true);
                bikeComboBox.setManaged(true);
            }

            else if ( RentalType.KAYAK.equals( newVal ) ) {
                kayakComboBox.setVisible(true);
                kayakComboBox.setManaged(true);
            }

            else if ( RentalType.TENT.equals( newVal ) ) {
                tentComboBox.setVisible(true);
                tentComboBox.setManaged(true);
            }
        });

        Button rentButton =  new Button( "Rent" );
        rentButton.setOnAction( e -> {

            Long memberId = memberComboBox.getSelectionModel().getSelectedItem().getId();

            Rental rental = new Rental(   );

            rental.setMember( memberService.findById( memberId ).get() );
            rental.setRentalType( rentalTypeComboBox.valueProperty().get() );

            rental.setRentalDate( LocalDateTime.now() );

            Long itemId = switch ( rentalTypeComboBox.valueProperty().get() ) {
                case BIKE  -> bikeComboBox.getSelectionModel().getSelectedItem().getId();
                case KAYAK -> kayakComboBox.getSelectionModel().getSelectedItem().getId();
                case TENT  -> tentComboBox.getSelectionModel().getSelectedItem().getId();
            };

            rental.setRentalObjectId( itemId );

            rentalService.processNewRental( rental );

            stage.close();

        } );


        rentButton.disableProperty().bind(
            memberComboBox.valueProperty().isNull()
                .or( rentalTypeComboBox.valueProperty().isNull() )
                .or(
                    rentalTypeComboBox.valueProperty().isEqualTo( RentalType.BIKE).and(bikeComboBox.valueProperty().isNull() )
                    .or( rentalTypeComboBox.valueProperty().isEqualTo(RentalType.KAYAK).and(kayakComboBox.valueProperty().isNull() ) )
                    .or( rentalTypeComboBox.valueProperty().isEqualTo( RentalType.TENT ).and( tentComboBox.valueProperty().isNull() ) )
                )
        );

        Button cancelButton =  new Button( "Cancel" );
        cancelButton.setOnAction( e -> {
            stage.close();
        } );

        root.add( rentButton, 0, 3 );
        root.add( cancelButton, 1, 3 );



        Scene scene = new Scene(root, 720, 480);

        stage.setScene(scene);
        stage.setTitle("Rental");
        stage.setResizable(false);

        scene.getStylesheets().add(RentalPopUp.class.getResource("/rental-pop-up.css").toExternalForm());

        memberLabel.getStyleClass().add("form-label");
        rentalTypeLabel.getStyleClass().add("form-label");
        rentalItemLabel.getStyleClass().add("form-label");

        rentButton.getStyleClass().add("crud-button");
        cancelButton.getStyleClass().add("crud-button");

        root.setPadding(new javafx.geometry.Insets(25));

        stage.initModality( Modality.APPLICATION_MODAL );

        stage.showAndWait();
    }
}

