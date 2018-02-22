package com.example.nambd.mvvm.screen.trackdetail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.nambd.mvvm.R;
import com.example.nambd.mvvm.data.model.Track;
import com.example.nambd.mvvm.databinding.ActivityDetailTrackBinding;


public class PlayTrackActivity extends AppCompatActivity implements OnClickTrackListener {
    private PlayTrackViewModel mPlayTrackViewModel;
    private Track mTrack;
    private final static String EXTRA_TRACK = "track";

    public static Intent getInstance(Context context, Track track) {
        Intent intent = new Intent(context, PlayTrackActivity.class);
        intent.putExtra(EXTRA_TRACK, track);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailTrackBinding activityPlayTrackBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_detail_track);
        Intent intent = getIntent();
        mTrack = intent.getParcelableExtra(EXTRA_TRACK);
        mPlayTrackViewModel = new PlayTrackViewModel(mTrack);
        mPlayTrackViewModel.setOnClickTrackListener(this);
        activityPlayTrackBinding.setViewModel(mPlayTrackViewModel);
        getSupportActionBar().hide();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClickBack(View view) {
        this.finish();
    }


}
