package org.example;

import java.util.Observer;

public interface IFridgeModel  {
    public void setProps(float internTemp, float externTemp, float humidity);
    public void setParams(IFridgeParams params);
    public void addObserver(Observer args);
    public void datasUpdater();
}
