package org.example;

import java.util.Observable;
import java.util.Observer;

public class FridgeModel extends Observable implements IFridgeModel {
    private IFridgeParams params = new FridgeParams();

    @Override
    public void setProps(int internTemp, int externTemp, float humidity) {
        this.params.setInternTemp(internTemp);
        this.params.setExternTemp(externTemp);
        this.params.setHumidity(humidity);
        setChanged();
        notifyObservers(getParams());
    }

    @Override
    public void setParams(IFridgeParams params) {
        this.params = params;
    }

    public IFridgeParams getParams() {
        return params;
    }

    @Override
    public void addObserver(Observer observer){
        super.addObserver(observer);
    }
}