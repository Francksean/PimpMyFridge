package org.example;

import java.util.Observer;

public interface IFridgeModel  {
    public void setProps(int internTemp, int externTemp, float humidity);
    public void setParams(IFridgeParams params);
    public IFridgeParams getParams();
    public void addObserver(Observer args);
}