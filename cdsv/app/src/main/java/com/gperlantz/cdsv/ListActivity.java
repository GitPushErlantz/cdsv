package com.gperlantz.cdsv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gperlantz.cdsv.adapters.SongAdapter;
import com.gperlantz.cdsv.models.Song;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String fileName = getIntent().getExtras().getString("filename");
        setTheme(R.style.orange);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        readContent(fileName);
    }

    protected void onResume() {
        super.onResume();
        String fileName = getIntent().getExtras().getString("filename");
        readContent(fileName);
    }

    public void readContent(String file) {
        // These are just the default values, they will be overwritten
        // They should never be seen by the user
        String listType = "Unknown";
        String listColor = "Teal";
        String listName = "List name";
        String authorName = "Artist name";
        ArrayList<Song> songs = new ArrayList<>();
        String fileName = getIntent().getExtras().getString("filename");
        File directory = this.getFilesDir();
        // Read the file
        try (FileReader fr = new FileReader(new File(directory, fileName))) {
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                String[] values = line.split(";");
                if (values[0].startsWith("cdsv")) { // If the line is the header, read the values. The file has to be made for cdsv, otherwise it will show the default values
                    listType = values[1]; // Get the icon
                    listColor = values[2]; // Get the color (it doesn't even work)
                    listName = values[3]; // Get the list name
                    authorName = values[4]; // Get the author name
                } else {
                    songs.add(new Song(values[0], values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3]), values[4]));
                }
                line = br.readLine();
            }
        } catch (IOException e) { //This should never happen
            Toast.makeText(this, "Error when reading file, somehow", Toast.LENGTH_SHORT).show();
            songs = new ArrayList<>();
        }
        // Set the icon
        ImageView iv = findViewById(R.id.ivLogo);
        if (listType.equals("Album")) {
            iv.setImageResource(R.drawable.album49);
        } else if (listType.equals("Mix")) {
            iv.setImageResource(R.drawable.mix49);
        } else {
            iv.setImageResource(R.drawable.logo49);
        }
        // (Attempt to) set the color of the list
        // yup, this is definitely rolling out later
        LinearLayout ll = findViewById(R.id.llList);
        switch (listColor) {
            case "orange":
                setTheme(R.style.orange);
                break;
            case "blue":
                setTheme(R.style.blue);
                break;
            case "purple":
                setTheme(R.style.purple);
                break;
            case "bw":
                setTheme(R.style.bw);
                break;
            default:
                setTheme(R.style.teal);
                break;
        }
        // Set the list name and author name
        TextView tvTitle = findViewById(R.id.tvTitlebarTitle);
        tvTitle.setText(listName);
        TextView tvAuthor = findViewById(R.id.tvAuthor);
        tvAuthor.setText(authorName);

        // Set up the recycler view
        RecyclerView rv = findViewById(R.id.rvSongs);
        FloatingActionButton fab = findViewById(R.id.fabAddSongs);
        // If the list is empty though, we don't want to show the recycler view
        if (songs.size() == 0) {
            Toast.makeText(this, "No songs in this list", Toast.LENGTH_SHORT).show();
        } else {
            rv.setLayoutManager(new LinearLayoutManager(this));
            SongAdapter adapter = new SongAdapter(songs, this);
            rv.setAdapter(adapter);
        }
        // Set an onclick listener for the add songs fab
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddSongs.class);
            intent.putExtra("filename", file);
            startActivity(intent);
        });
    }

}