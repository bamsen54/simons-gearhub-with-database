package com.simon.gui.inventory;

import com.simon.entity.ItemStatus;
import com.simon.entity.Tent;
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
import javafx.util.converter.DoubleStringConverter;

import java.math.BigDecimal;
import java.util.List;

public class TentTable {

    public static Parent getTentView( InventoryService inventoryService ) {

        VBox tentView = new VBox( 10 );

        CssUtil.setCss( tentView, "inventory.css" );

        ObservableList<Tent> tentObservable = FXCollections.observableArrayList();
        FilteredList<Tent> filteredTents = new FilteredList<>( tentObservable );
        SortedList<Tent> sortedTents = new SortedList<>( filteredTents );

        TableView<Tent> tentTable = new TableView<>();
        tentTable.setEditable( true );
        tentTable.setFixedCellSize( 50 );
        tentTable.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );

        Thread loadData = new Thread( () -> {

            List<Tent> tents = inventoryService.findAll( Tent.class );

            Platform.runLater( () -> {
                tentObservable.setAll( tents );
                tentTable.setPlaceholder( new Label( "No matches" )  );
            } );
        } );

        loadData.setDaemon( true );
        loadData.start();
        sortedTents.comparatorProperty().bind( tentTable.comparatorProperty() );

        TableColumn<Tent, Long> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>( "id" ) );

        TableColumn<Tent,String> name = new TableColumn<>( "Name" );
        name.setCellValueFactory( new PropertyValueFactory<>( "name" ) );

        TableColumn<Tent,Integer> capacity = new TableColumn<>( "Capacity" );
        capacity.setCellValueFactory( cellData -> {
            return cellData.getValue().capacityProperty().asObject();
        } );

        TableColumn<Tent,Double> weight = new TableColumn<>( "Weight" );
        weight.setCellValueFactory( cellData -> {
            return cellData.getValue().weightProperty().asObject();
        } );

        TableColumn<Tent, BigDecimal> price = new TableColumn<>( "Daily Price" );
        price.setCellValueFactory(cellData -> {
            return cellData.getValue().priceProperty();
        } );

        id.setPrefWidth( 60 );
        id.setMinWidth( 50 );
        name.setPrefWidth( 100 );
        capacity.setPrefWidth( 75 );
        weight.setPrefWidth( 75 );
        price.setPrefWidth( 100 );

        name.setCellFactory( TextFieldTableCell.forTableColumn() );
        name.setOnEditCommit( event -> {
            if  (event.getNewValue() != null && !event.getNewValue().trim().isEmpty() ) {
                Tent tent = event.getRowValue();
                tent.setName( event.getNewValue().trim() );
                inventoryService.update( tent );
            }
            else {
                tentTable.refresh();
            }
        } );

        capacity.setCellFactory(TextFieldTableCell.forTableColumn( new SafeIntegerConverter() ) );
        capacity.setOnEditCommit( event -> {
            if  ( event.getNewValue() != null  ) {
                Tent tent = event.getRowValue();
                tent.setCapacity( event.getNewValue() );
                inventoryService.update( tent );
            }
            else {
                tentTable.refresh();
            }
        } );

        weight.setCellFactory(TextFieldTableCell.forTableColumn( new DoubleStringConverter() ) );
        weight.setOnEditCommit( event -> {
            if  ( event.getNewValue() != null  ) {
                Tent tent = event.getRowValue();
                tent.setWeight( event.getNewValue() );
                inventoryService.update( tent );
            }
            else {
                tentTable.refresh();
            }
        } );

        price.setCellFactory( TextFieldTableCell.forTableColumn( new SafeBigDecimalConverter() ) );
        price.setOnEditCommit(event -> {
            BigDecimal newValue = event.getNewValue();
            if ( newValue != null && newValue.compareTo(BigDecimal.ZERO) >= 0 ) {
                Tent tent = event.getRowValue();
                tent.setPrice(newValue);
                inventoryService.update(tent);
            } else {
                tentTable.refresh();
            }
        });

        TableColumn<Tent, ItemStatus> status = new TableColumn<>("Status");
        status.setCellValueFactory( cellData -> cellData.getValue().statusProperty() );

        tentTable.setItems( sortedTents );
        tentTable.getColumns().addAll( id, name, capacity, weight, price, status );

        HBox buttonsAndSearchField = new HBox( 10 );

        Button addTentButton = new Button( "Add Tent" );
        addTentButton.setOnAction( e -> {
            Tent newTent = new Tent( "Tent Name", 2, 2.5, new BigDecimal( "0" ), ItemStatus.AVAILABLE );
            inventoryService.save( newTent  );
            tentObservable.setAll( inventoryService.findAll( Tent.class ) );
            tentTable.refresh();
        } );

        Button deleteTentButton = new Button( "Delete Tent" );
        deleteTentButton.setOnAction( e -> {
            Tent selectedTent = tentTable.getSelectionModel().getSelectedItem();
            if( selectedTent != null ) {
                inventoryService.delete( selectedTent );
                tentObservable.setAll( inventoryService.findAll( Tent.class ) );
                tentTable.refresh();
            }
        } );

        buttonsAndSearchField.setPadding( new Insets( 10, 10, 3, 10 ) );
        buttonsAndSearchField.setAlignment( Pos.CENTER_LEFT );

        Label filterLabel = new Label( "  Filter" );
        filterLabel.setStyle( "-fx-font-size: 14px; -fx-text-fill: white;  " );

        TextField filterField = new TextField();
        filterField.setStyle( "-fx-background-color: #2d2d2d; -fx-text-fill: white; -fx-border-color: #444444" );

        filterField.textProperty().addListener( (observable, oldValue, newValue) -> {
            filteredTents.setPredicate( tent -> {
                if( newValue == null || newValue.trim().isEmpty() )
                    return true;
                String filter =  newValue.toLowerCase();
                return tent.getId().toString().contains( filter )
                        || tent.getName().toLowerCase().contains( filter )
                        || String.valueOf( tent.getCapacity() ).contains( filter )
                        || String.valueOf( tent.getWeight() ).contains( filter )
                        || String.valueOf( tent.getPrice() ).contains( filter )
                        || String.valueOf( tent.getStatus() ).toLowerCase().contains( filter );
            } );
        } );

        buttonsAndSearchField.getChildren().addAll( addTentButton, deleteTentButton, filterLabel, filterField );

        addTentButton.getStyleClass().add( "crud-button" );
        deleteTentButton.getStyleClass().add( "crud-button" );

        tentView.getChildren().addAll( buttonsAndSearchField, tentTable );

        VBox.setVgrow(tentTable, Priority.ALWAYS );
        tentTable.setMaxHeight( Double.MAX_VALUE );
        tentTable.setPrefHeight(Integer.MAX_VALUE);
        tentView.setPrefHeight(Integer.MAX_VALUE);

        return tentView;
    }
}