package com.example.mastif.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mastif.Objects.Song;
import com.example.mastif.databinding.SongCardBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LibraryRecyclerAdapter extends RecyclerView.Adapter<LibraryRecyclerAdapter.ViewHolder> {
    private List<Song> songs;
    private SongCardBinding binding;

    public LibraryRecyclerAdapter(List<Song> songs) {
        this.songs = songs;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = SongCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_card, parent, false);
        ViewHolder vh = new ViewHolder(binding);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Song selectedSong = songs.get(position);
        holder.bind(selectedSong);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView mImageView;
        final TextView mTextView1;
        final TextView mTextView2;

        public ViewHolder(SongCardBinding binding) {
            super(binding.getRoot());
            mImageView = binding.imageView;
            mTextView1 = binding.textView1;
            mTextView2 = binding.textView2;
        }

        private void bind(Song selectedSong) {
            Log.d("bind111", selectedSong.getCover());
            Picasso.get().load(selectedSong.getCover()).into(mImageView);
            mTextView1.setText(selectedSong.getTitle());
            mTextView2.setText(selectedSong.getArtist());

            itemView.setOnClickListener(v -> songs.remove(0));
        }



    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
}
