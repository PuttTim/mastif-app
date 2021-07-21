package com.example.mastif.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mastif.R;
import com.example.mastif.Song;
import com.example.mastif.SongCollection;
import com.example.mastif.cardSong;

import java.util.List;

public class SharedViewModel extends ViewModel {
    SongCollection songLibrary = new SongCollection();
    MutableLiveData<List<Song>> songs = new MutableLiveData<>(songLibrary.getList());

    public MutableLiveData<List<Song>> getSongs() {
        return songs;
    }


}
