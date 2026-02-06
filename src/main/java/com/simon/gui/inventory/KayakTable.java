package com.simon.gui.inventory;

import com.simon.entity.ItemStatus;
import com.simon.entity.Kayak;
import com.simon.gui.util.CssUtil;
import com.simon.gui.util.SafeBigDecimalConverter;
import com.simon.gui.util.SafeIntegerConverter;
import com.simon.service.InventoryService;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.util.List;

public class KayakTable {

    public static Parent getKayakView( InventoryService inventoryService ) {

        VBox kayakView = new VBox( 10 );

        CssUtil.setCss( kayakView, "inventory.css" );

        ObservableList<Kayak> kayakObservable = FXCollections.observableArrayList();
        FilteredList<Kayak> filteredKayaks = new FilteredList<>( kayakObservable );
        SortedList<Kayak> sortedKayaks = new SortedList<>( filteredKayaks );

        TableView<Kayak> kayakTable = new TableView<>();
        kayakTable.setEditable( true );
        kayakTable.setFixedCellSize( 50 );
        kayakTable.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );

        Thread loadData = new Thread( () -> {

            List<Kayak> kayaks = inventoryService.findAll( Kayak.class );

            Platform.runLater( () -> {
                kayakObservable.setAll( kayaks );
                kayakTable.setPlaceholder( new Label( "No matches" )  );
            } );
        } );

        loadData.setDaemon( true );
        loadData.start();
        sortedKayaks.comparatorProperty().bind( kayakTable.comparatorProperty() );

        TableColumn<Kayak, Long> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>( "id" ) );

        TableColumn<Kayak,String> name = new TableColumn<>( "Name" );
        name.setCellValueFactory( new PropertyValueFactory<>( "name" ) );

        TableColumn<Kayak,Integer> numberOfSeats = new TableColumn<>( "Seats" );
        numberOfSeats.setCellValueFactory( new PropertyValueFactory<>( "numberOfSeats" ) );

        TableColumn<Kayak, Boolean> hasRudder = new TableColumn<>( "Rudder" );
        hasRudder.setCellValueFactory( cellData -> new SimpleBooleanProperty( cellData.getValue().isHasRudder() ) );

        TableColumn<Kayak, BigDecimal> price = new TableColumn<>( "Daily Price" );
        price.setCellValueFactory( new PropertyValueFactory<>( "price" ) );

        id.setPrefWidth( 60 );
        id.setMinWidth( 50 );
        name.setPrefWidth( 100 );
        numberOfSeats.setPrefWidth( 75 );
        hasRudder.setPrefWidth( 75 );
        price.setPrefWidth( 100 );

        name.setCellFactory( TextFieldTableCell.forTableColumn() );
        name.setOnEditCommit( event -> {
            if  (event.getNewValue() != null && !event.getNewValue().trim().isEmpty() ) {
                Kayak kayak = event.getRowValue();
                kayak.setName( event.getNewValue().trim() );
                inventoryService.update( kayak );
            }
            else {
                kayakTable.refresh();
            }
        } );

        numberOfSeats.setCellFactory( TextFieldTableCell.forTableColumn( new SafeIntegerConverter() ) );
        numberOfSeats.setOnEditCommit( event -> {
            if  ( event.getNewValue() != null  ) {
                Kayak kayak = event.getRowValue();
                kayak.setNumberOfSeats( event.getNewValue() );
                inventoryService.update( kayak );
            }
            else {
                kayakTable.refresh();
            }
        } );

        hasRudder.setCellFactory( CheckBoxTableCell.forTableColumn( hasRudder ) );

        price.setCellFactory( TextFieldTableCell.forTableColumn( new SafeBigDecimalConverter() ) );
        price.setOnEditCommit(event -> {
            try {
                BigDecimal newValue = event.getNewValue();
                if ( newValue != null && newValue.compareTo(BigDecimal.ZERO) >= 0 ) {
                    Kayak kayak = event.getRowValue();
                    kayak.setPrice(newValue);
                    inventoryService.update(kayak);
                } else {
                    kayakTable.refresh();
                }
            }
            catch (NumberFormatException e) {
                kayakTable.refresh();
            }
        });

        TableColumn<Kayak, ItemStatus> status = new TableColumn<>("Status");
        status.setCellValueFactory( new PropertyValueFactory<>( "status" ) );

        kayakTable.setItems( sortedKayaks );

        kayakTable.getColumns().addAll( id, name, numberOfSeats, hasRudder, price, status );

        HBox buttonsAndSearchField = new HBox( 10 );

        Button addKayakButton = new Button( "Add Kayak" );
        addKayakButton.setOnAction( e -> {
            Kayak newKayak = new Kayak( "Kayak Name", 1, false, new BigDecimal( "0" ), ItemStatus.AVAILABLE );
            inventoryService.save( newKayak  );
            kayakObservable.setAll( inventoryService.findAll( Kayak.class ) );
            kayakTable.refresh();
        } );

        Button deleteKayakButton = new Button( "Delete Kayak" );
        deleteKayakButton.setOnAction( e -> {
            Kayak selectedKayak = kayakTable.getSelectionModel().getSelectedItem();
            if( selectedKayak != null ) {
                inventoryService.delete( selectedKayak );
                kayakObservable.setAll( inventoryService.findAll( Kayak.class ) );
                kayakTable.refresh();
            }
        } );

        buttonsAndSearchField.setPadding( new Insets( 10, 10, 3, 10 ) );
        buttonsAndSearchField.setAlignment( Pos.CENTER_LEFT );

        Label filterLabel = new Label( "  Filter" );
        filterLabel.setStyle( "-fx-font-size: 14px; -fx-text-fill: white;  " );

        TextField filterField = new TextField();
        filterField.setStyle( "-fx-background-color: #2d2d2d; -fx-text-fill: white; -fx-border-color: #444444" );

        filterField.textProperty().addListener( (observable, oldValue, newValue) -> {
            filteredKayaks.setPredicate( kayak -> {
                if( newValue == null || newValue.trim().isEmpty() )
                    return true;

                String filter =  newValue.toLowerCase();

                return kayak.getId().toString().contains( filter )
                        || kayak.getName().toLowerCase().contains( filter )
                        || String.valueOf( kayak.getNumberOfSeats() ).contains( filter )
                        || String.valueOf( kayak.getPrice() ).contains( filter )
                        || String.valueOf( kayak.getStatus() ).toLowerCase().contains( filter );
            } );
        } );

        buttonsAndSearchField.getChildren().addAll( addKayakButton, deleteKayakButton, filterLabel, filterField );

        addKayakButton.getStyleClass().add( "crud-button" );
        deleteKayakButton.getStyleClass().add( "crud-button" );

        kayakView.getChildren().addAll( buttonsAndSearchField, kayakTable );

        VBox.setVgrow(kayakTable, Priority.ALWAYS );
        kayakTable.setMaxHeight( Double.MAX_VALUE );
        kayakTable.setPrefHeight(Integer.MAX_VALUE);
        kayakView.setPrefHeight(Integer.MAX_VALUE);

        return kayakView;
    }
}