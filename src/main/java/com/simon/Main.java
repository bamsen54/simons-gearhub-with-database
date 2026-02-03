package com.simon;

import com.simon.gui.sideMenu.SideMenu;
import com.simon.gui.util.CssUtil;
import com.simon.repo.MemberRepo;
import com.simon.repo.MemberRepoImpl;
import com.simon.service.MemberService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        MemberRepo memberRepo = new MemberRepoImpl();

        MemberService memberService = new MemberService(memberRepo, null);

        BorderPane root = new BorderPane();
        root.setId("main-root");

        root.setStyle("-fx-background-color: transparent;");

        CssUtil.setCss( root, "/root.css" );

        root.setLeft( SideMenu.get( root, memberRepo, memberService ) );
        root.setCenter( new Label( "Welcome" ) );

        Scene scene = new Scene( root, 1366, 768 );
        scene.setFill( Color.web("#121212") );

        stage.setScene(scene);
        stage.setTitle("GearHub v1.0");
        stage.resizableProperty().set( false );
        stage.show();
    }
}