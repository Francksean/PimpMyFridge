package org.example.components;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.chart.*;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.example.FridgeParams;
import org.example.FridgeView;
import org.example.IFridgeParams;
import org.example.IPanelGraphics;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class PanelGraphics extends StackPane implements IPanelGraphics {
    private static final int WINDOW_SIZE = 6 ;
    private static CategoryAxis xAxis = new CategoryAxis();
    private static NumberAxis yAxis = new NumberAxis();

    final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    private IFridgeParams params = new FridgeParams();
    public IFridgeParams getParams() {
        return params;
    }
    public void setParams(IFridgeParams params) {
        this.params = params;
    }

    private StackPane rootChart = new StackPane();

    String colorBlue = "#2fb6ee";

    public PanelGraphics(){

        this.setPrefSize(500,400);

        rootChart.getChildren().add(TempsChart());
        //bouton pour lancer le graphe des températres
        Button tempChartBtn = new Button("températures");
        tempChartBtn.setOnAction(e ->{
            switchCharts(TempsChart());
        });


        Button humChartBtn = new Button("Humidité");
        humChartBtn.setOnAction(e ->{
            switchCharts(HumChart());
        });

        HBox btnBox = new HBox(tempChartBtn, humChartBtn);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setSpacing(25);

        Button[] btnCharts = {tempChartBtn, humChartBtn};

        for(Button intems : btnCharts){
            intems.setStyle("-fx-background-color: none; -fx-border-width: 1px; -fx-border-radius: 1em; -fx-border-color:"+ colorBlue+" ");
        }

        VBox chartDisplayer = new VBox(btnBox, rootChart);
        chartDisplayer.setAlignment(Pos.CENTER);
        chartDisplayer.setSpacing(25);

        this.getChildren().add(chartDisplayer);

    }

    private StackPane TempsChart(){
        xAxis.setLabel("temps");
        xAxis.setAnimated(false);
        yAxis.setLabel("Valeur (en °C)");
        yAxis.setAnimated(false);

        final AreaChart<String, Number> areaChart = new AreaChart<>(xAxis, yAxis);
        areaChart.setTitle("graphique des températures");
        areaChart.setAnimated(false);

        XYChart.Series<String, Number> intTempSeries = new XYChart.Series<>();
        intTempSeries.setName("Temp. int.");

        XYChart.Series<String, Number> extTempSeries = new XYChart.Series<>();
        extTempSeries.setName("Temp. ext.");
        areaChart.getData().add(intTempSeries);
        areaChart.getData().add(extTempSeries);

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            Platform.runLater(()->{
                Date now = new Date();
                int random = ThreadLocalRandom.current().nextInt(5);
                intTempSeries.getData().add(new XYChart.Data<>(dateFormat.format(now), new FridgeView().getParams().getInternTemp()));
                if (intTempSeries.getData().size() > WINDOW_SIZE)
                    intTempSeries.getData().remove(0);
            });
        }, 0, 1, TimeUnit.SECONDS);

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            Platform.runLater(()->{
                Date now = new Date();
                int random = ThreadLocalRandom.current().nextInt(25, 30);
                extTempSeries.getData().add(new XYChart.Data<>(dateFormat.format(now), new FridgeView().getParams().getExternTemp()));
                if (extTempSeries.getData().size() > WINDOW_SIZE)
                    extTempSeries.getData().remove(0);
            });
    }, 0, 1, TimeUnit.SECONDS);
        return new StackPane(areaChart);
    }

    private StackPane HumChart(){
        xAxis.setLabel("temps");
        xAxis.setAnimated(false);
        yAxis.setLabel("Valeurs (en %)");
        yAxis.setAnimated(false);

        final AreaChart<String, Number> areaChart = new AreaChart<>(xAxis, yAxis);
        areaChart.setTitle("graphique de l'humidité");
        areaChart.setAnimated(false);

        XYChart.Series<String, Number> humSeries = new XYChart.Series<>();
        humSeries.setName("Humidité");

        areaChart.getData().add(humSeries);
        areaChart.setStyle("-fx-stroke-line-join: round");



        scheduledExecutorService.scheduleAtFixedRate(() -> {
            Platform.runLater(()->{
                Date now = new Date();
                int random = ThreadLocalRandom.current().nextInt(20, 25);

                humSeries.getData().add(new XYChart.Data<>(dateFormat.format(now), new FridgeView().getParams().getHumidity()));

                if (humSeries.getData().size() > WINDOW_SIZE)
                    humSeries.getData().remove(0);
            });
        }, 0, 1, TimeUnit.SECONDS);
        return new StackPane(areaChart);
    }

    private void switchCharts(StackPane pane){
        rootChart.getChildren().clear();
        rootChart.getChildren().add(pane);
        rootChart.setAlignment(Pos.CENTER);
    }
}
