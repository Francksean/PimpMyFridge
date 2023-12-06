package org.example;

import java.util.Observer;

public class FridgeController implements IFridgeController{

    private IFridgeModel model;
    private IFridgeView view;

    public FridgeController(IFridgeModel model, IFridgeView view){
        this.model = model;
        this.view = view;
        model.addObserver((Observer)view);
    }

    public void start() {
        view.main();
    }

    public void setModel(IFridgeModel model) {
        this.model = model;
    }
    public void setView(IFridgeView view) {
        this.view = view;
    }

    public IFridgeModel getModel() {
        return model;
    }
    public IFridgeView getView() {
        return view;
    }
}
