package com.gperlantz.cdsv.models;

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Song {
    public String title;
    public String artist;
    public int minutes, seconds;
    public String link;

    public Song(String title, String artist, int minutes, int seconds, String link) {
        this.title = title;
        this.artist = artist;
        this.minutes = minutes;
        this.seconds = seconds;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String toCsv() {
        return title + ";" + artist + ";" + minutes + ";" + seconds + ";" + link;
    }
}
