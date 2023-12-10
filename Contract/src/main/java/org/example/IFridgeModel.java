package org.example;

import java.util.Observer;

public interface IFridgeModel  {
    public void setProps(float internTemp, float externTemp, float interHum, float externHum);
    public void setParams(IFridgeParams params);
    public void addObserver(Observer args);
    public void datasUpdater();
    public void sendTemperatureToSerial(float consigne);
}
