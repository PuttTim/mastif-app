package com.putttim.mastif.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.putttim.mastif.Objects.Song;
import com.putttim.mastif.databinding.SongCardBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class LibraryRecyclerAdapter extends RecyclerView.Adapter<LibraryRecyclerAdapter.ViewHolder> {
    private SongCardBinding B;
    private final List<Song> songList;
    private final LibraryRecyclerAdapter.Callback callback;

    public LibraryRecyclerAdapter(List<Song> songs, Callback callback) {
        this.songList = songs;
        this.callback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        B = SongCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder vh = new ViewHolder(B);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(songList, position);
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public interface Callback {
        void onSongClick(List<Song> songList, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView mImageView;
        final TextView mTextView1;
        final TextView mTextView2;
        private Song selectedSong;

        public ViewHolder(SongCardBinding B) {
            super(B.getRoot());
            mImageView = B.imageView;
            mTextView1 = B.textView1;
            mTextView2 = B.textView2;

        }

        private void bind(List<Song> songList, int position) {
            // Gets the selectedSong from the songList and sets the image and text inside the recyclerView
            selectedSong = songList.get(position);
            Picasso.get().load(selectedSong.getCover()).transform(new CropCircleTransformation()).into(mImageView);
            mTextView1.setText(selectedSong.getTitle());
            mTextView2.setText(selectedSong.getArtist());

            // When the user clicks on an item (song), it'll call onSongClick passing in songList
            // and the position which also calls the method inside LibraryFragment.
            itemView.setOnClickListener(v -> callback.onSongClick(songList, position));
        }

    }

}