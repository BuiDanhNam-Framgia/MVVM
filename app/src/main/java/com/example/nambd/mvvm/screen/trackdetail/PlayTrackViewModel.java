package com.example.nambd.mvvm.screen.trackdetail;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.os.IBinder;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.nambd.mvvm.data.model.Track;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sony on 1/11/2018.
 */

public class PlayTrackViewModel extends BaseObservable {
    private List<Track> mTracks;
    private Track mTrack;
    private boolean mMusicBound = false;
    private OnClickTrackListener mOnClickTrackListener;

    public void setOnClickTrackListener(OnClickTrackListener onClickTrackListener) {
        mOnClickTrackListener = onClickTrackListener;
    }


    public PlayTrackViewModel(Track tracks) {
        mTrack = tracks;
        setTrack(tracks);
    }

    @Bindable
    public Track getTrack() {
        return mTrack;
    }

    public void setTrack(Track track) {
        mTrack = track;
        notifyPropertyChanged(0);
    }

    public String getDescription() {
        if (mTrack != null) {
            return mTrack.getDescription();
        }
        return null;
    }

    public String getImageTrack() {
        if (mTrack != null) {
            return mTrack.getArtworkUrl();
        }
        return null;
    }


    public String getTitle() {
        return mTrack != null ? mTrack.getTitle() : "";
    }

    public void onClickBack(View view) {
        mOnClickTrackListener.onClickBack(view);
    }
}
