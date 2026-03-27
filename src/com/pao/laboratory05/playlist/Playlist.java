package com.pao.laboratory05.playlist;

import java.util.Arrays;

public class Playlist {
    private String name;
    private Song[] songs;

    public Playlist(String name) {
        this.name = name;
        this.songs = new Song[0];
    }

    public void addSong(Song song) {
        Song[] copy = new Song[songs.length + 1];
        System.arraycopy(songs, 0, copy, 0, songs.length);
        copy[songs.length] = song;
        songs = copy;
    }

    public void printSortedByTitle() {
        Song[] copy = songs.clone();
        Arrays.sort(copy);
        for (Song s : copy) {
            System.out.println(s);
        }
    }

    public void printSortedByDuration() {
        Song[] copy = songs.clone();
        Arrays.sort(copy, new SongDurationComparator());
        for (Song s : copy) {
            System.out.println(s);
        }
    }

    public int getTotalDuration() {
        int total_seconds = 0;
        for (Song s : songs) {
            total_seconds += s.durationSeconds();
        }
        return total_seconds;
    }
    public String getName() {
        return name;
    }
}
