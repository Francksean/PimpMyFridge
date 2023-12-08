package org.example;

public class FridgeParams implements IFridgeParams {
    private float internTemp , externTemp , wantedTemp , internHum, externHum ;

    public float getWantedTemp() {
        return wantedTemp;
    }

    public void setWantedTemp(float wantedTemp) {
        this.wantedTemp = wantedTemp;
    }

    public float getInternTemp() {
        return internTemp;
    }

    public void setInternTemp(float internTemp) {
        this.internTemp = internTemp;
    }

    public void setExternTemp(float externTemp) {
        this.externTemp = externTemp;
    }

    public float getExternTemp() {
        return externTemp;
    }

    public float getInternHum() {
        return internHum;
    }

    public void setInternHum(float internHum) {
        this.internHum = internHum;
    }

    public float getExternHum() {
        return externHum;
    }

    public void setExternHum(float externHum) {
        this.externHum = externHum;
    }

}
