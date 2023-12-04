package org.example;

public interface IFridgeParams {
    public int getWantedTemp();
    public void setWantedTemp(int wantedTemp);
    public int getInternTemp();
    public void setInternTemp(int internTemp);
    public float getHumidity();
    public void setHumidity(float humidity);
    public void setExternTemp(int externTemp);
    public int getExternTemp();
}
