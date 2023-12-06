package org.example.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import org.example.FridgeParams;
import org.example.IFridgeParams;

import java.util.Observable;
import java.util.Observer;

public class PanelParams extends StackPane implements Observer {
    private IFridgeParams params = new FridgeParams();

    String colorBlue = "#2fb6ee";

    String fontUsed = "Roboto";

    @Override
    public void update(Observable o, Object params) {
        setParams((IFridgeParams) params);
    }
    public IFridgeParams getParams() {
        return params;
    }
    public void setParams(IFridgeParams params) {
        this.params = params;
    }

    public PanelParams(){
        this.setPrefSize(500,400);

        Label headerTitle = new Label("VOTRE FRIGO");
        headerTitle.setFont(Font.font(fontUsed, FontWeight.BOLD, 25));
        headerTitle.setPrefSize(500, 70);
        headerTitle.setAlignment(Pos.CENTER);
        headerTitle.setStyle("-fx-background-color: #2fb6ee");
        setAlignment(headerTitle, Pos.TOP_CENTER);

        Label extTempLabel = new javafx.scene.control.Label(Integer.toString(this.getParams().getExternTemp()) + "°C");
        extTempLabel.setPrefSize(100, 100);
        extTempLabel.setAlignment(Pos.CENTER);
        extTempLabel.setFont(Font.font(fontUsed,FontWeight.NORMAL, 25));
        Label cityRegion = new Label("Douala, Littoral");
        Label countryLoc = new Label("Cameroun");
        VBox location = new VBox(cityRegion, countryLoc);
        location.setAlignment(Pos.CENTER);
        location.setPrefSize(120,50);
        HBox InfosWrapperTop = new HBox(extTempLabel, location);
        InfosWrapperTop.setAlignment(Pos.CENTER);
        InfosWrapperTop.setSpacing(40);

        Label interTempLabelTitle = new Label("Température interne");
        Label internTempLabel = new Label(Integer.toString(this.getParams().getInternTemp()) + "°C");
        VBox interTempBox = new VBox(interTempLabelTitle,internTempLabel);

        Label internHumLabelTitle = new Label("Humidité interne");
        Label internHumLabel = new Label(Double.toString(this.getParams().getHumidity()) + "%");
        VBox internHumBox = new VBox(internHumLabelTitle,internHumLabel);

        Label[] propsLabel = {internTempLabel,internHumLabel };
        for(Label items : propsLabel){
            items.setPrefSize(100, 100);
            items.setStyle("-fx-border-width: 1px; -fx-border-color:" + colorBlue +"; -fx-border-radius: 1em; -fx-text-alignment: center; -fx-background-color: none;");
            items.setAlignment(Pos.CENTER);
            items.setFont(Font.font(fontUsed,FontWeight.NORMAL, 15));
        }


        VBox[] boxeFidgeParams = {interTempBox, internHumBox};
        for(VBox items : boxeFidgeParams){
            items.setAlignment(Pos.CENTER);
            items.setSpacing(10);
        }

        HBox internParamsWrapper = new HBox(interTempBox, internHumBox);
        internParamsWrapper.setSpacing(50);
        internParamsWrapper.setAlignment(Pos.CENTER);



        Label labelInputSlider = new Label("Température de consigne");

        Slider inputWantedTemp = new Slider(0,20,0);
        inputWantedTemp.setShowTickLabels(true);
        inputWantedTemp.setShowTickMarks(true);
        inputWantedTemp.setMajorTickUnit(5);
        inputWantedTemp.setPrefSize(250,50);
        inputWantedTemp.setStyle(
                "-fx-control-inner-background: none ; " +
                "-fx-background-color: transparent;");

        HBox slider = new HBox(inputWantedTemp);
        slider.setAlignment(Pos.CENTER);

        Image imgAcc = new Image(getClass().getResource("/validIcon.png").toExternalForm());
        ImageView imgAccView = new ImageView(imgAcc);
        Button validateBtn = new Button();
        validateBtn.setPrefSize(65,65);
        validateBtn.setStyle("-fx-background-color:none ; -fx-border-width: 2px ; -fx-border-color:" + colorBlue + "; -fx-background-radius: 50%; -fx-opacity: 0.8; -fx-border-radius: 50%");
        validateBtn.setGraphic(imgAccView);

        validateBtn.setOnAction(e->{
            params.setWantedTemp((int)inputWantedTemp.getValue());
            System.out.println(params.getWantedTemp());
        });

        validateBtn.setOnMouseEntered(e -> {
            validateBtn.setStyle("-fx-background-color:"+ colorBlue +"; -fx-border-width: 2px ; -fx-border-color:" + colorBlue + "; -fx-background-radius: 50%; -fx-opacity: 0.8; -fx-border-radius: 50%");
        });

        validateBtn.setOnMouseExited(e -> {
            validateBtn.setStyle("-fx-background-color:none ; -fx-border-width: 2px ; -fx-border-color:" + colorBlue + "; -fx-background-radius: 50%; -fx-opacity: 0.8; -fx-border-radius: 50%");
        });


        VBox SliderComponentWithLabel = new VBox(labelInputSlider,slider,validateBtn);
        SliderComponentWithLabel.setSpacing(30);
        SliderComponentWithLabel.setAlignment(Pos.CENTER);

        Label[] textLabel = {interTempLabelTitle,internHumLabelTitle, labelInputSlider, cityRegion, countryLoc};
        for(Label items : textLabel){
            items.setFont(Font.font(fontUsed,FontWeight.NORMAL, 15));
        }

        VBox InfosWrapperBottom = new VBox(internParamsWrapper, SliderComponentWithLabel);
        InfosWrapperBottom.setSpacing(45);

        VBox Infoswrapper = new VBox(InfosWrapperTop, InfosWrapperBottom);
        Infoswrapper.setAlignment(Pos.CENTER);
        Infoswrapper.setSpacing(50);

        Infoswrapper.setPadding(new Insets(50,0,0,0));
        this.getChildren().addAll(headerTitle, Infoswrapper);

    }
}
