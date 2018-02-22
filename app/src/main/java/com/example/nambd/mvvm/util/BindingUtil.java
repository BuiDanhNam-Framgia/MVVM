package com.example.nambd.mvvm.util;

import android.databinding.BindingAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nambd.mvvm.R;

/**
 * Created by NamBD on 2/22/2018.
 */

public class BindingUtil {
    @BindingAdapter("setAdapter")
    public static void setPlaylistAdapter(RecyclerView view, RecyclerView.Adapter adapter) {
        view.setAdapter(adapter);
    }

    @BindingAdapter("imgResource")
    public static void setImage(ImageView imageView, String urlImage) {
        if (urlImage == null) {
            imageView.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(imageView.getContext()).load(urlImage)
                    .into(imageView);
        }
    }

    @BindingAdapter("settext")
    public static void setLikeCount(TextView textView, int numberSong) {
        textView.setText(String.valueOf(numberSong));
    }
}
