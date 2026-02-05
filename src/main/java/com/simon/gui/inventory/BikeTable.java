package com.simon.gui.inventory;

import com.simon.entity.Bike;
import com.simon.entity.Member;
import com.simon.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class BikeTable {

    public static Parent getBikeView( InventoryService inventoryService ) {

        BorderPane bikeView = new BorderPane();

        ObservableList<Bike> bikeObservable = FXCollections.observableArrayList();
        FilteredList<Bike> filteredBikes = new FilteredList<>( bikeObservable );
        SortedList<Bike> sortedBikes = new SortedList<>( filteredBikes );

        TableView<Bike> bikeTable = new TableView<>();
        bikeTable.setEditable( true );
        bikeTable.setFixedCellSize( 50 );
        bikeTable.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );

        bikeTable.setItems( sortedBikes );
        sortedBikes.comparatorProperty().bind( bikeTable.comparatorProperty() );

        TableColumn<Bike, Long> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>( "id" ) );

        TableColumn<Bike,String> name = new TableColumn<>( "Name" );
        name.setCellValueFactory( new PropertyValueFactory<>( "name" ) );

        TableColumn<Bike,String> bikeType = new TableColumn<>( "Bike Type" );
        bikeType.setCellValueFactory( new PropertyValueFactory<>( "bikeType" ) );



        return bikeView;
    }
}
