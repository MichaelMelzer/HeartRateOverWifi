package io.michimpunkt.heartrateoverwifi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import io.michimpunkt.heartrateoverwifi.databinding.ActivityReadHeartRateBinding;

public class ReadHeartRateActivity extends Activity {

    private TextView mTextView;
    private ActivityReadHeartRateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityReadHeartRateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}