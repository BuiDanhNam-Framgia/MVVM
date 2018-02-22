package com.example.nambd.mvvm.data.source.repository;


import android.support.annotation.NonNull;

import com.example.nambd.mvvm.data.model.Track;
import com.example.nambd.mvvm.data.source.TrackDataSource;
import com.example.nambd.mvvm.data.source.local.TrackLocalDataSource;
import com.example.nambd.mvvm.data.source.remote.TrackRemoteDataSource;

import java.util.List;

import io.reactivex.Observable;

public class TrackRepository implements TrackDataSource {
    private static TrackRepository sInstance;
    private final TrackRemoteDataSource mTrackRemoteDataSource;
    private final TrackLocalDataSource mTrackLocalDataSource;

    private TrackRepository(@NonNull TrackDataSource trackRemoteDataSource,
                            @NonNull TrackDataSource trackLocalDataSource) {
        mTrackRemoteDataSource = (TrackRemoteDataSource) trackRemoteDataSource;
        mTrackLocalDataSource = (TrackLocalDataSource) trackLocalDataSource;
    }

    public static TrackRepository getInstance(TrackDataSource trackRemoteDataSource,
                                              TrackDataSource trackLocalDataSource) {
        if (sInstance == null) {
            sInstance = new TrackRepository(trackRemoteDataSource, trackLocalDataSource);
        }
        return sInstance;
    }

    @Override
    public void getListTrack(String url,
                             final Callback<List<Track>> callback) {
        mTrackRemoteDataSource.getListTrack(url, new Callback<List<Track>>() {
            @Override
            public void onGetSuccess(List<Track> data) {
                // mix data local and remote
                // save list track local
                mTrackLocalDataSource.updateListTrack(data);
                callback.onGetSuccess(data);
            }

            @Override
            public void onGetFailure(String message) {
                callback.onGetFailure(message);
                //if get value to remote failure , get value to local
                mTrackLocalDataSource.getListTrack(null, new Callback<List<Track>>() {
                    @Override
                    public void onGetSuccess(List<Track> data) {
                        callback.onGetSuccess(data);
                    }

                    @Override
                    public void onGetFailure(String message) {
                        callback.onGetFailure(message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }

            @Override
            public void onComplete() {
                callback.onComplete();
            }
        });
    }

    @Override
    public Observable<Track> addTrack(Track track) {
        return null;
    }

    @Override
    public Observable<Track> getTrackById(int id) {
//        final Track trackTemp = null;
//        mTrackRemoteDataSource.getTrackById(id)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Observer<Track>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Track track) {
//                        trackTemp = track;
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
        return null;
    }


}
