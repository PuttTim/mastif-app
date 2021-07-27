package com.example.mastif.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mastif.Fragments.LibraryFragmentDirections;
import com.example.mastif.Objects.Song;
import com.example.mastif.databinding.SongCardBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LibraryRecyclerAdapter extends RecyclerView.Adapter<LibraryRecyclerAdapter.ViewHolder> {
    private SongCardBinding B;
    private final List<Song> songs;
    private final LibraryRecyclerAdapter.Callback callback;

    public LibraryRecyclerAdapter(List<Song> songs, Callback callback) {
        this.songs = songs;
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
        holder.bind(songs, position);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public interface Callback {
        void onSongClick(int position);
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

        private void bind(List<Song> songs, int position) {
            selectedSong = songs.get(position);
            Picasso.get().load(selectedSong.getCover()).into(mImageView);
            mTextView1.setText(selectedSong.getTitle());
            mTextView2.setText(selectedSong.getArtist());

            itemView.setOnClickListener(v -> callback.onSongClick(position));
        }

    }

}
