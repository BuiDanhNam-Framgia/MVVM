package com.example.nambd.mvvm.screen.main;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nambd.mvvm.BR;
import com.example.nambd.mvvm.data.model.Track;
import com.example.nambd.mvvm.data.source.TrackDataSource;
import com.example.nambd.mvvm.data.source.repository.TrackRepository;
import com.example.nambd.mvvm.screen.trackdetail.PlayTrackActivity;
import com.example.nambd.mvvm.util.Constant;

import java.util.List;

/**
 * Created by NamBD on 2/22/2018.
 */

public class MainViewModel extends BaseObservable implements OnItemTrackClick {
    private TrackAdapter mPlaylistAdapter;
    private Context mContext;
    private List<Track> mTracks;
    private TrackRepository mTrackRepository;

    public MainViewModel(Context context, TrackRepository trackRepository) {
        mContext = context;
        mTrackRepository = trackRepository;
        mPlaylistAdapter = new TrackAdapter();
        mPlaylistAdapter.setOnItemAlbumClick(this);
        feetData();


    }

    private void feetData() {
        mTrackRepository.getListTrack(Constant.URL_REMOTE, new TrackDataSource.Callback<List<Track>>() {
            @Override
            public void onGetSuccess(List<Track> data) {
                mTracks = data;
                mPlaylistAdapter.updateAlbums(data);
            }

            @Override
            public void onGetFailure(String message) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Bindable
    public TrackAdapter getPlaylistAdapter() {
        return mPlaylistAdapter;
    }

    public void setPlaylistAdapter(TrackAdapter playlistAdapter) {
        mPlaylistAdapter = playlistAdapter;
        notifyPropertyChanged(BR.playlistAdapter);
    }

    public void onFabAddAlbumClick() {
        Toast.makeText(mContext, "onFAB click", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(Track track) {
        if (track == null) {
            return;
        }
        mContext.startActivity(PlayTrackActivity.getInstance(mContext, track));
    }


}
