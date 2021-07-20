package com.example.mastif;

import java.util.ArrayList;
import java.util.List;

public class SongCollection {
    List<Song> libraryList = new ArrayList<Song>();

    public SongCollection() {

        Song Eyes = new Song("1",
                "Eyes",
                "Anonymouz",
                "https://cdn.discordapp.com/attachments/853989212721774652/866965934442676274/Anonymouz_-_Eyes.jpg",
                "https://cdn.discordapp.com/attachments/853989212721774652/866965313890025482/Anonymouz_-_Eyes_.mp3");

        libraryList.add(Eyes);
    }

    public Song getSongById(int SongId) {
        return libraryList.get(SongId);
    }

    public List<Song> getLibraryList() {
        return libraryList;
    }
}