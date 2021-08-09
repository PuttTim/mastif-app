package com.putttim.mastif.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.putttim.mastif.FirestoreRepository;
import com.putttim.mastif.Objects.Playlist;
import com.putttim.mastif.Objects.Song;
import com.putttim.mastif.Objects.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SharedViewModel extends ViewModel {
    FirestoreRepository repo = new FirestoreRepository();
    MutableLiveData<List<Song>> songs = new MutableLiveData<>();
    MutableLiveData<List<Playlist>> playlistList = new MutableLiveData<>();
    private Playlist viewingPlaylist;
    private User user;

    public SharedViewModel() {
    }

    public MutableLiveData<List<Song>> getSongs() {
        return songs;
    }

    public MutableLiveData<List<Playlist>> getPlaylistList() {
        return playlistList;
    }

    // Currently unused but works in real testing.
    public void addSongToLiked(Song song) {
        repo.addSongToPlaylist(song, repo.getPlaylistRef("0"));
    }

    public void createPlaylist(Playlist playlist) {
        repo.createPlaylist(playlist);
        updatePlaylistList();
    }

    // Again, currently unused but does work.
    public void addSongToPlaylist(Song song, String playlistId) {
        repo.addSongToPlaylist(song, repo.getPlaylistRef(playlistId));
    }

    private void updatePlaylistList() {
        playlistList.setValue(repo.getPlaylistList());
    }

    public void startupValueSet() {
        songs.setValue(repo.getSongs());
        updatePlaylistList();
    }

    public void setViewingplaylist(Playlist playlist) {
        this.viewingPlaylist = playlist;
    }

    public Playlist getViewingPlaylist() {
        return this.viewingPlaylist;
    }

    public void setUser(String userId, String username) {
        user = new User(userId, username, "");
    }
}
