package org.example.components;

import javafx.scene.layout.StackPane;
import org.example.IFridgeParams;

import java.util.Observable;
import java.util.Observer;

public class PanelGraphics extends StackPane implements Observer {
    private IFridgeParams params;
    @Override
    public void update(Observable o, Object params) {
        setParams((IFridgeParams) params);
    }
    public IFridgeParams getParams() {
        return params;
    }
    public void setParams(IFridgeParams params) {
        this.params = params;
    }

    public PanelGraphics(){
        this.setPrefSize(500,400);
    }

}
