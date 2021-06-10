package io.michimpunkt.heartrateoverwifi;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.security.Permission;

import io.michimpunkt.heartrateoverwifi.databinding.ActivityMainBinding;

public class MainActivity extends Activity {

    private ActivityMainBinding binding;
    private static String[] PERMS = new String[] {
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.BODY_SENSORS
    };
    private static int PERMSCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActivityCompat.requestPermissions(this, PERMS, PERMSCODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMSCODE && grantResults.length == PERMS.length) {
            boolean allPerms = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED)
                    allPerms = false;
            }
            if (allPerms) {
                setupWithPerms();
            } else {
                noPerms();
            }
        } else {
            noPerms();
        }
    }

    private void setupWithPerms() {

    }

    // if we don't have any permissions, we can not use the app
    private void noPerms() {
        Toast.makeText(this, "We need those permissions for the app to work..", Toast.LENGTH_SHORT).show();
        System.exit(0);
    }

}