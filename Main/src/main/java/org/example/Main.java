package org.example;

public class Main {
    public static void main(String[] args){
        IFridgeParams params = new FridgeParams();
        IFridgeModel model = new FridgeModel(params);
        IFridgeView view = new FridgeView(model, params);
        new FridgeController(model, view ).start();
        view.main(args);
    }
}