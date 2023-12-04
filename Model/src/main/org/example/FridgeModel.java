package Model.src.main.org.example;

import java.util.Observable;

public class FridgeModel extends Observable implements IFridgeModel {
    FridgeParams params;
    @Override
    public void setProps(int internTemp, int externTemp, float humidity) {
        this.params.setInternTemp(internTemp);
        this.params.setExternTemp(externTemp);
        this.params.setHumidity(humidity);
        setChanged();
        notifyObservers(params);
    }

    public void setParams(FridgeParams params) {
        this.params = params;
    }

    public FridgeParams getParams() {
        return params;
    }
}