package Model.src.main.org.example;

public class FridgeParams implements IFridgeParams {
    private int internTemp, externTemp, wantedTemp;
    private float humidity;

    public int getWantedTemp() {
        return wantedTemp;
    }
    public void setWantedTemp(int wantedTemp) {
        this.wantedTemp = wantedTemp;
    }
    public int getInternTemp() {
        return internTemp;
    }
    public void setInternTemp(int internTemp) {
        this.internTemp = internTemp;
    }
    public float getHumidity() {
        return humidity;
    }
    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }
    public void setExternTemp(int externTemp) {
        this.externTemp = externTemp;
    }
    public int getExternTemp() {
        return externTemp;
    }
}
