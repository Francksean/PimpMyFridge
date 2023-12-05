package org.example;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.components.PanelGraphics;
import org.example.components.PanelInfos;
import org.example.components.PanelLanding;
import org.example.components.PanelParams;

public class FridgeView extends Application implements  IFridgeView   {

    StackPane root = new StackPane();

    int menusItemSize = 100;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pimp My Fridge");
        stage.setResizable(false);

        BorderPane pane = new BorderPane();
        HBox menu = new HBox();

        root.getChildren().add(new PanelLanding());
        // bouton d'acceuil
        Button Btnacceuil = new Button("Acceuil");
        Btnacceuil.setOnAction(e ->{ SwitchTo(new PanelLanding());});
        Label btnAccLabel = new Label("Acceuil");
        VBox accVb = new VBox(Btnacceuil, btnAccLabel);

        // bouton fenêtre des chiffres
        Button BtnParams = new Button("Votre frigo");
        BtnParams.setOnAction(e ->{ SwitchTo(new PanelParams());});
        Label btnParLabel = new Label("Votre frigo");
        VBox parVb = new VBox(BtnParams, btnParLabel);

        // bouton fenêtre des graphes
        Button BtnGraphics = new Button("Graphes");
        BtnGraphics.setOnAction(e ->{ SwitchTo(new PanelGraphics());});
        Label btnGraphLabel = new Label("Graphes");
        VBox graphVb = new VBox(BtnGraphics, btnGraphLabel);


        // bouton infos
        Button BtnInfos = new Button("Infos");
        BtnInfos.setOnAction(e ->{ SwitchTo(new PanelInfos());});
        Label btnInfosLabel = new Label("Infos");
        VBox infosVb = new VBox(BtnInfos, btnInfosLabel);

        // tableau des label des boutons du menu
        Label[] labelsMenus = {btnAccLabel,btnParLabel,btnGraphLabel,btnInfosLabel};
        for(Label items : labelsMenus){
            items.setPrefSize(menusItemSize,10);
            items.setAlignment(Pos.CENTER);
        }

        //tableau des boutons du menu
        Button[] btnMenus = {Btnacceuil,BtnParams,BtnGraphics,BtnInfos};
        for(Button items : btnMenus){
            items.setPrefSize(menusItemSize,menusItemSize);
            items.setStyle("-fx-background-radius: 50%;");
            items.setAlignment(Pos.CENTER);
        }

        //tableau des box bouton du menu + label
        VBox[] boxesMenus = {accVb,parVb, graphVb, infosVb};
        for(VBox items : boxesMenus){
            menu.getChildren().add(items);
        }

        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(10);
        pane.setBottom(menu);

        // paneau principal
        root.setPrefSize(500,400);



        pane.setCenter(root);
        Scene scene = new Scene(pane); // ajustez la taille selon vos besoins
        stage.setScene(scene);
        stage.sizeToScene();

        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

    public void SwitchTo(StackPane pane){
        root.getChildren().clear();
        root.getChildren().add(pane);
        root.setAlignment(Pos.CENTER);
    }
}



















