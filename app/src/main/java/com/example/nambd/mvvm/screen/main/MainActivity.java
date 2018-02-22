package com.example.nambd.mvvm.screen.main;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nambd.mvvm.R;
import com.example.nambd.mvvm.data.source.local.TrackLocalDataSource;
import com.example.nambd.mvvm.data.source.remote.TrackRemoteDataSource;
import com.example.nambd.mvvm.data.source.repository.TrackRepository;
import com.example.nambd.mvvm.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TrackRepository trackRepository = TrackRepository.getInstance(
                TrackRemoteDataSource.getInstance(),
                TrackLocalDataSource.getInstance(this)
        );
        MainViewModel mainViewModel = new MainViewModel(this, trackRepository);
        ActivityMainBinding activityMainBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setViewModel(mainViewModel);
    }
}
