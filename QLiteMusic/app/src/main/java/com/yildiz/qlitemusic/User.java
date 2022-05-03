package com.yildiz.qlitemusic;

import android.net.Uri;

import java.util.ArrayList;

public class User {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String username;
    private String password;
    private transient Uri profilePicUri;
    private ArrayList<SongList> playlists;

    public User(String name, String surname,String email, String phone,String username, String password, Uri profilePicUri) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.profilePicUri = profilePicUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Uri getProfilePicUri() {
        return profilePicUri;
    }

    public void setProfilePicUri(Uri profilePicUri) {
        this.profilePicUri = profilePicUri;
    }

    public void addPlaylist(String name, Music music){
        SongList newSongList = new SongList(name, music);
        this.playlists.add(newSongList);
    }

    public void removePlaylist(String name){
        playlists.removeIf(x -> x.getName().equals(name));
    }

    public void addToPlaylist(SongList songlist, Music music){
        songlist.addMusic(music);
    }

    public void removeFromPlaylist(SongList songlist, Music music){
        songlist.removeMusic(music);
    }
}
