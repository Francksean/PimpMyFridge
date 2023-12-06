package org.example;

public interface IFridgeParams {
    public int getWantedTemp();
    public void setWantedTemp(int wantedTemp);
    public int getInternTemp();
    public void setInternTemp(int internTemp);
    public double getHumidity();
    public void setHumidity(double humidity);
    public void setExternTemp(int externTemp);
    public int getExternTemp();
}
