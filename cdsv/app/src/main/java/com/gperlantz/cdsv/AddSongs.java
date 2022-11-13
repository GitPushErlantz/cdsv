package com.gperlantz.cdsv;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class AddSongs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_songs);
        Button save = findViewById(R.id.btnSaveSong);
        ArrayList<String> songs;
        songs = readCsvSongs();
        ArrayList<String> finalSongs = songs;
        save.setOnClickListener(view -> Save(finalSongs));
    }

    public void Save(ArrayList<String> songs) {
        String fileName = getIntent().getExtras().getString("filename");
        EditText edtTitle = findViewById(R.id.edtSongName);
        EditText edtArtist = findViewById(R.id.edtSongArtist);
        EditText edtMinutes = findViewById(R.id.edtMinutes);
        EditText edtSeconds = findViewById(R.id.edtSeconds);
        EditText edtLink = findViewById(R.id.edtSongLink);
        String title = edtTitle.getText().toString();
        String artist = edtArtist.getText().toString();
        String minutes = edtMinutes.getText().toString();
        String seconds = edtSeconds.getText().toString();
        String link = edtLink.getText().toString();
        if (title.isEmpty() || artist.isEmpty() || minutes.isEmpty() || seconds.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.noEmpty), Toast.LENGTH_SHORT).show();
        } else if (link.isEmpty()) {
            link = "NOLINK";
        } else if (!link.startsWith("https://youtu.be/") || !link.startsWith("https://youtube.com/watch?v=")) {
            Toast.makeText(this, getResources().getString(R.string.wrongLink), Toast.LENGTH_SHORT).show();
        } else if (title.contains(";") || artist.contains(";") || minutes.contains(";") || seconds.contains(";") || link.contains(";")) {
            Toast.makeText(this, getResources().getString(R.string.noSemicolon), Toast.LENGTH_SHORT).show();
        } else if (Integer.parseInt(seconds) > 59) {
            Toast.makeText(this, getResources().getString(R.string.invalidSeconds), Toast.LENGTH_SHORT).show();
        } else {
            try (PrintWriter pw = new PrintWriter(new File(this.getFilesDir(), fileName))) {
                for (String song : songs) {
                    pw.println(song);
                }
                pw.println(title + ";" + artist + ";" + minutes + ";" + seconds + ";" + link);
                Toast.makeText(this, "Song added", Toast.LENGTH_SHORT).show();
                finish();
            } catch (FileNotFoundException e) {
                Toast.makeText(this, "Error when writing to file, somehow", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

    public ArrayList<String> readCsvSongs() {
        ArrayList<String> songs = new ArrayList<>();
        String fileName = getIntent().getExtras().getString("filename");
        File directory = this.getFilesDir();
        try (FileReader fr = new FileReader(new File(directory, fileName))) {
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                Collections.addAll(songs, line);
                line = br.readLine();
            }
        } catch (IOException e) {
            Toast.makeText(this, "Error when reading file, somehow", Toast.LENGTH_SHORT).show();
            songs = new ArrayList<>();
        }
        return songs;
    }
}