package com.putttim.mastif;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.putttim.mastif.Objects.Song;
import com.putttim.mastif.Objects.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FirestoreRepository {
    private static final String TAG = "FIREBASE_REPO";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ARTIST = "artist";
    private static final String KEY_COVER = "cover";
    private static final String KEY_LINK = "link";

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
                Log.d("LogD FR", "User set in Firestore");
        }
    });

    }

    public List<Song> getSongs() {
        if (libraryList != null) {
            getLibrary();
            return libraryList;
        }
        return libraryList;
    }
}
