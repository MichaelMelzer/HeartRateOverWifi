package io.michimpunkt.heartrateoverwifi;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import io.michimpunkt.heartrateoverwifi.databinding.ActivityReadHeartRateBinding;

public class ReadHeartRateActivity extends Activity implements SensorEventListener {

    private TextView mTextView;
    private ActivityReadHeartRateBinding binding;
    private HttpServer httpServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityReadHeartRateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        sensorManager.registerListener(this, heartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);

        try {
            httpServer = new HttpServer(this, getLocalIp(), 8080);
        } catch (IOException e) {
            Log.e(getClass().getSimpleName(), "Could not start httpServer", e);
            Toast.makeText(this, "Could not start HTTP server", Toast.LENGTH_SHORT).show();
            finish();
        }
        Log.i(getClass().getSimpleName(), "onCreate() finished");
    }

    private String getLocalIp() {
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        return Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            float heartRate = event.values[0];
            TextView tvHeartRate = findViewById(R.id.tvHeartRate);
            tvHeartRate.setText(String.valueOf(Math.round(heartRate)) + " BPM");
            Log.i(getClass().getSimpleName(), event.values + " BPM - " + event.accuracy + " Accuracy @"+event.timestamp);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(getClass().getSimpleName(), "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        httpServer.stop();
        Log.i(getClass().getSimpleName(), "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(getClass().getSimpleName(), "onDestroy()");
    }

}