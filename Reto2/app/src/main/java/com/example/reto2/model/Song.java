package com.example.reto2.model;

import java.util.ArrayList;

public class Song {


    private String title;
    private String duration;
    private String preview;
   // private ArrayList<Artista>artist;

    private Artista artist;
    private Album album;

    public Song() {
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

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public Artista getArtist() {
        return artist;
    }

    public void setArtist(Artista artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public class Artista{
        private String name;
        private String picture;

        public Artista() {
        }

        public String getName() {
            return name;
        }

        public String getPicture() {
            return picture;
        }
    }

    public class Album{
        private String title;

        public Album(){
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
