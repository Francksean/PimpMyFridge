package org.example;

public interface IFridgeParams {
    public float getWantedTemp();
    public void setWantedTemp(float wantedTemp);
    public float getInternTemp();
    public void setInternTemp(float internTemp);
    public float getInternHum();
    public void setInternHum(float internHum);
    public float getExternHum();
    public void setExternHum(float externHum);
    public void setExternTemp(float externTemp);
    public float getExternTemp();
}
