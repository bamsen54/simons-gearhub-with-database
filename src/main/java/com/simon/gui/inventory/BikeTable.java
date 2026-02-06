package com.simon.gui.inventory;

import com.simon.entity.Bike;
import com.simon.entity.ItemStatus;
import com.simon.gui.util.CssUtil;
import com.simon.gui.util.SafeBigDecimalConverter;
import com.simon.gui.util.SafeIntegerConverter;
import com.simon.service.InventoryService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.util.List;

public class BikeTable {

    public static Parent getBikeView( InventoryService inventoryService ) {

        VBox bikeView = new VBox( 10 );

        CssUtil.setCss( bikeView, "inventory.css" );

        ObservableList<Bike> bikeObservable = FXCollections.observableArrayList();
        FilteredList<Bike> filteredBikes = new FilteredList<>( bikeObservable );
        SortedList<Bike> sortedBikes = new SortedList<>( filteredBikes );

        TableView<Bike> bikeTable = new TableView<>();
        bikeTable.setEditable( true );
        bikeTable.setFixedCellSize( 50 );
        bikeTable.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );

        Thread loadData = new Thread( () -> {

            List<Bike> bikes = inventoryService.findAll( Bike.class );

            Platform.runLater( () -> {
                bikeObservable.setAll( bikes );
                bikeTable.setPlaceholder( new Label( "No matches" )  );
            } );
        } );

        loadData.setDaemon( true );
        loadData.start();
        sortedBikes.comparatorProperty().bind( bikeTable.comparatorProperty() );

        TableColumn<Bike, Long> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>( "id" ) );

        TableColumn<Bike,String> name = new TableColumn<>( "Name" );
        name.setCellValueFactory( new PropertyValueFactory<>( "name" ) );

        TableColumn<Bike,String> bikeType = new TableColumn<>( "Bike Type" );
        bikeType.setCellValueFactory( new PropertyValueFactory<>( "bikeType" ) );

        TableColumn<Bike,Integer> gearCount = new TableColumn<>( "Gear Count" );
        gearCount.setCellValueFactory( cellData -> {
            return cellData.getValue().gearCountProperty().asObject();
        } );

        TableColumn<Bike, BigDecimal> price = new TableColumn<>( "Daily Price" );
        price.setCellValueFactory(cellData -> {
            return cellData.getValue().priceProperty();
        } );

        id.setPrefWidth( 60 );
        id.setMinWidth( 50 );
        name.setPrefWidth( 100 );
        bikeType.setPrefWidth( 100 );
        gearCount.setPrefWidth( 75 );
        price.setPrefWidth( 100 );

        name.setCellFactory( TextFieldTableCell.forTableColumn() );
        name.setOnEditCommit( event -> {
            if  (event.getNewValue() != null && !event.getNewValue().trim().isEmpty() ) {
                Bike bike = event.getRowValue();
                bike.setName( event.getNewValue().trim() );
                inventoryService.update( bike );
            }

            else {
                bikeTable.refresh();
            }
        } );

        bikeType.setCellFactory( TextFieldTableCell.forTableColumn() );
        bikeType.setOnEditCommit( event -> {
            if  (event.getNewValue() != null && !event.getNewValue().trim().isEmpty() ) {
                Bike bike = event.getRowValue();
                bike.setBikeType( event.getNewValue().trim() );
                inventoryService.update( bike );
            }

            else {
                bikeTable.refresh();
            }
        } );

        gearCount.setCellFactory(TextFieldTableCell.forTableColumn( new SafeIntegerConverter() ) );
        gearCount.setOnEditCommit( event -> {
            if  ( event.getNewValue() != null  ) {
                Bike bike = event.getRowValue();
                bike.setGearCount( event.getNewValue() );
                inventoryService.update( bike );
            }

            else {
                bikeTable.refresh();
            }
        } );

        price.setCellFactory( TextFieldTableCell.forTableColumn( new SafeBigDecimalConverter() ) );
        price.setOnEditCommit(event -> {

            try {

                BigDecimal newValue = event.getNewValue();

                if ( newValue != null && newValue.compareTo(BigDecimal.ZERO) >= 0 ) {
                    Bike bike = event.getRowValue();
                    bike.setPrice(newValue);
                    inventoryService.update(bike);
                } else {

                    bikeTable.refresh();
                }
            }

            catch (NumberFormatException e) {
                bikeTable.refresh();
            }
        });

        TableColumn<Bike, ItemStatus> status = new TableColumn<>("Status");
        status.setCellValueFactory( cellData -> cellData.getValue().statusProperty() );


        bikeTable.setItems( sortedBikes );

        bikeTable.getColumns().addAll( id, name, bikeType, gearCount, price, status );

        HBox buttonsAndSearchField = new HBox( 10 );

        Button addBikeButton = new Button( "Add Bike" );
        addBikeButton.setOnAction( e -> {
            Bike newBike = new Bike( "Bike Name", "Bike Type", 1, new BigDecimal( "0" ), ItemStatus.AVAILABLE );
            inventoryService.save( newBike  );
            bikeObservable.setAll( inventoryService.findAll( Bike.class ) );
            bikeTable.refresh();
        } );

        Button deleteBikeButton = new Button( "Delete Bike" );
        deleteBikeButton.setOnAction( e -> {
            Bike selectedBike = bikeTable.getSelectionModel().getSelectedItem();
            if( selectedBike != null ) {
                inventoryService.delete( selectedBike );
                bikeObservable.setAll( inventoryService.findAll( Bike.class ) );
                bikeTable.refresh();
            }
        } );

        buttonsAndSearchField.setPadding( new Insets( 10, 10, 3, 10 ) );
        buttonsAndSearchField.setAlignment( Pos.CENTER_LEFT );

        Label filterLabel = new Label( "  Filter" );
        filterLabel.setStyle( "-fx-font-size: 14px; -fx-text-fill: white;  " );

        TextField filterField = new TextField();

        filterField.setStyle( "-fx-background-color: #2d2d2d; -fx-text-fill: white; -fx-border-color: #444444" );

        filterField.textProperty().addListener( (observable, oldValue, newValue) -> {

            filteredBikes.setPredicate( bike -> {
                if( newValue == null || newValue.trim().isEmpty() )
                    return true;

                String filter =  newValue.toLowerCase();

                return bike.getId().toString().contains( filter )
                        || bike.getName().toLowerCase().contains( filter )
                        || bike.getBikeType().toLowerCase().contains( filter )
                        || String.valueOf( bike.getGearCount() ).contains( filter )
                        || String.valueOf( bike.getPrice() ).contains( filter )
                        || String.valueOf( bike.getStatus() ).toLowerCase().contains( filter );

            } );
        } );

        buttonsAndSearchField.getChildren().addAll( addBikeButton, deleteBikeButton, filterLabel, filterField );

        addBikeButton.getStyleClass().add( "crud-button" );
        deleteBikeButton.getStyleClass().add( "crud-button" );

        bikeView.getChildren().addAll( buttonsAndSearchField, bikeTable );


        VBox.setVgrow(bikeTable, Priority.ALWAYS );
        bikeTable.setMaxHeight( Double.MAX_VALUE );
        bikeTable.setPrefHeight(Integer.MAX_VALUE); // Tvingar tabellen att begära all plats
        bikeView.setPrefHeight(Integer.MAX_VALUE);  // Tvingar VBoxen att begära all plats

        return bikeView;
    }
}