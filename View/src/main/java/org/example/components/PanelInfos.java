package org.example.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.IFridgeParams;

import java.util.Observable;
import java.util.Observer;

public class PanelInfos extends StackPane implements Observer {
    private IFridgeParams params;
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

    String fontUsed = "Roboto";


    public PanelInfos(){
        this.setPrefSize(500,400);
        Label headerTitle = new Label("FRIDGY");
        headerTitle.setFont(Font.font(fontUsed, FontWeight.BOLD, 25));
        headerTitle.setPrefSize(500, 70);
        headerTitle.setAlignment(Pos.CENTER);
        headerTitle.setStyle("-fx-background-color: #2fb6ee");
        Label headerText = new Label("Ceci est votre centre de notification, vous serez notifiez de différents évenements concernat votre frigo.");
        headerText.setPrefWidth(300);
        headerText.setAlignment(Pos.CENTER);
        headerText.setWrapText(true);
        headerText.setFont(Font.font(fontUsed,FontWeight.NORMAL, 15));
        VBox header = new VBox(headerTitle,headerText);
        header.setSpacing(20);
        header.setAlignment(Pos.TOP_CENTER);

        this.getChildren().add(headerText);
    }


}
