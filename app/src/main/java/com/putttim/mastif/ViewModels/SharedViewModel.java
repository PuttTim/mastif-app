package com.putttim.mastif.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.firestore.DocumentReference;
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

    public MutableLiveData<List<Song>> getSongs() {
        return songs;
    }

    public void addSongToLiked(Song song) {
        repo.addSongToPlaylist(song, repo.getPlaylistRef("0"));
    }

    public void createPlaylist(Playlist playlist) {
        repo.createPlaylist(playlist);
    }

    public void addSongToPlaylist(Song song, String playlistId) {
        repo.addSongToPlaylist(song, repo.getPlaylistRef(playlistId));
    }

    public void addListToPlaylist(List<Song> songsList, String playlistId) {
        repo.addSongsListToPlaylist(songsList, repo.getPlaylistRef(playlistId));
    }

}
