package com.putttim.mastif.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.putttim.mastif.Objects.Song;
import com.putttim.mastif.R;
import com.putttim.mastif.databinding.CardSongsBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class LibraryRecyclerAdapter extends RecyclerView.Adapter<LibraryRecyclerAdapter.LibraryViewHolder> {
    private CardSongsBinding B;
    private final List<Song> songList;
    private final LibraryRecyclerAdapter.Callback callback;

    public LibraryRecyclerAdapter(List<Song> songs, Callback callback) {
        this.songList = songs;
        this.callback = callback;
    }

    @Override
    public LibraryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        B = CardSongsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        LibraryViewHolder vh = new LibraryViewHolder(B);
        return vh;
    }

    @Override
    public void onBindViewHolder(LibraryViewHolder holder, int position) {
        holder.bind(songList, position);
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public interface Callback {
        void onSongClick(List<Song> songList, int position);
        void onAddToLikedClick(Song song);
    }

    class LibraryViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageCover;
        final TextView textTitle;
        final TextView textArtist;
        final ImageView imageMenu;
        private Song selectedSong;

        public LibraryViewHolder(CardSongsBinding B) {
            super(B.getRoot());
            this.imageCover = B.imgCover;
            this.textTitle = B.txtTitle;
            this.textArtist = B.txtArtist;
            this.imageMenu = B.imgMenu;
        }

        private void bind(List<Song> songList, int position) {
            // Gets the selectedSong from the songList and sets the image and text inside the recyclerView
            selectedSong = songList.get(position);
            Picasso.get().load(selectedSong.getCover()).transform(new CropCircleTransformation()).into(this.imageCover);
            this.textTitle.setText(selectedSong.getTitle());
            this.textArtist.setText(selectedSong.getArtist());

            // When the user clicks on an item (song), it'll call onSongClick passing in songList
            // and the position which also calls the method inside LibraryFragment.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("LogD PCFRA", String.format("selectedSong: ", selectedSong.getTitle()));
                    callback.onSongClick(songList, position);
                }
            });
            this.imageMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu menu = new PopupMenu(itemView.getContext(), imageMenu);

                    menu.inflate(R.menu.options_menu);

                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            callback.onAddToLikedClick(selectedSong);
                            return true;
                        }
                    });
                    menu.show();
                }

            });


        }

    }

}
