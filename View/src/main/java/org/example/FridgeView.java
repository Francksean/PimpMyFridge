package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.example.components.PanelInfos;
import org.example.components.PanelGraphics;
import org.example.components.PanelLanding;
import org.example.components.PanelParams;

import java.util.Observable;
import java.util.Observer;

public class FridgeView extends Application implements Observer, IFridgeView   {

    StackPane root = new StackPane();
    String colorBlue = "#2fb6ee";
    String fontUsed = "Roboto";
    int menusItemSize = 60;
    private static IFridgeParams params = new FridgeParams();

    private IFridgeModel viewModel;

    @Override
    public void update(Observable o, Object params) {
        setParams((IFridgeParams) params);
        System.out.println("ok update ");
        //panelParams.updateLabels((IFridgeParams) params);
    }

    public IFridgeParams getParams() {
        return params;
    }
    public void setParams(IFridgeParams paramss) {
        params = paramss;
    }

    public FridgeView(IFridgeModel model){
        this.setViewModel(model);
    }

    public void setViewModel(IFridgeModel viewModel) {
        this.viewModel = viewModel;
    }

    public IFridgeModel getViewModel() {
        return viewModel;
    }

    public FridgeView() {}

    @Override
    public void start(Stage stage){
        stage.setTitle("FRIDGY");
        stage.setResizable(false);
        stage.getIcons().add(new Image("/miniFridgeIcon2.png"));

        PanelInfos panelInfos = new PanelInfos(this);
        PanelGraphics panelGraphics = new PanelGraphics(this);
        PanelParams panelParams = new PanelParams(this, this.getViewModel());


        BorderPane pane = new BorderPane();
        HBox menu = new HBox();

        root.getChildren().add(new PanelLanding(this));
        // bouton d'acceuil
        Image imgAcc = new Image(String.valueOf(getClass().getResource("/homeIcon.png")));
        ImageView imgAccView = new ImageView(imgAcc);
        Button Btnacceuil = new Button();
        Btnacceuil.setGraphic(imgAccView);
        Btnacceuil.setOnAction(e ->{ SwitchTo(new PanelLanding(this));stage.setTitle("FRIDGY - Acceuil");});
        Label btnAccLabel = new Label("Acceuil");
        VBox accVb = new VBox(Btnacceuil, btnAccLabel);

        // bouton fenêtre des chiffres
        Image imgPar = new Image(String.valueOf(getClass().getResource("/settingsIcon.png")));
        ImageView imgParView = new ImageView(imgPar);
        Button BtnParams = new Button();
        BtnParams.setGraphic(imgParView);
        BtnParams.setOnAction(e ->{ SwitchTo(panelParams);stage.setTitle("FRIDGY - Parmètres internes");});
        Label btnParLabel = new Label("Votre frigo");
        VBox parVb = new VBox(BtnParams, btnParLabel);

        // bouton fenêtre des graphes
        Image imgGraph = new Image(String.valueOf(getClass().getResource("/graphIcon.png")));
        ImageView imgGraphView = new ImageView(imgGraph);
        Button BtnGraphics = new Button();
        BtnGraphics.setGraphic(imgGraphView);
        BtnGraphics.setOnAction(e ->{ SwitchTo(panelGraphics);stage.setTitle("FRIDGY - Graphes");});
        Label btnGraphLabel = new Label("Graphes");
        VBox graphVb = new VBox(BtnGraphics, btnGraphLabel);


        // bouton infos
        Image imgInfos = new Image(String.valueOf(getClass().getResource("/infosIcon.png")));
        ImageView imgInfosView = new ImageView(imgInfos);
        Button BtnInfos = new Button();
        BtnInfos.setGraphic(imgInfosView);
        BtnInfos.setOnAction(e ->{ SwitchTo(panelInfos);stage.setTitle("FRIDGY - Centre de notifications");});
        Label btnInfosLabel = new Label("Infos");
        VBox infosVb = new VBox(BtnInfos, btnInfosLabel);

        // tableau des label des boutons du menu
        Label[] labelsMenus = {btnAccLabel,btnParLabel,btnGraphLabel,btnInfosLabel};
        for(Label items : labelsMenus){
            items.setPrefSize(menusItemSize,10);
            items.setFont(Font.font(fontUsed, FontWeight.NORMAL, 15));
            items.setAlignment(Pos.CENTER);
        }

        //tableau des boutons du menu
        Button[] btnMenus = {Btnacceuil,BtnParams,BtnGraphics,BtnInfos};
        for(Button items : btnMenus){
            items.setOnMouseEntered(e -> items.setStyle("-fx-border-radius: 50%; -fx-background-color: none; -fx-border-width: 1px; -fx-border-color:" + colorBlue + "; -fx-translate-y: -5px"));
            items.setOnMouseExited(e -> items.setStyle("-fx-border-radius: 50%; -fx-background-color: none; -fx-border-width: 1px; -fx-border-color: gainsboro; -fx-translate-y: 0px"));
            items.setPrefSize(menusItemSize,menusItemSize);
            items.setStyle("-fx-border-radius: 50%; -fx-background-color: none; -fx-border-width: 1px; -fx-border-color: gainsboro");
            items.setAlignment(Pos.CENTER);
        }

        //tableau des box bouton du menu + label
        VBox[] boxesMenus = {accVb,parVb, graphVb, infosVb};
        for(VBox items : boxesMenus){
            items.setSpacing(10);
            menu.getChildren().add(items);
        }

        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(10);
        pane.setBottom(menu);
        menu.setPadding(new Insets(0,0,25,0));

        // paneau principal
        root.setPrefSize(370,650);

        pane.setCenter(root);
        Scene scene = new Scene(pane); // ajustez la taille selon vos besoins
        stage.setScene(scene);
        stage.sizeToScene();

        stage.show();

    }

    @Override
    public void main(String[] args) {
        launch(args);
    }

    public void SwitchTo(StackPane pane){
        root.getChildren().clear();
        root.getChildren().add(pane);
        root.setAlignment(Pos.CENTER);
    }
}