package com.example.mastif;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private List<Song> songs;
    private RecyclerClick recyclerClick;
    public RecyclerAdapter(List<Song> songs, RecyclerClick recyclerClick) {
        this.songs = songs;
        this.recyclerClick = recyclerClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_card, parent, false);
        ViewHolder vh = new ViewHolder(v);
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

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView1);
            mTextView2 = itemView.findViewById(R.id.textView2);
        }

        private void bind(Song selectedSong) {
            Log.d("bind111", selectedSong.getCover());
            Picasso.get().load(selectedSong.getCover()).into(mImageView);
            mTextView1.setText(selectedSong.getTitle());
            mTextView2.setText(selectedSong.getArtist());

            itemView.setOnClickListener(v -> recyclerClick.onSongClick());
        }



    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
}
