package com.gperlantz.cdsv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gperlantz.cdsv.adapters.PlaylistAdapter;
import com.gperlantz.cdsv.models.Playlist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createContent();
    }

    protected void onResume() {
        super.onResume();
        createContent();
    }

    public void createContent() {
        ArrayList<Playlist> files = new ArrayList<>();
        //read the first line of each csv file and add it to the list
        for (String file : this.fileList()) { //get all files in the app's directory
            if (file.endsWith(".csv")) { //check if the file is a csv file
                try {
                    BufferedReader br = new BufferedReader(new FileReader(new File(this.getFilesDir(), file)));
                    String line = br.readLine();
                    String[] values = line.split(";");
                    files.add(new Playlist(values[0], values[1], values[2], values[3], values[4], file)); //add the playlist to the list, the values are in order
                } catch (IOException e) {
                    Toast.makeText(this, "Error when reading file, somehow", Toast.LENGTH_SHORT).show();
                }
            }
        }
        TextView noLists = findViewById(R.id.tvNoLists);
        RecyclerView rv = findViewById(R.id.rvLists);
        if (files.size() == 0) { //if there are no files, encourage the user to create one
            rv.setVisibility(RecyclerView.GONE);
            noLists.setVisibility(TextView.VISIBLE);
        } else {
            noLists.setVisibility(TextView.GONE);
            rv.setVisibility(RecyclerView.VISIBLE);
            rv.setLayoutManager(new LinearLayoutManager(this));
            PlaylistAdapter adapter = new PlaylistAdapter(files, this);
            rv.setAdapter(adapter);
        }
        //grab the fab and set its onClickListener
        FloatingActionButton fab = findViewById(R.id.fabCreate);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CreateList.class);
            startActivity(intent);
        });
    }
}