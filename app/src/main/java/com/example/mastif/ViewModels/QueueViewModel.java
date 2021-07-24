package com.example.mastif.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mastif.Objects.Song;

import java.util.ArrayList;
import java.util.List;

public class QueueViewModel extends ViewModel {
    private List<Song> playingQueue = new ArrayList<Song>();
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


        mPlayingQueue.postValue(list);
    }


}
