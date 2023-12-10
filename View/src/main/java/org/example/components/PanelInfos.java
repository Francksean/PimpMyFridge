package org.example.components;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
        sp.setPrefSize(100, 500);
        sp.setPadding(new Insets(20, 10, 0, 15));
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setStyle("-fx-background: none;");
        sp.setContent(notifsWrapper);

        VBox infosWrapper = new VBox(headerText, sp);
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

        double alpha = ((A_VALUE * temperature) / (B_VALUE + temperature)) + Math.log(humidity / 100.0);
        double dewPoint = (B_VALUE * alpha) / (A_VALUE - alpha);

        if (temperature <= dewPoint) {
            Label alert = createNotificationLabel("Condensation probable vers " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + ".", "pink");
            alert.setTextFill(Color.RED);
            notifsWrapper.getChildren().add(alert);
            showAlert();
        }
        if (temperature <= 5) {
            Label alert = createNotificationLabel("Votre frigo a été particulièrement froid.", "blue");
            alert.setTextFill(Color.BLUE);
            notifsWrapper.getChildren().add(alert);
        }
    }

    private Label createNotificationLabel(String text, String color) {
        Label alert = new Label(text);
        alert.setAlignment(Pos.CENTER);
        alert.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        alert.setPadding(new Insets(5));
        alert.setStyle("-fx-border-width: 1px; -fx-border-color: " + color + "; -fx-border-radius: 5px");
        return alert;
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Risque de Condensation");
        alert.setHeaderText(null);
        alert.setContentText("ATTENTION !!! RISQUE DE CONDENSATION");
        alert.showAndWait();
    }
}
