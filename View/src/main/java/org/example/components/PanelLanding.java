package org.example.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.example.FridgeParams;
import org.example.IFridgeParams;

import java.util.Observable;
import java.util.Observer;

public class PanelLanding extends StackPane implements Observer {

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
    public PanelLanding(){
        this.setPrefSize(500,400);
        Label extTempLabel = new javafx.scene.control.Label(Integer.toString(this.getParams().getExternTemp()) + "°C");
        Label cityRegion = new Label("Douala, Littoral");
        Label countryLoc = new Label("Cameroon");
        VBox location = new VBox(cityRegion, countryLoc);
        VBox landingInfosWrapper = new VBox(extTempLabel, location);
        this.getChildren().addAll(landingInfosWrapper);
    }
}
