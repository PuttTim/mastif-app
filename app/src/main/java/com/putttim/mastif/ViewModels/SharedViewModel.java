package com.putttim.mastif.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.putttim.mastif.Objects.Song;
import com.putttim.mastif.Objects.SongCollection;

import java.util.List;

public class SharedViewModel extends ViewModel {
    SongCollection songLibrary = new SongCollection();
    MutableLiveData<List<Song>> songs = new MutableLiveData<>(songLibrary.getList());

    public MutableLiveData<List<Song>> getSongs() {
        return songs;
    }
}
