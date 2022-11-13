package com.gperlantz.cdsv.models;

public class Playlist {
    public String version, type, color, title, author, fileName;
    public Song[] songs;

    public Playlist(String version, String type, String color, String title, String author, String fileName) {
        this.version = version;
        this.type = type;
        this.color = color;
        this.title = title;
        this.author = author;
        this.fileName = fileName;
    }

    public String getVersion() {
        return version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFilename() {
        return fileName;
    }

    public void setFilename(String fileName) {
        this.fileName = fileName;
    }

    public String toCsv() {
        return version + ";" + type + ";" + color + ";" + title + ";" + author;
    }
}
