package org.example.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.FridgeParams;
import org.example.IFridgeParams;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class PanelLanding extends StackPane implements Observer {

    private IFridgeParams params = new FridgeParams();
    String fontUsed = "Roboto";

    Date date = new Date();

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

        Label headerTitle = new Label("FRIDGY");
        headerTitle.setFont(Font.font(fontUsed,FontWeight.BOLD, 25));
        headerTitle.setPrefSize(500, 70);
        headerTitle.setAlignment(Pos.CENTER);
        headerTitle.setStyle("-fx-background-color: #2fb6ee");
        Label headerText = new Label("Votre application de monitoring... Soyez au courant de l'état de votre frigo en temps reél.");
        headerText.setPrefWidth(300);
        headerText.setAlignment(Pos.CENTER);
        headerText.setWrapText(true);
        headerText.setFont(Font.font(fontUsed,FontWeight.NORMAL, 15));


        VBox header = new VBox(headerTitle,headerText);
        header.setSpacing(20);
        header.setAlignment(Pos.TOP_CENTER);

        Label extTempLabel = new javafx.scene.control.Label(Integer.toString(this.getParams().getExternTemp()) + "°C");
        extTempLabel.setPrefSize(100,100);
        extTempLabel.setAlignment(Pos.CENTER);
        extTempLabel.setFont(Font.font(fontUsed,FontWeight.NORMAL, 25));
        extTempLabel.setStyle("-fx-border-width: 0px; -fx-border-color: none; -fx-border-radius: 1em; -fx-text-alignment: center; -fx-background-color: none");
        Label cityRegion = new Label("Douala, Littoral");
        Label countryLoc = new Label("Cameroun");
        VBox location = new VBox(cityRegion, countryLoc);
        location.setAlignment(Pos.CENTER);
        cityRegion.setFont(Font.font(fontUsed,FontWeight.NORMAL, 15));
        countryLoc.setFont(Font.font(fontUsed,FontWeight.NORMAL, 15));

        Label currentTime = new Label(java.time.LocalDate.now().toString());
        currentTime.setFont(Font.font(fontUsed,FontWeight.NORMAL, 15));

        VBox landingInfosWrapper = new VBox(extTempLabel, location, currentTime);
        landingInfosWrapper.setSpacing(15);
        landingInfosWrapper.setAlignment(Pos.CENTER);

        setAlignment(header, Pos.TOP_CENTER);
        this.getChildren().addAll(header, landingInfosWrapper);
    }
}
