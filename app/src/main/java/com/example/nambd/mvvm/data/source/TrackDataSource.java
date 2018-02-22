package com.example.nambd.mvvm.data.source;


import com.example.nambd.mvvm.data.model.Track;

import java.util.List;

import io.reactivex.Observable;


public interface TrackDataSource {
    interface Callback<T> {

        void onGetSuccess(T data);

        void onGetFailure(String message);

        void onComplete();
    }

    // use callback handle data
    void getListTrack(String url, Callback<List<Track>> callback);

    Observable<Track> addTrack(Track track);

    Observable<Track> getTrackById(int id);
}
