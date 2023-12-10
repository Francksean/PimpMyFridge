package org.example;

public class Main {
    public static void main(String[] args){
        IFridgeModel model = new FridgeModel();
        IFridgeView view = new FridgeView(model);
        new FridgeController(model, view ).start();
        view.main(args);
    }
}