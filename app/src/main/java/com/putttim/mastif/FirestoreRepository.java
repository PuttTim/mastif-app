package com.putttim.mastif;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.putttim.mastif.Objects.Playlist;
import com.putttim.mastif.Objects.Song;
import com.putttim.mastif.Objects.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FirestoreRepository {
    List<Song> libraryList = new ArrayList<>();
    List<Playlist> playlistList = new ArrayList<>();

    // Final references to the base root collections of the Firestore
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference libraryRef = db.collection("songs-library");
    private final CollectionReference userbaseRef = db.collection("userbase");

    // Gets the default songs library from Firestore
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

    // Adds the user into the database, this is done everytime a completely new user logs into the app.
    public void addUser(User user) {
        DocumentReference userRef = userbaseRef.document(user.getUserId());
        userRef.get().addOnSuccessListener(documentSnapshot -> {
            // if the snapshot of the user does not exist,
            // then this will be the initial setup of the user.
            if (!documentSnapshot.exists()) {
                userRef.set(user);
                Playlist likedPlaylist = new Playlist("0",
                        "Liked songs",
                        "Liked songs",
                        "https://cdn.discordapp.com/attachments/737456465979244564/873854140969795594/Liked_Logo_Png.png",
                        null);
                this.createLikedPlaylist(likedPlaylist);
                Log.d("LogD FR", "User set in Firestore");
                fetchPlaylists();
        }
    });
    }

    // Fetches all of the user's playlists.
    private void fetchPlaylists() {
        CollectionReference playlistsRef = userbaseRef
                .document(this.getUserId())
                .collection("playlists");
        playlistList.clear();
        playlistsRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    Log.d("LogD FR", doc.getId());
                    Playlist playlist = doc.toObject(Playlist.class);
                    CollectionReference songsRef = playlistsRef.document(doc.getId()).collection("songs");

                    playlist.setPlaylistSongs(fetchPlaylistSong(songsRef));
                    playlistList.add(playlist);
                }
            }
        });
    }

    // Fetches a Playlist's songs using the reference of the songs collection inside the playlist
    private List<Song> fetchPlaylistSong(CollectionReference songsRef) {
        List<Song> songList = new ArrayList<>();

        songsRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot songDoc : queryDocumentSnapshots) {
                Song song = songDoc.toObject(Song.class);
                song.setSongId(songDoc.getId());
                songList.add(song);
            }
        });
        return songList;
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
    playlistData.put("playlistCover", playlist.getPlaylistCover());

    // Creates a Document with the playlistData (In this case, a liked playlist)
    playlistRef.set(playlistData);
    Log.d("LogD FR", "LikedPlaylist added");
    }

    // Creates a playList inside the Firestore using the taken in Playlist object
    public void createPlaylist(Playlist playlist) {
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

        // Sets the playlistData to the autogenerated playlistId
        playlistRef.update("playlistId",  playlistRef.getId());
        playlist.setId(playlistRef.getId());
        this.addSongsListToPlaylist(playlist.getPlaylistSongs(), playlistRef);
    }

    // Adds a songList to a playlist
    public void addSongsListToPlaylist(List<Song> playlistSongs, DocumentReference playlistRef) {
        if (playlistSongs != null){
            for (Song song : playlistSongs) {
                this.addSongToPlaylist(song, playlistRef);
            }
        }
        else {
            Log.d("LogD FR", "addSongsListToPlaylist playlistSongs is null");
        }
        fetchPlaylists();
    }

    // Adds a song to a playlist by the playlistRef
    public void addSongToPlaylist(Song song, DocumentReference playlistRef) {
        playlistRef
                .collection("songs")
                // Setting the document id as the song.getSongId means that there will be no duplicates
                .document(song.getSongId())
                .set(song);
        Log.d("LogD FR", String.format("added Title: %s to playlist %s", song.getTitle(), playlistRef.getId()));
    }

    public void addToLiked(Song song) {
        DocumentReference playlistRef = getPlaylistRef("0");
        playlistRef
                .collection("songs")
                .document(song.getSongId())
                .set(song);
                // Setting the document id as the song.getSongId means that there will be no duplicates
        Log.d("LogD FR", String.format("added Title: %s to playlist %s", song.getTitle(), playlistRef.getId()));
        fetchPlaylists();
    }

    // This gets the Playlist Reference as specified by the playlistId
    public DocumentReference getPlaylistRef(String playlistId) {
        return userbaseRef
                .document(this.getUserId())
                .collection("playlists")
                .document(playlistId);
    }

    // Gets the UserId from FirebaseAuth
    public String getUserId() {
        return Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    }

    // Gets the Songs Library
    public List<Song> getSongs() {
        if (libraryList != null) {
            getLibrary();
            return libraryList;
        }
        return libraryList;
    }

    // Fetches the user's playlists and returns it to whoever called this method.
    public List<Playlist> getPlaylistList() {
        fetchPlaylists();
        return playlistList;
    }
}
