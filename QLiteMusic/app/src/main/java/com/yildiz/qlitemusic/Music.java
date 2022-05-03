package com.yildiz.qlitemusic;

import java.io.Serializable;

public class Music implements Serializable {
    String path;
    String title;
    String duration;
    String artist;
    String albumid;
    String album;
    public Music(String path, String title, String duration, String artist, String albumid, String album) {
        this.path = path;
        this.title = title;
        this.duration = duration;
        this.artist = artist;
        this.albumid = albumid;
        this.album = album;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return albumid;
    }

    public void setAlbum(String albumid) {
        this.albumid = albumid;
    }

    public String getLastAlbum() { return album;}
    public void setLastAlbum() {this.album = album;}
}
