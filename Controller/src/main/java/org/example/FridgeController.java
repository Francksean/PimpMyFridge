package org.example;

import javafx.application.Platform;

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
        Thread thread = new Thread(()->{
            for (int i = 0; i < 200; i++) {
                model.setProps(model.getParams().getInternTemp() + 1,model.getParams().getExternTemp() + 1,model.getParams().getHumidity() + 1);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
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
