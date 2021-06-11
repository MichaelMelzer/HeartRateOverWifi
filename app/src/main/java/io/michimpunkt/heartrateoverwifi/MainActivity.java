package io.michimpunkt.heartrateoverwifi;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
            Manifest.permission.BODY_SENSORS,
            Manifest.permission.INTERNET
    };
    private static int PERMSCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActivityCompat.requestPermissions(this, PERMS, PERMSCODE);

        Log.i(getClass().getSimpleName(), "onCreate() FINISHED");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMSCODE) {
            if (grantResults.length == PERMS.length) {
                boolean allPerms = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        allPerms = false;
                        Log.e(getClass().getSimpleName(), "NO PERM "+permissions[i]);
                    } else {
                        Log.i(getClass().getSimpleName(), "We have perms for "+permissions[i]);
                    }
                }
                if (allPerms) {
                    setupWithPerms();
                } else {
                    Log.e(getClass().getSimpleName(), "noPerms() !allPerms");
                    noPerms();
                }
            } else {
                Log.e(getClass().getSimpleName(), "noPerms() PERMS.length");
                noPerms();
            }
        }
    }

    // we have all perms, let's go!
    private void setupWithPerms() {
        findViewById(R.id.btnStart).setEnabled(true);
        Log.i(getClass().getSimpleName(), "setupWithPerms()");
    }

    // if we don't have any permissions, we can not use the app
    private void noPerms() {
        Toast.makeText(this, "We need those permissions for the app to work..", Toast.LENGTH_SHORT).show();
        System.exit(0);
    }

    // everything is set up and ready to go
    public void onBtnStartClicked(View view) {
        // show next activity
        Intent intent = new Intent(this, ReadHeartRateActivity.class);
        startActivity(intent);
        Log.i(getClass().getSimpleName(), "onBtnStartClicked()");
    }

}