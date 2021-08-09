package com.putttim.mastif.Adapters;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.putttim.mastif.Objects.Playlist;
import com.putttim.mastif.Objects.Song;
import com.putttim.mastif.R;
import com.putttim.mastif.databinding.CardPlaylistSelectSongsBinding;
import com.putttim.mastif.databinding.CardSongsBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class PlaylistCreateRecyclerAdapter extends RecyclerView.Adapter<PlaylistCreateRecyclerAdapter.PlaylistCreateViewHolder> {
    private CardPlaylistSelectSongsBinding B;
    private final List<Song> songList;
    private PlaylistCreateRecyclerAdapter.songSelectCallback callback;

    public PlaylistCreateRecyclerAdapter(List<Song> songs, songSelectCallback callback) {
        this.songList = songs;
        this.callback = callback;
    }

    @Override
    public PlaylistCreateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        B = CardPlaylistSelectSongsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        PlaylistCreateViewHolder vh = new PlaylistCreateViewHolder(B);
        return vh;
    }

    @Override
    public void onBindViewHolder(PlaylistCreateViewHolder holder, int position) {
        holder.bind(songList, position);
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public interface songSelectCallback {
        void onSelectSong(Song song);
    }

    class PlaylistCreateViewHolder extends RecyclerView.ViewHolder {
        final ImageView mImageCover;
        final TextView mTextTitle;
        final TextView mTextArtist;
        final CardView mCardView;
        final ImageView mSelectCircle;
        private Song selectedSong;
        List<Song> selectedSongs = new ArrayList<>();


        public PlaylistCreateViewHolder(CardPlaylistSelectSongsBinding B) {
            super(B.getRoot());
            mImageCover = B.imgCover;
            mTextTitle = B.txtTitle;
            mTextArtist = B.txtArtist;
            mCardView = B.cardView;
            mSelectCircle = B.imgSelectCircle;

            Log.d("LogD PCRA", String.format("size: %s", songList.size()));


        }

        @SuppressLint("ResourceAsColor")
        private void bind(List<Song> songList, int position) {
            // Gets the selectedSong from the songList and sets the image and text inside the recyclerView
            selectedSong = songList.get(position);
            Picasso.get().load(selectedSong.getCover()).transform(new CropCircleTransformation()).into(mImageCover);
            mTextTitle.setText(selectedSong.getTitle());
            mTextArtist.setText(selectedSong.getArtist());

            // Logics for setting the selected circle icon to make sure that it doesn't reset
            // when the user scrolls, as this is a RecyclerView.
            if (selectedSongs.contains(selectedSong)) {
                mSelectCircle.setColorFilter(itemView.getContext().getColor(R.color.purpleLavender));
            } else if (!selectedSongs.contains(selectedSong)) {
                mSelectCircle.setColorFilter(itemView.getContext().getColor(R.color.primaryGrey));
            }

            // When the user selects a song, it'll get added to the playlist of selectedSongs
            // which will be called back to PlaylistCreateFragment which will later on confirm the playlist
            // and adds it to Firestore.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectCircle.setColorFilter(itemView.getContext().getColor(R.color.purpleLavender));
                    callback.onSelectSong(selectedSong);
                    selectedSongs.add(selectedSong);
                }
            });

        }

    }

}