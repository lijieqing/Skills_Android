package hua.lee.skills;

import android.os.Bundle;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import hua.lee.skills.databinding.ActivityMainBinding;
import hua.lee.skills.performance.LooperWatch;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        requestPermissions(new String[]{
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.INTERNET",
        }, 0);

        LooperWatch.startWatch(Looper.getMainLooper(), 300, 10);
    }
}