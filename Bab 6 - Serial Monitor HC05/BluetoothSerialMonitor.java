package com.acsl.serialmonitorhc05;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public class BluetoothSerialMonitor {

    private boolean firstAttempt = false;
    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothSocket mBluetoothSocket = null;
    private static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private BluetoothSerialMonitorListener blueListener;
    private Handler activityHandler;
    private String deviceName;
    private String delimiter;
    private String message = "";

    public BluetoothSerialMonitor(BluetoothSerialMonitorListener blueListener,
                                  Handler handler,
                                  String deviceName,
                                  String delimiter) {
        this.blueListener = blueListener;
        this.activityHandler = handler;
        this.delimiter = delimiter;
        this.deviceName = deviceName;


        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null) {
            postToBlueListener("Module Bluetooth tidak ditemukan!");
        }
        else {
            if(mBluetoothAdapter.isEnabled()){
                    new Thread(new discoverAndConnect()).start();
            }else{
                postToBlueListener("Aktifkan Bluetooth anda terlebih dahulu!");
            }
        }
    }

    private void postToBlueListener(String msg){
        this.message = msg;
        activityHandler.post(new Runnable() {
            @Override
            public void run() {
                blueListener.onIncomingMessage(message);
                message = "";
            }
        });
    }

    public boolean available(){
        boolean isAvailable = false;
        int n = 0;
        try{
            n = mBluetoothSocket.getInputStream().available();

        }catch (IOException e) { }

        if (n > 0) isAvailable = true;
        return isAvailable;
    }

    public int read(){
        int i = -1;
        try{
            i = mBluetoothSocket.getInputStream().read();
        }catch (IOException e) { }

        return i;
    }

    private void connectToDevice(){
        boolean devicesFoundStatus = false;
        boolean connected = false;
        Set<BluetoothDevice> listPairedDevices = mBluetoothAdapter.getBondedDevices();
        if(listPairedDevices.size() > 0){

            for(BluetoothDevice bt : listPairedDevices){

                if(bt.getName().equals(deviceName)){
                    devicesFoundStatus = true;
                    try {
                        if (mBluetoothSocket == null || !connected) {
                            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(bt.getAddress());
                            mBluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(myUUID);
                            BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                            mBluetoothSocket.connect();

                            connected = true;
                        }
                    } catch (IOException e){
                        if(!firstAttempt){
                            postToBlueListener("Inisialisasi RFCOMM gagal! reconnecting...");
                            firstAttempt = true;
                        }
                        connectToDevice();
                    }

                    break;
                }
            }

            if(!devicesFoundStatus){
                postToBlueListener("Lakukan pairing dengan perangkat "+deviceName+" terlebih dahulu!");
            }else if(devicesFoundStatus && connected){
                postToBlueListener("Koneksi Berhasil! mulailah mengirim pesan!");
                getSerialMessage();
            }
        }
    }

    public void getSerialMessage(){
        while (true){
            if(available()){
                int incomingMessage = read();
                String incomingMessageAsString = Character.toString((char) incomingMessage);

                if(incomingMessageAsString.equals(delimiter)){
                    postToBlueListener(message);
                }else{
                    message += incomingMessageAsString;
                }
            }
        }
    }

    private class discoverAndConnect implements Runnable {
        @Override
        public void run() {
            connectToDevice();
        }
    }
}

