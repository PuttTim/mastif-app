package com.example.mastif.Objects;

import java.util.ArrayList;
import java.util.List;

public class SongCollection {
    List<Song> libraryList = new ArrayList<Song>();

    public SongCollection() {
        libraryList.add(new Song ("0", "Eyes", "Anonymouz",
                "https://cdn.discordapp.com/attachments/853989212721774652/866965934442676274/Anonymouz_-_Eyes.jpg",
                "https://cdn.discordapp.com/attachments/853989212721774652/866965313890025482/Anonymouz_-_Eyes_.mp3"));
        libraryList.add(new Song("1", "UWU", "Chevy",
                "https://i.scdn.co/image/ab67616d0000b2730c84e423b7547f3ee6e3c719",
                "https://p.scdn.co/mp3-preview/c0d1baa42c5a7b6bf757a5b3f7877983cb273271?cid=774b29d4f13844c495f206cafdad9c86"));
        libraryList.add(new Song("2", "Lightning Bug", "Dark Cat",
                "https://i.scdn.co/image/ab67616d0000b2730a3fc1521ba5994f799beb92",
                "https://p.scdn.co/mp3-preview/b79085062346d705ca579f9a18153c61b0cb5d2e?cid=774b29d4f13844c495f206cafdad9c86"));
    }

    public List<Song> getList() {
        return libraryList;
    }

//    public Song getSong(int songId) {
//        return libraryList.get(songId);
//    }
}