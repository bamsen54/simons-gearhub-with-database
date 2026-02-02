package com.simon.gui;

import com.simon.entity.Member;
import com.simon.repo.MemberRepo;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.List;

public class MemberMenu {

    public static Parent display(MemberRepo memberRepo) {

        VBox memberMenu = new VBox(10);


        TableView<Member> memberTable = new TableView<>();

        TableColumn<Member,String> firstName = new TableColumn<>("First Name");
        firstName.setCellValueFactory( new PropertyValueFactory<>("firstName") );

        TableColumn<Member,String> lastame = new TableColumn<>("Last Name");
        lastame.setCellValueFactory( new PropertyValueFactory<>("lastName") );

        ProgressIndicator spinner = new ProgressIndicator();
        spinner.setMaxSize(50, 50); // Gör den lagom stor

        memberTable.setPlaceholder(  spinner  );



        Thread loadData = new Thread( () -> {
            List<Member> members = memberRepo.findAll();
            ObservableList<Member> memberObservable = FXCollections.observableList( members );

            Platform.runLater( () -> {
                memberTable.setItems( memberObservable );
            } );
        } );

        loadData.setDaemon( true );
        loadData.start();

        memberTable.getColumns().addAll( firstName, lastame );

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

    public static ObservableList<Member> updateMemberMenu(MemberRepo memberRepo) {
        ObservableList<Member> membersList = FXCollections.observableArrayList();

        List<Member> members = memberRepo.findAll();

        for( Member member : members ) {
            membersList.add( member );
        }

        return membersList;
    }
}
