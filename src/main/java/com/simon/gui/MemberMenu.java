package com.simon.gui;

import com.simon.entity.Member;
import com.simon.gui.util.CssUtil;
import com.simon.service.MemberService;
import javafx.animation.FadeTransition;
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
import javafx.util.Duration;

import java.util.List;

public class MemberMenu {

    public static Parent display(MemberService memberService) {

        VBox memberMenu = new VBox( 10 );
        CssUtil.setCss( memberMenu, "/member-menu.css" );

        ObservableList<Member> memberObservable = FXCollections.observableArrayList();
        FilteredList<Member> filteredMembers = new FilteredList<>(memberObservable);
        SortedList<Member> sortedMembers = new SortedList<>( filteredMembers );

        TableView<Member> memberTable = new TableView<>();
        memberTable.setEditable( true );
        memberTable.setFixedCellSize( 50 );
        memberTable.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );

        memberTable.setItems( sortedMembers );
        sortedMembers.comparatorProperty().bind( memberTable.comparatorProperty() );

        TableColumn<Member, Long> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>( "id" ) );

        TableColumn<Member,String> firstName = new TableColumn<>( "First Name" );
        firstName.setCellValueFactory( new PropertyValueFactory<>( "firstName" ) );

        TableColumn<Member,String> lastName = new TableColumn<>( "Last Name" );
        lastName.setCellValueFactory( new PropertyValueFactory<>( "lastName" ) );

        TableColumn<Member,String> email = new TableColumn<>("Email");
        email.setCellValueFactory( new PropertyValueFactory<>( "email" ) );

        id.setPrefWidth( 60 );
        firstName.setPrefWidth(100);
        lastName.setPrefWidth(100);
        email.setPrefWidth(250);

        id.setMinWidth( 50 );

        firstName.setCellFactory( TextFieldTableCell.forTableColumn() );
        firstName.setOnEditCommit( event -> {
            if (event.getNewValue() != null && !event.getNewValue().trim().isEmpty() ) {
                Member member = event.getRowValue();
                member.setFirstName( event.getNewValue().trim() );
                memberService.updateMember( member );
            }

            else {
                memberTable.refresh();
            }
        } );

        lastName.setCellFactory( TextFieldTableCell.forTableColumn() );
        lastName.setOnEditCommit( event -> {
            if (event.getNewValue() != null && !event.getNewValue().trim().isEmpty() ) {
                Member member = event.getRowValue();
                member.setLastName( event.getNewValue().trim() );
                memberService.updateMember( member );
            }

            else {
                memberTable.refresh();
            }
        } );

        email.setCellFactory( TextFieldTableCell.forTableColumn() );
        email.setOnEditCommit(event -> {

            String newEmail = event.getNewValue().trim().toLowerCase();
            String oldEmail = event.getOldValue();

            boolean exists = memberObservable.stream()
                    .anyMatch(m -> m.getEmail().equalsIgnoreCase(newEmail) && m != event.getRowValue());

            if (exists) {
                ShowDarkAlert.showDarkAlert( "Error", "Email Already exists" );
                memberTable.refresh();
                return;
            }

            try {
                Member member = event.getRowValue();
                member.setEmail(newEmail);
                memberService.updateMember(member);
            }

            catch (Exception e) {
                event.getRowValue().setEmail(oldEmail);
                memberTable.refresh();
            }
        } );

        firstName.setId("first-name-column");
        lastName.setId("last-name-column");

        firstName.setPrefWidth( 150 );
        lastName.setPrefWidth( 150 );

        ProgressIndicator spinner = new ProgressIndicator();
        spinner.setMaxSize( 64, 64 );

        memberTable.setPlaceholder(  spinner  );

        Thread loadData = new Thread( () -> {

            List<Member> members = memberService.findAll();

            Platform.runLater( () -> {
                memberObservable.setAll( members );
                memberTable.setPlaceholder(  new Label( "No matches" )  );
            } );
        } );

        loadData.setDaemon( true );
        loadData.start();

        memberTable.getColumns().addAll( id, firstName, lastName, email );


        memberMenu.setVgrow( memberTable , Priority.ALWAYS);

        FadeTransition fadeIn = new FadeTransition( Duration.millis( 500 ), memberMenu );

        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        // buttons and search field
        HBox buttonsAndSearchField = new HBox( 10 );

        Button addMemberButton = new Button( "Add Member" );
        addMemberButton.setOnAction( e -> {
            Member newMember = new Member("New Member", "New Member", "mail");
            memberService.addMember(newMember);

            newMember.setEmail( "example" + newMember.getId() + "@mail.com" );
            memberService.updateMember(newMember);

            memberObservable.add( newMember );
        } );

        Button deleteMemberButton = new Button( "Delete Member" );
        deleteMemberButton.setOnAction( e -> {
            Member selectedMember = memberTable.getSelectionModel().getSelectedItem();

            if( selectedMember == null )
                return;

            memberService.deleteMember( selectedMember );
            memberObservable.setAll( memberService.findAll() );
            memberTable.refresh();
        } );

        addMemberButton.getStyleClass().add( "crud-button" );
        deleteMemberButton.getStyleClass().add( "crud-button" );

        Label filterLabel = new Label( "  Filter" );
        filterLabel.setStyle( "-fx-font-size: 14px; -fx-text-fill: white;  " );

        TextField filterField = new TextField();

        filterField.setStyle( "-fx-background-color: #2d2d2d; -fx-text-fill: white; -fx-border-color: #444444" );

        filterField.textProperty().addListener( (observable, oldValue, newValue) -> {

            filteredMembers.setPredicate( member -> {
                if( newValue == null || newValue.trim().isEmpty() )
                    return true;

                String filter =  newValue.toLowerCase();

                return member.getId().toString().contains( filter ) || member.getFirstName().toLowerCase().contains( filter )
                                                                    || member.getLastName().toLowerCase().contains( filter )
                                                                    || member.getEmail().toLowerCase().contains( filter );
            } );
        } );

        buttonsAndSearchField.setPadding( new Insets( 10, 10, 3, 10 ) );

        buttonsAndSearchField.setAlignment( Pos.CENTER_LEFT );
        buttonsAndSearchField.getChildren().addAll( addMemberButton, deleteMemberButton, filterLabel, filterField );

        memberMenu.getChildren().addAll( buttonsAndSearchField, memberTable );

        return  memberMenu;
    }
}
