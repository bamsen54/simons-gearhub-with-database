package com.simon;

import com.simon.gui.sideMenu.SideMenu;
import com.simon.gui.util.CssUtil;
import com.simon.repo.*;
import com.simon.service.IncomeService;
import com.simon.service.InventoryService;
import com.simon.service.MemberService;
import com.simon.service.RentalService;
import com.simon.util.HibernateUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        HibernateUtil.getSessionFactory().openSession();

        MemberRepo memberRepo = new MemberRepoImpl();
        RentalRepo rentalRepo = new RentalRepoImpl();
        IncomeRepo incomeRepo = new IncomeRepoImpl();

        BikeRepo bikeRepo = new BikeRepoImpl();
        KayakRepo kayakRepo = new KayakRepoImpl();
        TentRepo tentRepo = new TentRepoImpl();

        MemberService memberService = new MemberService(memberRepo, null);
        InventoryService inventoryService = new InventoryService( bikeRepo,  kayakRepo, tentRepo );
        IncomeService incomeService = new IncomeService( incomeRepo );
        RentalService rentalService = new RentalService( rentalRepo, memberRepo, bikeRepo, kayakRepo, tentRepo, inventoryService, incomeService );

        BorderPane root = new BorderPane();
        root.setId("main-root");

        root.setStyle("-fx-background-color: transparent;");

        CssUtil.setCss( root, "/root.css" );

        root.setLeft( SideMenu.get( root, memberRepo, rentalRepo, memberService, inventoryService, rentalService, incomeService ) );
        Label welcomeLabel = new Label( "Welcome" );
        welcomeLabel.setStyle( "-fx-font-size: 24px; -fx-text-fill: white" );
        root.setCenter( welcomeLabel);

        Scene scene = new Scene( root, 1366, 768 );
        scene.setFill( Color.web("#121212") );

        stage.setScene(scene);
        stage.setTitle("GearHub v1.0");
        stage.resizableProperty().set( false );
        stage.show();
    }
}