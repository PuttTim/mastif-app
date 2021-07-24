package com.example.mastif.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mastif.Fragments.LibraryFragment;
import com.example.mastif.Objects.Song;
import com.example.mastif.RecyclerClick;
import com.example.mastif.ViewModels.PlayerViewModel;
import com.example.mastif.databinding.FragmentPlayerBinding;
import com.example.mastif.databinding.SongCardBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LibraryRecyclerAdapter extends RecyclerView.Adapter<LibraryRecyclerAdapter.ViewHolder> {
    private List<Song> songs;
    private SongCardBinding binding;
    private RecyclerClick mCallback;


    public LibraryRecyclerAdapter(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = SongCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder vh = new ViewHolder(binding, mCallback);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Song selectedSong = songs.get(position);
        holder.bind(songs, position);


    }

    public void setOnItemClickListener (RecyclerClick listener) {
        mCallback = listener;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView mImageView;
        final TextView mTextView1;
        final TextView mTextView2;
        private Song selectedSong;

        public ViewHolder(SongCardBinding B, RecyclerClick listener) {

            super(B.getRoot());
            mImageView = B.imageView;
            mTextView1 = B.textView1;
            mTextView2 = B.textView2;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getBindingAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onSongClick(position);
                            Log.d("MASTIF", String.format("Song clicked %s", songs.get(position).getTitle()));
                        }
                    }
                }
            });
        }

        private void bind(List<Song> songs, int position) {
            selectedSong = songs.get(position);
            Log.d("bind111", selectedSong.getCover());
            Picasso.get().load(selectedSong.getCover()).into(mImageView);
            mTextView1.setText(selectedSong.getTitle());
            mTextView2.setText(selectedSong.getArtist());



        }



    }




    @Override
    public int getItemCount() {
        return songs.size();
    }
}
