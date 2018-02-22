package com.example.nambd.mvvm.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.nambd.mvvm.data.model.Track;
import com.example.nambd.mvvm.data.source.TrackDataSource;
import com.example.nambd.mvvm.util.Constant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import static com.example.nambd.mvvm.util.Constant.ID_TRACK;
import static com.example.nambd.mvvm.util.Constant.IMAGE_TRACK;
import static com.example.nambd.mvvm.util.Constant.LIKES_COUNT;
import static com.example.nambd.mvvm.util.Constant.TABLE_TRACK;
import static com.example.nambd.mvvm.util.Constant.TITLE_TRACK;


/**
 * Created by Bui Danh Nam on 8/1/2018.
 */

public class TrackLocalDataSource extends SQLiteOpenHelper implements TrackDataSource {

    public static int VERSION = 1;
    private static TrackLocalDataSource sInstance;

    public static TrackLocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TrackLocalDataSource(context);
        }
        return sInstance;
    }

    public TrackLocalDataSource(Context context) {
        super(context, Constant.DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE "
                + TABLE_TRACK + " ("
                + ID_TRACK + " INTEGER  primary key,"
                + IMAGE_TRACK + " TEXT, "
                + LIKES_COUNT + " INTEGER, "
                + TITLE_TRACK + " TEXT)";
        db.execSQL(sqlQuery);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    @Override
    public void getListTrack(String url, Callback<List<Track>> callback) {
        List<Track> tracks = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_TRACK,
                null, null, null,
                null, null, null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    tracks.add(parseCursorToTrack(cursor));
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        callback.onGetSuccess(tracks);
    }

    public void updateListTrack(List<Track> tracks) {
        if (tracks == null) {
            return;
        }
        for (Track track : tracks) {
            if (checkTrackExist(track.getId())) {
                updateTrack(track);
            } else {
                addTrack(track);
            }
        }
    }

    @Override
    public Observable<Track> addTrack(final Track track) {
        if (track == null) {
            return null;
        }
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE_TRACK, track.getTitle());
        contentValues.put(ID_TRACK, track.getId());
        contentValues.put(IMAGE_TRACK, track.getArtworkUrl());
        contentValues.put(LIKES_COUNT, track.getLikesCount());
        final long result = sqLiteDatabase.insert(TABLE_TRACK, null, contentValues);
        return Observable.create(new ObservableOnSubscribe<Track>() {
            @Override
            public void subscribe(ObservableEmitter<Track> e) throws Exception {
                if (result != -1) {
                    e.onNext(track);
                } else {
                    e.onError(new SQLiteException());
                }
            }
        });
    }

    @Override
    public Observable<Track> getTrackById(final int id) {
        return Observable.create(new ObservableOnSubscribe<Track>() {
            @Override
            public void subscribe(ObservableEmitter<Track> e) throws Exception {
                SQLiteDatabase database = sInstance.getReadableDatabase();
                Cursor cursor = database.query(TABLE_TRACK,
                        new String[]{ID_TRACK, TITLE_TRACK, IMAGE_TRACK, LIKES_COUNT},
                        ID_TRACK + "=?",
                        new String[]{String.valueOf(id)},
                        null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    Track track = parseCursorToTrack(cursor);
                    e.onNext(track);
                }
            }
        });
    }

    public boolean updateTrack(Track track) {
        if (track == null) {
            return false;
        }
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE_TRACK, track.getTitle());
        contentValues.put(LIKES_COUNT, track.getLikesCount());
        contentValues.put(IMAGE_TRACK, track.getArtworkUrl());
        String where = ID_TRACK + " = " + track.getId();
        int numRow = writableDatabase.update(TABLE_TRACK, contentValues, where, null);
        return numRow > 0;
    }

    public boolean checkTrackExist(int idTrack) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(TABLE_TRACK,
                new String[]{ID_TRACK}, ID_TRACK + "=?",
                new String[]{String.valueOf(idTrack)},
                null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    private Track parseCursorToTrack(Cursor cursor) {
        int indexKeyName = cursor.getColumnIndex(ID_TRACK);
        int indexImage = cursor.getColumnIndex(IMAGE_TRACK);
        int indexTitle = cursor.getColumnIndex(TITLE_TRACK);
        int indexLikeCount = cursor.getColumnIndex(LIKES_COUNT);
        Track track = new Track();
        track.setId(cursor.getInt(indexKeyName));
        track.setTitle(cursor.getString(indexTitle));
        track.setArtworkUrl(cursor.getString(indexImage));
        track.setLikesCount(cursor.getInt(indexLikeCount));
        return track;
    }

}
