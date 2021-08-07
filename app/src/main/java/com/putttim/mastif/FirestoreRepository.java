package com.putttim.mastif;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.putttim.mastif.Objects.Playlist;
import com.putttim.mastif.Objects.Song;
import com.putttim.mastif.Objects.User;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FirestoreRepository {
    List<Song> libraryList = new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference libraryRef = db.collection("songs-library");
    private CollectionReference userbaseRef = db.collection("userbase");

    private void getLibrary() {
        libraryRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Song song = documentSnapshot.toObject(Song.class);

                    song.setSongId(documentSnapshot.getId());

                    libraryList.add(song);
                }
            }
        }).addOnFailureListener(e -> Log.d("LogD FR", "onFail"));
    }

    public void addUser(User user) {
        DocumentReference userRef = userbaseRef.document(user.getUserId());
        userRef.get().addOnSuccessListener(documentSnapshot -> {
            // if the snapshot of the user does not exist, then
            if (!documentSnapshot.exists()) {
                userRef.set(user);
                Playlist likedPlaylist = new Playlist("0", "liked", "Liked songs", null);
                this.createLikedPlaylist(likedPlaylist);
                Log.d("LogD FR", "User set in Firestore");
        }
    });
    }

    // Liked playlist is simply a default playlist, we're using 0 as the playlistId as we can refer
    // to this easily when adding songs to the likedPlaylist without having to sort through every playlist.
    // Thus, saving some computational power.
    private void createLikedPlaylist(Playlist playlist) {
        DocumentReference playlistRef = userbaseRef
                .document(this.getUserId())
                .collection("playlists")
                .document("0");

        Map<String, Object> playlistData = new HashMap<>();
        playlistData.put("playlistName", playlist.getPlaylistName());
        playlistData.put("description", playlist.getDescription());
        playlistData.put("playlistId", "0");
        playlistRef.set(playlistData);

        Log.d("LogD FR", "LikedPlaylist added");

    }

    public void createPlaylist(Playlist playlist) {
        Log.d("LogD FR", "createPlaylist start");
        // Creates an empty document inside the "playlists" collection
        DocumentReference playlistRef = userbaseRef
                .document(this.getUserId())
                .collection("playlists")
                .document();
        // Sets the the fields with their corresponding value inside the playlist
        // We're using a map instead of passing in the playlist straight as .set(playlist);
        // because we want it to skip the playlistSongs part of the list as we'll be using a
        // subcollection to store our songs inside the playlists
        Map<String, Object> playlistData = new HashMap<>();
        playlistData.put("playlistName", playlist.getPlaylistName());
        playlistData.put("description", playlist.getDescription());
        playlistData.put("playlistId", null);
        playlistRef.set(playlistData);

        playlistRef.update("playlistId",  playlistRef.getId());
        Log.d("LogD FR", "createPlaylist end");
        this.addSongsListToPlaylist(playlist.getPlaylistSongs(), playlistRef);
    }

    public void addSongsListToPlaylist(List<Song> playlistSongs, DocumentReference playlistRef) {
        if (playlistSongs != null){
            for (Song song : playlistSongs) {
                this.addSongToPlaylist(song, playlistRef);
            }

        }
        else {
            Log.d("LogD FR", "addSongsListToPlaylist playlistSongs is null");
        }

    }

    public void addSongToPlaylist(Song song, DocumentReference playlistRef) {
        playlistRef
                .collection("songs")
                // Setting the document id as the song.getSongId means that there will be no duplicates
                .document(song.getSongId())
                .set(song);
        Log.d("LogD FR", String.format("added Title: %s to playlist %s", song.getTitle(), playlistRef.getId()));

    }

    public DocumentReference getPlaylistRef(String playlistId) {
        return userbaseRef
                .document(this.getUserId())
                .collection("playlists")
                .document(playlistId);
    }

    public String getUserId() {
        return Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    }

    public List<Song> getSongs() {
        if (libraryList != null) {
            getLibrary();
            return libraryList;
        }
        return libraryList;
    }
}
