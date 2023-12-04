package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class FridgeView extends Application implements Observer, IFridgeView   {
    private IFridgeParams params;
    @Override
    public void update(Observable o, Object params) {
        setParams((IFridgeParams) params);
    }
    public IFridgeParams getParams() {
        return params;
    }
    @Override
    public void setParams(IFridgeParams params) {
        this.params = params;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(new StackPane(new Text("Hello World")), 300, 300));
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
