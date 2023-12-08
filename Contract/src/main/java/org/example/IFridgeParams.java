package org.example;

public interface IFridgeParams {
    public float getWantedTemp();
    public void setWantedTemp(float wantedTemp);
    public float getInternTemp();
    public void setInternTemp(float internTemp);
    public float getHumidity();
    public void setHumidity(float humidity);
    public void setExternTemp(float externTemp);
    public float getExternTemp();
}
