package com.simon;

import com.simon.entity.Member;
import com.simon.gui.MemberMenu;
import com.simon.gui.sideMenu.SideMenu;
import com.simon.gui.util.CssUtil;
import com.simon.repo.MemberRepo;
import com.simon.repo.MemberRepoImpl;
import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        MemberRepo memberRepo = new MemberRepoImpl();

        BorderPane root = new BorderPane();
        root.setId("main-root");

        root.setStyle("-fx-background-color: transparent;");

        CssUtil.setCss( root, "/root.css" );

        root.setLeft( SideMenu.get( root, memberRepo ) );
        root.setCenter( new Label( "Welcome" ) );

        Scene scene = new Scene(root, 1280, 720);
        scene.setFill( Color.web("#121212") );


        stage.setScene(scene);
        stage.setTitle("GearHub v1.0");
        stage.resizableProperty().set( false );
        stage.show();
    }
}