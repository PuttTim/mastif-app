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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FirestoreRepository {
    private Song song;

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
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.d("LogD FR", "onFail");
            }
        });
    }

    public void addUser(User user) {
        DocumentReference userRef = userbaseRef.document(user.getUserId());
        userRef.get().addOnSuccessListener(documentSnapshot -> {
            // if the snapshot of the user does not exist, then
            if (!documentSnapshot.exists()) {
                userRef.set(user);
                Playlist likedPlaylist = new Playlist("", "liked", "Liked songs", null);
                this.createPlaylist(likedPlaylist);
                Log.d("LogD FR", "User set in Firestore");
        }
    });
    }

    public String createPlaylist(Playlist playlist) {
        Log.d("LogD FR", "createPlaylist start");
        DocumentReference userRef = userbaseRef.document(this.getUserId());
        CollectionReference playlistsRef = userRef.collection("playlists");
        DocumentReference playlistRef = playlistsRef.document();
        playlistRef.set(playlist);
        // Sets the field playlistId to the document Id
        playlistRef.update("playlistId",  playlistRef.getId());
        Log.d("LogD FR", "createPlaylist end");

        return playlistRef.getId();
    }

    public void setPlaylistSongs(Playlist playlist) {
        playlist.setId(createPlaylist(playlist));
        Log.d("LogD FR", "setPlaylistSongs start");
        DocumentReference playlistRef = userbaseRef
                .document(this.getUserId())
                .collection("playlists")
                .document(playlist.getPlaylistId());

        List<Song> songsList = playlist.getPlaylistSongs();
        for (Song song : songsList) {
            CollectionReference songslistRef = playlistRef.collection("songs");
            DocumentReference songsRef = songslistRef.document();
            songsRef.set(song);
            Log.d("LogD FR", String.format("setPlaylist Loop %s", song.getTitle()));

        }
        Log.d("LogD FR", "setPlaylistSongs end");
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
