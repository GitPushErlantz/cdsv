package com.gperlantz.cdsv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gperlantz.cdsv.R;
import com.gperlantz.cdsv.models.Song;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    ArrayList<Song> songs;
    Context context;

    public SongAdapter(ArrayList<Song> songs, Context context) {
        this.songs = songs;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cardview_songs, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.ViewHolder holder, int position) {
        final Song song = songs.get(position);
        holder.songName.setText(song.getTitle());
        holder.songArtist.setText(song.getArtist());
        String duration;
        if (song.getSeconds() < 10) {
            duration = song.getMinutes() + ":0" + song.getSeconds();
        } else {
            duration = song.getMinutes() + ":" + song.getSeconds();
        }
        holder.songDuration.setText(duration);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView songName;
        TextView songArtist;
        TextView songDuration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.tvSongTitle);
            songArtist = itemView.findViewById(R.id.tvSongArtist);
            songDuration = itemView.findViewById(R.id.tvSongDuration);
        }
    }
}
