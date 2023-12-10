package org.example.components;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.FridgeView;
import org.example.IFridgeModel;
import org.example.IPanelParams;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PanelParams extends StackPane implements IPanelParams {

    private final String colorBlue = "#2fb6ee";

    private final Label extTempLabel;
    private final Label extHumLabel;
    private final Label internTempLabel;
    private final Label internHumLabel;

    private int wantedTemp;

    private final FridgeView paramsView;
    private final IFridgeModel paramsModel;

    public PanelParams(FridgeView view, IFridgeModel model) {
        this.paramsModel = model;
        this.paramsView = view;
        this.setPrefSize(500, 400);

        Label headerTitle = new Label("VOTRE FRIGO");
        String fontUsed = "Roboto";
        headerTitle.setFont(Font.font(fontUsed, FontWeight.BOLD, 25));
        headerTitle.setPrefSize(500, 70);
        headerTitle.setAlignment(Pos.CENTER);
        headerTitle.setStyle("-fx-background-color: #2fb6ee");
        setAlignment(headerTitle, Pos.TOP_CENTER);

        extTempLabel = new Label(paramsView.getParams().getExternTemp() + "°C");
        extTempLabel.setPrefSize(100, 50);
        extTempLabel.setAlignment(Pos.CENTER);
        extTempLabel.setFont(Font.font(fontUsed, FontWeight.NORMAL, 25));

        extHumLabel = new Label(paramsView.getParams().getExternHum() + "%");
        extHumLabel.setPrefSize(100, 50);
        extHumLabel.setAlignment(Pos.CENTER);
        extHumLabel.setFont(Font.font(fontUsed, FontWeight.NORMAL, 25));

        VBox extParams = new VBox(extTempLabel, extHumLabel);
        extParams.setSpacing(10);
        extParams.setAlignment(Pos.CENTER);

        Label cityRegion = new Label("Douala, Littoral");
        Label countryLoc = new Label("Cameroun");
        VBox location = new VBox(cityRegion, countryLoc);
        location.setAlignment(Pos.CENTER);
        location.setPrefSize(120, 50);

        HBox InfosWrapperTop = new HBox(extParams, location);
        InfosWrapperTop.setAlignment(Pos.CENTER);
        InfosWrapperTop.setSpacing(40);

        Label interTempLabelTitle = new Label("Température interne");

        internTempLabel = new Label(paramsView.getParams().getInternTemp() + "°C");
        VBox interTempBox = new VBox(interTempLabelTitle, internTempLabel);

        Label internHumLabelTitle = new Label("Humidité interne");

        internHumLabel = new Label(Double.toString(paramsView.getParams().getInternHum()) + "%");
        VBox internHumBox = new VBox(internHumLabelTitle, internHumLabel);

        Label[] propsLabel = {internTempLabel, internHumLabel};
        for (Label items : propsLabel) {
            items.setPrefSize(100, 100);
            items.setStyle("-fx-border-width: 1px; -fx-border-color:" + colorBlue + "; -fx-border-radius: 1em; -fx-text-alignment: center; -fx-background-color: none;");
            items.setAlignment(Pos.CENTER);
            items.setFont(Font.font(fontUsed, FontWeight.NORMAL, 15));
        }

        VBox[] boxeFidgeParams = {interTempBox, internHumBox};
        for (VBox items : boxeFidgeParams) {
            items.setAlignment(Pos.CENTER);
            items.setSpacing(10);
        }

        HBox internParamsWrapper = new HBox(interTempBox, internHumBox);
        internParamsWrapper.setSpacing(50);
        internParamsWrapper.setAlignment(Pos.CENTER);

        Label labelInputSlider = new Label("Température de consigne");

        Slider inputWantedTemp = new Slider(15, 40, 0);
        inputWantedTemp.setShowTickLabels(true);
        inputWantedTemp.setShowTickMarks(true);
        inputWantedTemp.setMajorTickUnit(5);
        inputWantedTemp.setPrefSize(250, 50);
        inputWantedTemp.setStyle(
                "-fx-control-inner-background: none ; " +
                        "-fx-background-color: transparent;");

        HBox slider = new HBox(inputWantedTemp);
        slider.setAlignment(Pos.CENTER);

        Image imgAcc = new Image(String.valueOf(getClass().getResource("/validIcon.png")));
        ImageView imgAccView = new ImageView(imgAcc);
        Button validateBtn = new Button();
        validateBtn.setPrefSize(65, 65);
        validateBtn.setStyle("-fx-background-color:none ; -fx-border-width: 2px ; -fx-border-color:" + colorBlue + "; -fx-background-radius: 50%; -fx-opacity: 0.8; -fx-border-radius: 50%");
        validateBtn.setGraphic(imgAccView);

        validateBtn.setOnAction(e -> {
            wantedTemp = (int) inputWantedTemp.getValue();
            System.out.println("température de consigne entrée" + wantedTemp);
            this.paramsView.getParams().setWantedTemp(wantedTemp);
        });

        validateBtn.setOnMouseEntered(e ->
            validateBtn.setStyle("-fx-background-color:" + colorBlue + "; -fx-border-width: 2px ; -fx-border-color:" + colorBlue + "; -fx-background-radius: 50%; -fx-opacity: 0.8; -fx-border-radius: 50%")
        );

        validateBtn.setOnMouseExited(e ->
            validateBtn.setStyle("-fx-background-color:none ; -fx-border-width: 2px ; -fx-border-color:" + colorBlue + "; -fx-background-radius: 50%; -fx-opacity: 0.8; -fx-border-radius: 50%")
        );

        VBox SliderComponentWithLabel = new VBox(labelInputSlider, slider, validateBtn);
        SliderComponentWithLabel.setSpacing(30);
        SliderComponentWithLabel.setAlignment(Pos.CENTER);

        Label[] textLabel = {interTempLabelTitle, internHumLabelTitle, labelInputSlider, cityRegion, countryLoc};
        for (Label items : textLabel) {
            items.setFont(Font.font(fontUsed, FontWeight.NORMAL, 15));
        }

        VBox InfosWrapperBottom = new VBox(internParamsWrapper, SliderComponentWithLabel);
        InfosWrapperBottom.setSpacing(45);

        VBox Infoswrapper = new VBox(InfosWrapperTop, InfosWrapperBottom);
        Infoswrapper.setAlignment(Pos.CENTER);
        Infoswrapper.setSpacing(50);

        Infoswrapper.setPadding(new Insets(50, 0, 0, 0));
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() ->
            Platform.runLater(() -> {
                internTempLabel.setText(paramsView.getParams().getInternTemp() + "°C");
                extTempLabel.setText(paramsView.getParams().getExternTemp() + "°C");
                extHumLabel.setText(paramsView.getParams().getExternHum() + "%");
                internHumLabel.setText(paramsView.getParams().getInternHum() + "%");
                //infos.verifyDew();
            })
        , 0, 1, TimeUnit.SECONDS);
        this.getChildren().addAll(headerTitle, Infoswrapper);
    }
}
