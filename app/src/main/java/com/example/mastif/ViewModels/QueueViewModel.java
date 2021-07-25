package com.example.mastif.ViewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mastif.Objects.Song;

import java.util.ArrayList;
import java.util.List;

public class QueueViewModel extends ViewModel {
    private List<Song> playingQueue = new ArrayList<>();
    MutableLiveData<List<Song>> mPlayingQueue = new MutableLiveData<>(playingQueue);

    public MutableLiveData<List<Song>> getQueue() {
        return mPlayingQueue;
    }

    public void queueAddList (List<Song> list) {
        mPlayingQueue.postValue(list);
    }

    public void queueAddSong (Song song) {
        List<Song> list = mPlayingQueue.getValue();
        list.add(song);
        Log.d("LogcatDebug", String.format("Queue added %s", song.getTitle()));

        mPlayingQueue.postValue(list);
    }


}
