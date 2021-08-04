package com.putttim.mastif.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.putttim.mastif.FirestoreRepository;
import com.putttim.mastif.Objects.Song;

import java.util.List;

public class SharedViewModel extends ViewModel {
    FirestoreRepository repo = new FirestoreRepository();
    MutableLiveData<List<Song>> songs = new MutableLiveData<>();

    public MutableLiveData<List<Song>> getSongs() {
        return songs;
    }

    public SharedViewModel() {
        songs.setValue(repo.getSongs());
    }
}
