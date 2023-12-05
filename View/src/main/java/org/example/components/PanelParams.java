package org.example.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.example.FridgeParams;
import org.example.IFridgeParams;

import java.util.Observable;
import java.util.Observer;

public class PanelParams extends StackPane implements Observer {
    private IFridgeParams params = new FridgeParams();
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

        Label extTempLabel = new javafx.scene.control.Label(Integer.toString(this.getParams().getExternTemp()) + "°C");
        Label cityRegion = new Label("Douala, Littoral");
        Label countryLoc = new Label("Cameroon");
        VBox location = new VBox(cityRegion, countryLoc);
        location.setAlignment(Pos.CENTER);
        location.setPrefSize(90,50);
        HBox InfosWrapperTop = new HBox(extTempLabel, location);
        InfosWrapperTop.setAlignment(Pos.CENTER);
        InfosWrapperTop.setSpacing(10);

        Label interTempLabelTitle = new Label("Température interne");
        Label internTempLabel = new Label(Integer.toString(this.getParams().getInternTemp()) + "°C");
        VBox interTempBox = new VBox(interTempLabelTitle,internTempLabel);

        Label internHumLabelTitle = new Label("Humidité interne");
        Label internHumLabel = new Label(Float.toString(this.getParams().getHumidity()));
        VBox internHumBox = new VBox(internHumLabelTitle,internHumLabel);

        Label[] propsLabel = {extTempLabel, internTempLabel,internHumLabel };
        for(Label items : propsLabel){
            items.setPrefSize(50, 50);
            items.setStyle("-fx-border-width: 1px; -fx-border-color: orange; -fx-border-radius: 1em; -fx-text-alignment: center; -fx-background-color: none");
            items.setAlignment(Pos.CENTER);
        }

        VBox[] boxeFidgeParams = {interTempBox, internHumBox};
        for(VBox items : boxeFidgeParams){
            items.setAlignment(Pos.CENTER);
            items.setSpacing(10);
        }

        HBox internParamsWrapper = new HBox(interTempBox, internHumBox);
        internParamsWrapper.setSpacing(25);
        internParamsWrapper.setAlignment(Pos.CENTER);

        Slider inputWantedTemp = new Slider(0,20,0);
        inputWantedTemp.setShowTickLabels(true);
        inputWantedTemp.setShowTickMarks(true);
        inputWantedTemp.setMajorTickUnit(5);
        inputWantedTemp.setPrefWidth(200);
        inputWantedTemp.setPrefHeight(20);

        Label labelInputSlider = new Label("Température souhaitée");

        HBox SliderComponent = new HBox(labelInputSlider,inputWantedTemp);
        SliderComponent.setSpacing(10);

        VBox InfosWrapperBottom = new VBox(internParamsWrapper, SliderComponent);
        InfosWrapperBottom.setSpacing(25);

        VBox Infoswrapper = new VBox(InfosWrapperTop, InfosWrapperBottom);
        Infoswrapper.setAlignment(Pos.CENTER);
        Infoswrapper.setSpacing(50);

        this.getChildren().add(Infoswrapper);

    }
}
