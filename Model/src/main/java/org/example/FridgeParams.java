package org.example;

public class FridgeParams implements IFridgeParams {
    private int internTemp = 2, externTemp = 25, wantedTemp = 0;
    private double humidity = 2.5;

    public FridgeParams(){
        this.internTemp = getInternTemp();
        this.externTemp = getExternTemp();
        this.humidity = getHumidity();
    }

    public int getWantedTemp() {
        return wantedTemp;
    }
    public void setWantedTemp(int wantedTemp) {
        this.wantedTemp = wantedTemp;
    }
    public int getInternTemp() {
        return internTemp;
    }
    public void setInternTemp(int internTemp) {
        this.internTemp = internTemp;
    }
    public double getHumidity() {
        return humidity;
    }
    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }
    public void setExternTemp(int externTemp) {
        this.externTemp = externTemp;
    }
    public int getExternTemp() {
        return externTemp;
    }
}
