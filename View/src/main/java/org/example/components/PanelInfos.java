package org.example.components;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.FridgeView;
import org.example.IPanelInfos;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PanelInfos extends StackPane implements IPanelInfos {

    private final String colorBlue = "#2fb6ee";

    private static final double A_VALUE = 17.27;
    private static final double B_VALUE = 237.7;

    private final FridgeView infosView;
    private final VBox notifsWrapper;

    public PanelInfos(FridgeView view) {
        this.infosView = view;
        this.setPrefSize(500, 400);

        Label headerTitle = new Label("FRIDGY");
        headerTitle.setFont(Font.font("Roboto", FontWeight.BOLD, 25));
        headerTitle.setPrefSize(500, 70);
        headerTitle.setAlignment(Pos.CENTER);
        headerTitle.setStyle("-fx-background-color: #2fb6ee");

        Label headerText = new Label("Ceci est votre centre de notification, vous serez notifié de différents événements concernant votre frigo.");
        headerText.setPrefWidth(300);
        headerText.setAlignment(Pos.CENTER);
        headerText.setWrapText(true);
        headerText.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));

        VBox header = new VBox(headerTitle, headerText);
        header.setSpacing(20);
        header.setAlignment(Pos.TOP_CENTER);

        notifsWrapper = new VBox();
        notifsWrapper.setSpacing(5);
        notifsWrapper.setAlignment(Pos.CENTER);

        ScrollPane sp = new ScrollPane();
        sp.setPrefSize(100, 400);
        sp.setPadding(new Insets(20, 10, 0, 50));
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setStyle("-fx-background: none;");
        sp.setContent(notifsWrapper);

        Image imgDel = new Image(String.valueOf(getClass().getResource("/deletIcon.png")));
        ImageView imgDelView = new ImageView(imgDel);
        Button resetBtn = new Button();
        resetBtn.setGraphic(imgDelView);
        resetBtn.setPrefSize(60, 60);
        resetBtn.setStyle("-fx-background-color:none ; -fx-border-width: 1px ; -fx-border-color:black ; -fx-background-radius: 2em; -fx-opacity: 0.8; -fx-border-radius: 50%");
        resetBtn.setOnAction(e->resetNotifs());

        VBox spResetContainer = new VBox(sp, resetBtn);
        spResetContainer.setAlignment(Pos.CENTER);
        spResetContainer.setSpacing(35);

        VBox infosWrapper = new VBox(headerText, spResetContainer);
        infosWrapper.setAlignment(Pos.TOP_CENTER);
        infosWrapper.setSpacing(25);
        infosWrapper.setPadding(new Insets(25, 0, 0, 0));

        this.getChildren().add(infosWrapper);

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(this::checkNotifications, 0, 1, TimeUnit.SECONDS);
    }

    private void checkNotifications() {
        Platform.runLater(this::verifyDew);
    }

    public void verifyDew() {
        float temperature = infosView.getParams().getInternTemp();
        float humidity = infosView.getParams().getInternHum();
        float temperatureExt = infosView.getParams().getExternTemp();

        double alpha = ((A_VALUE * temperature) / (B_VALUE + temperature)) + Math.log(humidity / 100.0);
        double dewPoint = (B_VALUE * alpha) / (A_VALUE - alpha);

        if (temperature <= dewPoint) {
            Label alert = createNotificationLabel("Condensation probable vers " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + ".", "pink");
            alert.setTextFill(Color.RED);
            notifsWrapper.getChildren().add(alert);
            showAlert("ATTENTION !!! RISQUE DE CONDENSATION");
        }
        if (temperature <= 25) {
            Label alert = createNotificationLabel("Votre frigo a été particulièrement froid.", colorBlue);
            alert.setTextFill(Color.web(colorBlue));
            notifsWrapper.getChildren().add(alert);
        }

        /*pose porblème au lancement de l'application*/

        /*if (temperature == temperatureExt ) {
            Label alert = createNotificationLabel("Votre frigo s'est ouvert vers " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))+ ".", "pink");
            alert.setTextFill(Color.RED);
            notifsWrapper.getChildren().add(alert);
            showAlert("VOTRE FRIGO S'EST OUVERT !!!");
        }*/
    }

    private void resetNotifs(){
        notifsWrapper.getChildren().clear();
    }

    private Label createNotificationLabel(String text, String color) {
        Label alert = new Label(text);
        alert.setAlignment(Pos.CENTER);
        alert.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        alert.setPadding(new Insets(5));
        alert.setStyle("-fx-border-width: 1px; -fx-border-color: " + color + "; -fx-border-radius: 5px");
        return alert;
    }

    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Risque de Condensation");
        alert.setHeaderText(null);
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }
}
