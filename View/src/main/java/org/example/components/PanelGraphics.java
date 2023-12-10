package org.example.components;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.example.FridgeView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PanelGraphics extends StackPane {

    private static final int WINDOW_SIZE = 12;
    private final CategoryAxis tempXAxis = new CategoryAxis();
    private final CategoryAxis humXAxis = new CategoryAxis();
    private final NumberAxis tempYAxis = new NumberAxis();
    private final NumberAxis humYAxis = new NumberAxis();

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    private final StackPane rootChart = new StackPane();
    private final FridgeView graphView;

    private final StackPane humChart;
    private final StackPane tempChart;

    private XYChart.Series<String, Number> internHumSeries;
    private XYChart.Series<String, Number> externHumSeries;
    private XYChart.Series<String, Number> intTempSeries;
    private XYChart.Series<String, Number> extTempSeries;

    public PanelGraphics(FridgeView view) {
        this.graphView = view;
        this.setPrefSize(500, 400);
        this.humChart = createHumChart();
        this.tempChart = createTempChart();

        rootChart.getChildren().add(tempChart);

        Button tempChartBtn = new Button("Températures");
        tempChartBtn.setOnAction(e -> switchCharts(tempChart));

        Button humChartBtn = new Button("Humidité");
        humChartBtn.setOnAction(e -> switchCharts(humChart));

        HBox btnBox = new HBox(tempChartBtn, humChartBtn);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setSpacing(25);

        Button[] btnCharts = {tempChartBtn, humChartBtn};

        for (Button button : btnCharts) {
            String colorBlue = "#2fb6ee";
            button.setStyle("-fx-background-color: none; -fx-border-width: 1px; -fx-border-radius: 1em; -fx-border-color:" + colorBlue + " ");
            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: none; -fx-border-width: 1px; -fx-border-radius: 1em; -fx-border-color:" + colorBlue + "; -fx-translate-y: -5px"));
            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: none; -fx-border-width: 1px; -fx-border-radius: 1em; -fx-border-color:" + colorBlue + "; -fx-translate-y: 0px"));
        }

        VBox chartDisplayer = new VBox(btnBox, rootChart);
        chartDisplayer.setAlignment(Pos.CENTER);
        chartDisplayer.setSpacing(25);

        this.getChildren().add(chartDisplayer);
    }

    private StackPane createTempChart() {
        tempXAxis.setLabel("Temps");
        tempXAxis.setAnimated(false);
        tempYAxis.setLabel("Valeur (en °C)");
        tempYAxis.setAnimated(false);

        final AreaChart<String, Number> areaChart = new AreaChart<>(tempXAxis, tempYAxis);
        areaChart.setTitle("Graphique des températures");
        areaChart.setAnimated(false);

        intTempSeries = new XYChart.Series<>();
        intTempSeries.setName("Temp. int.");

        extTempSeries = new XYChart.Series<>();
        extTempSeries.setName("Temp. ext.");

        areaChart.getData().add(intTempSeries);
        areaChart.getData().add(extTempSeries);

        scheduledExecutorService.scheduleAtFixedRate(() ->
            Platform.runLater(() -> {
                Date now = new Date();
                intTempSeries.getData().add(new XYChart.Data<>(dateFormat.format(now), graphView.getParams().getInternTemp()));
                if (intTempSeries.getData().size() > WINDOW_SIZE)
                    intTempSeries.getData().remove(0);
            })
        , 0, 1, TimeUnit.SECONDS);

        scheduledExecutorService.scheduleAtFixedRate(() ->
            Platform.runLater(() -> {
                Date now = new Date();
                extTempSeries.getData().add(new XYChart.Data<>(dateFormat.format(now), graphView.getParams().getExternTemp()));
                if (extTempSeries.getData().size() > WINDOW_SIZE)
                    extTempSeries.getData().remove(0);
            })
        , 0, 1, TimeUnit.SECONDS);

        return new StackPane(areaChart);
    }

    private StackPane createHumChart() {
        humXAxis.setLabel("Temps");
        humXAxis.setAnimated(false);
        humYAxis.setLabel("Valeur (en %)");
        humYAxis.setAnimated(false);

        final AreaChart<String, Number> areaChart = new AreaChart<>(humXAxis, humYAxis);
        areaChart.setTitle("Graphique de l'humidité");
        areaChart.setAnimated(false);

        internHumSeries = new XYChart.Series<>();
        internHumSeries.setName("Hum. int.");

        externHumSeries = new XYChart.Series<>();
        externHumSeries.setName("Hum. ext.");

        areaChart.getData().add(internHumSeries);
        areaChart.getData().add(externHumSeries);

        areaChart.setStyle("-fx-stroke-line-join: round");

        scheduledExecutorService.scheduleAtFixedRate(() ->
            Platform.runLater(() -> {
                Date now = new Date();

                internHumSeries.getData().add(new XYChart.Data<>(dateFormat.format(now), graphView.getParams().getInternHum()));

                if (internHumSeries.getData().size() > WINDOW_SIZE)
                    internHumSeries.getData().remove(0);
            })
        , 0, 1, TimeUnit.SECONDS);

        scheduledExecutorService.scheduleAtFixedRate(() ->
            Platform.runLater(() -> {
                Date now = new Date();

                externHumSeries.getData().add(new XYChart.Data<>(dateFormat.format(now), graphView.getParams().getExternHum()));

                if (externHumSeries.getData().size() > WINDOW_SIZE)
                    externHumSeries.getData().remove(0);
            })
        , 0, 1, TimeUnit.SECONDS);

        return new StackPane(areaChart);
    }

    private void switchCharts(StackPane pane) {
        rootChart.getChildren().clear();
        rootChart.getChildren().add(pane);
        rootChart.setAlignment(Pos.CENTER);
    }
}

