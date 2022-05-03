package com.yildiz.qlitemusic;

import java.util.ArrayList;

public class SongList {
    ArrayList<Music> musics;
    String name;
    public SongList(String name, Music music){
        this.name = name;
        musics.add(music);
    }

    public ArrayList<Music> getMusics() {
        return musics;
    }

    public void setMusics(ArrayList<Music> musics) {
        this.musics = musics;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addMusic(Music music){
        musics.add(music);
    }

    public void removeMusic(Music music) { musics.remove(music); }
}
