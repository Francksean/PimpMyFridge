package org.example;

public interface IFridgeController {
    void start();
    void setModel(IFridgeModel model);
    void setView(IFridgeView view);
    IFridgeModel getModel();
    IFridgeView getView();
}

