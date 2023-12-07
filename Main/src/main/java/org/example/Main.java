package org.example;

public class Main {
    private static IFridgeModel model = new FridgeModel();

    private  static IFridgeView view = new FridgeView();

    public static void main(String[] args){
            new FridgeController(model, view ).start();
            view.main(args);
    }
}