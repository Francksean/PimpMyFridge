package org.example;

import com.fazecast.jSerialComm.SerialPort;
import com.google.gson.Gson;

import java.io.*;
import java.util.Observable;
import java.util.Observer;

public class FridgeModel extends Observable implements IFridgeModel {
    private IFridgeParams params = new FridgeParams();
    private final SerialPort[] ports = SerialPort.getCommPorts();
    private final SerialPort activePort = ports[0];

    public FridgeModel(){
        activePort.openPort();
    }


    public void datasUpdater() {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(activePort.getInputStream()))) {
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

    public void sendTemperatureToSerial(float consigne) {
        System.out.println("\nwsssssssssssssshhhhhhhhhhhhhhh\n");
        /*BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(activePort.getOutputStream()));

        System.out.println("entree");
        try {
            writer.write(String.valueOf(consigne));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

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
