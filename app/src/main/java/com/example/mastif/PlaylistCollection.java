package com.example.mastif;

import java.util.ArrayList;
import java.util.List;

public class PlaylistCollection {
    List<Playlist> playlistList = new ArrayList<Playlist>();

    public void addPlaylistCollection(String id, String name, List<Song> PlaylistContent) {
        System.out.println("before: " + playlistList.size());
        Playlist playlistToBeAdded = new Playlist(id, name, PlaylistContent);
        playlistList.add(playlistToBeAdded);
        System.out.printf("Playlist \"%s\" added \n", name);
        System.out.println("after: " + playlistList.size());
    }

    public List<Playlist> getList() {
        // System.out.println(playlistList);
        return playlistList;
    }

    public Playlist getPlaylist(int playlistId) {
        // System.out.println(playlistList.get(playlistId));
        return playlistList.get(playlistId);
    }

    public int getCollectionSize() {
        return playlistList.size();
    }

}
