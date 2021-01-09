package com.acsl.serialmonitorhc05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements BluetoothSerialMonitorListener {
    private Handler mHandler;
    private BluetoothSerialMonitor bluetoothSerialMonitor;
    private TextView textViewBluetoothMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewBluetoothMessage = findViewById(R.id.bluetoothResult);
        textViewBluetoothMessage.setText("Menghubungkan...");

        mHandler = new Handler();

        bluetoothSerialMonitor = new BluetoothSerialMonitor(this, mHandler,"HC-05", "=");

    }

    @Override
    public void onIncomingMessage(String message) {
        Log.d("message from hc05", message);
        textViewBluetoothMessage.setText(message);
    }
}
