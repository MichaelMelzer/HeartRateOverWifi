package io.michimpunkt.heartrateoverwifi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import io.michimpunkt.heartrateoverwifi.databinding.ActivityMainBinding;

public class MainActivity extends Activity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}