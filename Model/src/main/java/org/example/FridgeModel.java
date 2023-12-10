package org.example;

import com.fazecast.jSerialComm.SerialPort;
import com.google.gson.Gson;

import java.io.*;
import java.util.Observable;
import java.util.Observer;

public class FridgeModel extends Observable implements IFridgeModel {
    private IFridgeParams params;
    private final SerialPort[] ports = SerialPort.getCommPorts();
    private final SerialPort activePort = ports[0];

    public FridgeModel( IFridgeParams paramss){
        activePort.openPort();
        params = paramss;
    }


    public void datasUpdater() {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(activePort.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(activePort.getOutputStream()))) {
            String line;
            Gson gson = new Gson();
            while ((line = reader.readLine()) != null) {
                System.out.println("datasReceived: " + line);

                try {
                    FridgeParams newParams = gson.fromJson(line, FridgeParams.class);
                    System.out.println("température interne: " + newParams.getInternTemp());
                    System.out.println("température externe : " + newParams.getExternTemp());
                    System.out.println("humidité interne : " + newParams.getInternHum());
                    System.out.println("humidité externe : " + newParams.getExternHum());
                    System.out.println("valeur de consigne : " + newParams.getWantedTemp());
                    writer.write(String.valueOf(params.getWantedTemp()));
                    writer.newLine();
                    writer.flush();
                    setProps(newParams.getInternTemp(), newParams.getExternTemp(), newParams.getInternHum(), newParams.getExternHum());
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
        }
    }



    @Override
    public void setProps(float internTemp, float externTemp, float internHum, float externHum) {
        params.setInternTemp(internTemp);
        params.setExternTemp(externTemp);
        params.setInternHum(internHum);
        params.setExternHum(externHum);
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
