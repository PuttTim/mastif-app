package com.putttim.mastif.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.putttim.mastif.Objects.Playlist;
import com.putttim.mastif.Objects.Song;
import com.putttim.mastif.R;
import com.putttim.mastif.databinding.CardPlaylistBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class PlaylistRecyclerAdapter extends RecyclerView.Adapter<PlaylistRecyclerAdapter.PlaylistViewHolder> {
    private CardPlaylistBinding B;
    private List<Playlist> playlistList;
    private final PlaylistRecyclerAdapter.Callback callback;

    public PlaylistRecyclerAdapter(List<Playlist> playlistList, Callback callback) {
        this.playlistList = playlistList;
        this.callback = callback;
    }

    @Override
    public PlaylistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        B = CardPlaylistBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        PlaylistViewHolder vh = new PlaylistViewHolder(B);
        return vh;
    }

    @Override
    public void onBindViewHolder(PlaylistRecyclerAdapter.PlaylistViewHolder holder, int position) {
        holder.bind(playlistList, position);
    }

    @Override
    public int getItemCount() {
        return playlistList.size();
    }

    public interface Callback {
        void onPlaylistClick(Playlist selectedPlaylist);
    }


    class PlaylistViewHolder extends RecyclerView.ViewHolder {
        final ImageView mImagePlaylistCover;
        final TextView mTextPlaylistName;
        final TextView mTextSongCount;
        private Playlist bindedPlaylist;

        public PlaylistViewHolder(CardPlaylistBinding B) {
            super(B.getRoot());
            mImagePlaylistCover = B.imageCover;
            mTextPlaylistName = B.textName;
            mTextSongCount = B.textSongCount;
        }

        private void bind(List<Playlist> playlistList, int position) {
            // Sets the bindedPlaylist as the current playlist inside that position
            bindedPlaylist = playlistList.get(position);

            // Sets the playlist name and the count of the Songs inside the playlist
            mTextPlaylistName.setText(bindedPlaylist.getPlaylistName());
            mTextSongCount.setText(String.format("%s songs", bindedPlaylist.getPlaylistSongs().size()));

            if (bindedPlaylist.getPlaylistId().equals("0")) {
                // If the song is the liked Playlist, it'll use the Heart icon.
                Picasso.get().load(bindedPlaylist.getPlaylistCover())
                        .transform(new RoundedCornersTransformation(50, 0))
                        .into(mImagePlaylistCover);
            }
            else if (!bindedPlaylist.getPlaylistSongs().isEmpty()) {
                // Checks if the playlist has a song, if it does, it'll use this picture for the playlistCover.
                Picasso.get().load(bindedPlaylist.getPlaylistSongs().get(0).getCover())
                        .transform(new RoundedCornersTransformation(50, 0))
                        .into(mImagePlaylistCover);
            }
            else {
                // Else, it'll set it as the default MASTIF logo cover
                Picasso.get().load(bindedPlaylist.getPlaylistCover())
                        .transform(new RoundedCornersTransformation(50, 0))
                        .into(mImagePlaylistCover);
            }

            // onClickListener for when the user clicks on a playlist.
            itemView.setOnClickListener(v -> callback.onPlaylistClick(bindedPlaylist));
        }
}}
