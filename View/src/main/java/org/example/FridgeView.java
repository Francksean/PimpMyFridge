package org.example;

import java.util.Observable;
import java.util.Observer;


public class FridgeView implements Observer, IFridgeView {
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

}
