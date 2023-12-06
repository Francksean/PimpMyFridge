package org.example;


import javafx.application.Platform;
import org.example.components.PanelGraphics;
import org.example.components.PanelInfos;
import org.example.components.PanelLanding;
import org.example.components.PanelParams;

public class Main {
    private static IFridgeModel model = new FridgeModel();

    private  static IFridgeView view = new FridgeView();

    public static void main(String[] args){
            new FridgeController(model, view ).start();
            view.main(args);


    }
}