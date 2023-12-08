package org.example;

public class FridgeParams implements IFridgeParams {
    private float internTemp , externTemp , wantedTemp , humidity ;

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

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    // Corrected setExternTemp method with parameter
    public void setExternTemp(float externTemp) {
        this.externTemp = externTemp;
    }

    public float getExternTemp() {
        return externTemp;
    }
}
