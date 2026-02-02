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


        TableColumn<Member,String> firstName = new TableColumn<>("First Name");
        firstName.setCellValueFactory( new PropertyValueFactory<>("firstName") );

        TableColumn<Member,String> lastName = new TableColumn<>("Last Name");
        lastName.setCellValueFactory( new PropertyValueFactory<>("lastName") );

        firstName.setCellFactory( TextFieldTableCell.forTableColumn() );
        firstName.setOnEditCommit( event -> {
            Member member = event.getRowValue();
            member.setFirstName( event.getNewValue() );
            memberService.updateMember( member );

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

        memberTable.getColumns().addAll( firstName, lastName );

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
