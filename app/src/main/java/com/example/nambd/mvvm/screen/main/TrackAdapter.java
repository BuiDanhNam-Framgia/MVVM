package com.example.nambd.mvvm.screen.main;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.nambd.mvvm.R;
import com.example.nambd.mvvm.data.model.Track;
import com.example.nambd.mvvm.databinding.ItemTrackBinding;

import java.util.List;

/**
 * Created by Bui Danh Nam on 8/1/2018.
 */

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {
    private List<Track> mTracks;
    private OnItemTrackClick mOnItemAlbumClick;

    public TrackAdapter() {
    }

    public List<Track> getAlbums() {
        return mTracks;
    }

    public void updateAlbums(List<Track> albums) {
        if (albums == null) {
            return;
        }
        mTracks = albums;
        notifyDataSetChanged();
    }

    public void setOnItemAlbumClick(OnItemTrackClick onItemAlbumClick) {
        mOnItemAlbumClick = onItemAlbumClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemTrackBinding itemAlbumBinding = DataBindingUtil.inflate(
                LayoutInflater.from(
                        parent.getContext()), R.layout.item_track, parent, false);

        return new ViewHolder(itemAlbumBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mTracks.get(position));
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size() : 0;
    }

    /**
     * Created by Bui Danh Nam on 8/1/2018.
     */

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemTrackBinding mItemAlbumBinding;

        public ViewHolder(ItemTrackBinding itemAlbumBinding) {
            super(itemAlbumBinding.getRoot());
            mItemAlbumBinding = itemAlbumBinding;
        }

        private void bindData(Track track) {
            mItemAlbumBinding.setTrack(track);
            mItemAlbumBinding.setListener(mOnItemAlbumClick);
            mItemAlbumBinding.executePendingBindings();
        }
    }

}
