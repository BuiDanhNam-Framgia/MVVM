package com.example.nambd.mvvm.data.source.remote;

import android.content.Context;

import com.example.nambd.mvvm.data.model.Collection;
import com.example.nambd.mvvm.data.model.CollectionResult;
import com.example.nambd.mvvm.data.model.Track;
import com.example.nambd.mvvm.data.source.TrackDataSource;
import com.example.nambd.mvvm.data.source.local.TrackLocalDataSource;
import com.example.nambd.mvvm.util.Constant;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;


public class TrackRemoteDataSource implements TrackDataSource {
    private TrackRemoteDataSource() {
    }

    private static TrackRemoteDataSource sInstance;

    public static TrackRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new TrackRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public void getListTrack(String url,
                             final Callback<List<Track>> callback) {
        new LoadAsync(new TrackDataSource.Callback<String>() {

            @Override
            public void onGetSuccess(String data) {
                if (data == null) {
                    callback.onGetFailure("ERROR");
                    return;
                }
                callback.onGetSuccess(getToJson(data));
            }

            @Override
            public void onGetFailure(String message) {
                callback.onGetFailure(message);
            }

            @Override
            public void onComplete() {
                callback.onComplete();
            }
        }).execute(url);

    }

    @Override
    public Observable<Track> addTrack(Track track) {
        return null;
    }

    @Override
    public Observable<Track> getTrackById(int id) {

        return null;
    }

    private List<Track> getToJson(String jsonData) {
        CollectionResult collectionResult =
                new Gson().fromJson(jsonData, CollectionResult.class);
        List<Track> result = new ArrayList<>();
        for (Collection collection : collectionResult.getCollection()) {
            result.add(collection.getTrack());
        }
        return result;
    }

}
