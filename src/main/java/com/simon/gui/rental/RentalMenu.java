package com.simon.gui.rental;

import com.simon.entity.*;
import com.simon.gui.util.CssUtil;
import com.simon.service.InventoryService;
import com.simon.service.MemberService;
import com.simon.service.RentalService;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RentalMenu {

    public static Parent display(RentalService rentalService, MemberService memberService, InventoryService inventoryService) {

        VBox rentalMenu = new VBox(10);
        CssUtil.setCss(rentalMenu, "/member-menu.css");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        ObservableList<Rental> rentalObservable = FXCollections.observableArrayList();
        FilteredList<Rental> filteredRentals = new FilteredList<>(rentalObservable);
        SortedList<Rental> sortedRentals = new SortedList<>(filteredRentals);

        TableView<Rental> rentalTable = new TableView<>();
        rentalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        rentalTable.setFixedCellSize(50);
        rentalTable.setItems(sortedRentals);
        sortedRentals.comparatorProperty().bind(rentalTable.comparatorProperty());

        TableColumn<Rental, String> itemCol = new TableColumn<>("Item");
        itemCol.setCellValueFactory(data -> {
            Rental r = data.getValue();
            Long id = r.getRentalObjectId();
            String itemName = "Unknown";
            try {
                if (r.getRentalType() == RentalType.BIKE) {
                    Bike b = inventoryService.findById(Bike.class, id);
                    if (b != null) itemName = b.getName();
                } else if (r.getRentalType() == RentalType.KAYAK) {
                    Kayak k = inventoryService.findById(Kayak.class, id);
                    if (k != null) itemName = k.getName();
                } else if (r.getRentalType() == RentalType.TENT) {
                    Tent t = inventoryService.findById(Tent.class, id);
                    if (t != null) itemName = t.getName();
                }
            } catch (Exception e) {}
            return new SimpleStringProperty(id + " - " + r.getRentalType() + ": " + itemName);
        });

        TableColumn<Rental, String> memberCol = new TableColumn<>("Member");
        memberCol.setCellValueFactory(data -> {
            Member m = data.getValue().getMember();
            if (m != null) {
                return new SimpleStringProperty(m.getId() + " - " + m.getFirstName() + " " + m.getLastName());
            }
            return new SimpleStringProperty("Missing");
        });

        TableColumn<Rental, String> rentedCol = new TableColumn<>("Rented");
        rentedCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getRentalDate() != null ? data.getValue().getRentalDate().format(formatter) : ""));

        TableColumn<Rental, String> returnedCol = new TableColumn<>("Returned");
        returnedCol.setCellValueFactory(data -> {
            var date = data.getValue().getReturnDate();
            return new SimpleStringProperty(date != null ? date.format(formatter) : "NOT RETURNED");
        });

        rentalTable.getColumns().addAll(itemCol, memberCol, rentedCol, returnedCol);

        HBox controls = new HBox(10);
        controls.setPadding(new Insets(10, 10, 3, 10));
        controls.setAlignment(Pos.CENTER_LEFT);

        Button addRentalButton = new Button("Add Rental");
        addRentalButton.getStyleClass().add("crud-button");

        Button returnItemButton = new Button("Return Item");
        returnItemButton.getStyleClass().add("crud-button");
        returnItemButton.setDisable(true);

        Label filterLabel = new Label("  Filter");
        filterLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

        TextField filterField = new TextField();
        filterField.setStyle("-fx-background-color: #2d2d2d; -fx-text-fill: white; -fx-border-color: #444444");

        Thread loadData = new Thread(() -> {
            List<Rental> rentals = rentalService.getAllRentals();
            Platform.runLater(() -> {
                rentalObservable.setAll(rentals);
                rentalTable.setPlaceholder(new Label("No rentals found"));
            });
        });
        loadData.setDaemon(true);
        loadData.start();

        rentalTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            returnItemButton.setDisable(newVal == null || newVal.getReturnDate() != null);
        });

        Runnable handleReturn = () -> {
            Rental selected = rentalTable.getSelectionModel().getSelectedItem();
            if (selected != null && selected.getReturnDate() == null) {
                selected.setReturnDate(LocalDateTime.now());
                rentalService.updateRental(selected);
                rentalTable.refresh();
                returnItemButton.setDisable(true);
            }
        };

        returnItemButton.setOnAction(e -> handleReturn.run());

        rentalTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) handleReturn.run();
        });

        addRentalButton.setOnAction(e -> {
            RentalPopUp.display(rentalService, memberService, inventoryService);
            rentalObservable.setAll(rentalService.getAllRentals());
        });

        filterField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredRentals.setPredicate(rental -> {
                if (newVal == null || newVal.isBlank()) return true;
                String filter = newVal.toLowerCase().trim();

                String mInfo = rental.getMember() != null ?
                        (rental.getMember().getId() + " - " + rental.getMember().getFirstName() + " " + rental.getMember().getLastName()) : "";

                String iInfo = rental.getRentalObjectId() + " - " + rental.getRentalType();

                String rDate = rental.getRentalDate() != null ? rental.getRentalDate().format(formatter) : "";
                String retDate = rental.getReturnDate() != null ? rental.getReturnDate().format(formatter) : "not returned";

                String allData = (mInfo + " " + iInfo + " " + rDate + " " + retDate).toLowerCase();
                return allData.contains(filter);
            });
        });

        controls.getChildren().addAll(addRentalButton, returnItemButton, filterLabel, filterField);
        rentalMenu.getChildren().addAll(controls, rentalTable);
        VBox.setVgrow(rentalTable, Priority.ALWAYS);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), rentalMenu);
        fadeIn.setFromValue(0.0); fadeIn.setToValue(1.0); fadeIn.play();

        return rentalMenu;
    }
}