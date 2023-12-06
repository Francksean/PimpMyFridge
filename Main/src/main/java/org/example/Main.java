package org.example;


import org.example.components.PanelGraphics;
import org.example.components.PanelLanding;
import org.example.components.PanelParams;

public class Main {
    private static IFridgeModel model = new FridgeModel();

    private  static IFridgeView view = new FridgeView();
    public static void main(String[] args){
        FridgeController controller = new FridgeController(model, view );
        controller.start();
    }
}