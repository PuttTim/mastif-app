package com.putttim.mastif.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.putttim.mastif.FirestoreRepository;
import com.putttim.mastif.Objects.Playlist;
import com.putttim.mastif.Objects.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SharedViewModel extends ViewModel {
    FirestoreRepository repo = new FirestoreRepository();
    MutableLiveData<List<Song>> songs = new MutableLiveData<>();

    public SharedViewModel() {
        songs.setValue(repo.getSongs());
    }

    public void setPlaylistSongs(Playlist playlist) {
        List<Song> songsList = new ArrayList<>();
        songsList.add(Objects.requireNonNull(songs.getValue()).get(0));
        songsList.add(Objects.requireNonNull(songs.getValue()).get(3));
        songsList.add(Objects.requireNonNull(songs.getValue()).get(6));
        songsList.add(Objects.requireNonNull(songs.getValue()).get(9));
        playlist.setPlaylistSongs(songsList);
        repo.setPlaylistSongs(playlist);
    }


    public MutableLiveData<List<Song>> getSongs() {
        return songs;
    }

}
