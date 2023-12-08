package org.example;

import com.fazecast.jSerialComm.SerialPort;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;

public class FridgeModel extends Observable implements IFridgeModel {
    private IFridgeParams params = new FridgeParams();
    private SerialPort activePort;
    private SerialPort[] ports = SerialPort.getCommPorts();

    public void datasUpdater() {
        activePort = ports[0];

        if (activePort.openPort()) {
            System.out.println(activePort.getPortDescription() + " port opened.");
        } else {
            System.out.println("Port conflict");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(activePort.getInputStream()))) {
            String line;
            Gson gson = new Gson();
            while ((line = reader.readLine()) != null) {
                System.out.println("datasReceived: " + line);

                try {
                    FridgeParams newParams = gson.fromJson(line, FridgeParams.class);
                    System.out.println("température interne: " + newParams.getInternTemp());
                    System.out.println("humidité : " + newParams.getHumidity());
                    System.out.println("température externe : " + newParams.getExternTemp());
                    setProps(newParams.getInternTemp(), newParams.getExternTemp(), newParams.getHumidity());
                    System.out.println(" ");
                } catch (Exception e) {
                    System.out.println("Error converting to JSON: " + e.getMessage());
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Re-assert interrupt status
                    System.err.println("Thread interrupted: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            activePort.closePort();
            System.out.println("Port closed.");
        }
    }

    @Override
    public void setProps(float internTemp, float externTemp, float humidity) {
        params.setInternTemp(internTemp);
        params.setExternTemp(externTemp);
        params.setHumidity(humidity);
        setChanged();
        notifyObservers(getParams());
    }

    @Override
    public void setParams(IFridgeParams params) {
        this.params = params;
    }

    public IFridgeParams getParams() {
        return params;
    }

    @Override
    public void addObserver(Observer observer) {
        super.addObserver(observer);
    }
}
