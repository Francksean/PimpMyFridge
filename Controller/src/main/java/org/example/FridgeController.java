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
        Thread thread = new Thread(()->{
            while(this.view.isRunning()){
                model.datasUpdater();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                view.isRunning();
                System.out.println(this.view.isRunning());
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
