package com.simon.gui;

import com.simon.entity.Member;
import com.simon.repo.MemberRepo;
import com.simon.service.MemberService;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.converter.DefaultStringConverter;

import java.util.List;

public class MemberMenu {

    public static Parent display(MemberService memberService) {

        VBox memberMenu = new VBox(10);


        TableView<Member> memberTable = new TableView<>();
        memberTable.setEditable( true );
        memberTable.setFixedCellSize( 50 );
        memberTable.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );

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
        email.setOnEditCommit( event -> {
            if (event.getNewValue() != null && !event.getNewValue().trim().isEmpty() ) {
                Member member = event.getRowValue();
                member.setEmail( event.getNewValue().trim().toLowerCase() );
                memberService.updateMember( member );
                memberTable.refresh();
            }

            else {
                memberTable.refresh();
            }
        } );

        firstName.setId("first-name-column");
        lastName.setId("last-name-column");

        firstName.setPrefWidth( 150 );
        lastName.setPrefWidth( 150 );

        ProgressIndicator spinner = new ProgressIndicator();
        spinner.setMaxSize(50, 50); // Gör den lagom stor

        memberTable.setPlaceholder(  spinner  );


        Thread loadData = new Thread( () -> {
            List<Member> members = memberService.findAll();
            ObservableList<Member> memberObservable = FXCollections.observableList( members );

            Platform.runLater( () -> {
                memberTable.setItems( memberObservable );
            } );
        } );

        loadData.setDaemon( true );
        loadData.start();

        memberTable.getColumns().addAll( id, firstName, lastName, email );

        memberMenu.getChildren().addAll( memberTable );

        memberMenu.setVgrow( memberTable , Priority.ALWAYS);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), memberMenu );

        // 2. Definiera start och stopp (0.0 är osynlig, 1.0 är helt synlig)
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        // 3. Starta animationen
        fadeIn.play();

        return  memberMenu;
    }

    public static ObservableList<Member> updateMemberMenu( MemberService memberService ) {
        ObservableList<Member> membersList = FXCollections.observableArrayList();

        List<Member> members = memberService.findAll();

        for( Member member : members ) {
            membersList.add( member );
        }

        return membersList;
    }
}
