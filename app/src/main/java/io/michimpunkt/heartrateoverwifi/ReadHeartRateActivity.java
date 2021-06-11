package io.michimpunkt.heartrateoverwifi;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import io.michimpunkt.heartrateoverwifi.databinding.ActivityReadHeartRateBinding;

public class ReadHeartRateActivity extends Activity implements SensorEventListener {

    private TextView mTextView;
    private ActivityReadHeartRateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityReadHeartRateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        sensorManager.registerListener(this, heartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);

        Log.i(getClass().getName(), "onCreate() FINISHED");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            float heartRate = event.values[0];
            TextView tvHeartRate = findViewById(R.id.tvHeartRate);
            tvHeartRate.setText(String.valueOf(Math.round(heartRate)) + " BPM");
            Log.i(getClass().getName(), event.values + " BPM - " + event.accuracy + " Accuracy @"+event.timestamp);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(getClass().getName(), "onPause()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(getClass().getName(), "onDestroy()");
    }
}