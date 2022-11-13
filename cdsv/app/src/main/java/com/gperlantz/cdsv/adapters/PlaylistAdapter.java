package com.gperlantz.cdsv.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gperlantz.cdsv.ListActivity;
import com.gperlantz.cdsv.MainActivity;
import com.gperlantz.cdsv.R;
import com.gperlantz.cdsv.models.Playlist;

import java.util.ArrayList;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    ArrayList<Playlist> files;
    Context context;

    public PlaylistAdapter(ArrayList<Playlist> playlists, MainActivity activity) {
        this.files = playlists;
        this.context = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cardview_lists, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBindViewHolder(@NonNull PlaylistAdapter.ViewHolder holder, int position) {
        final Playlist playlist = files.get(position);
        holder.listName.setText(playlist.getTitle());
        holder.listAuthor.setText(playlist.getAuthor());
        if(playlist.getType().equals("Mix")) {
            holder.ivType.setImageResource(R.drawable.mix39);
        } else if (playlist.getType().equals("Album")) {
            holder.ivType.setImageResource(R.drawable.album39);
        } else {
            holder.ivType.setImageResource(R.drawable.logo39);
        }
        // set the theme color
        // Sets an onClickListener to access the playlist by sending an intent with the filename
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ListActivity.class);
            intent.putExtra("filename", playlist.getFilename());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView listName;
        TextView listAuthor;
        ImageView ivType;
        Resources.Theme theme;
        LinearLayout ll;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listName = itemView.findViewById(R.id.txtListTitle);
            listAuthor = itemView.findViewById(R.id.txtListAuthor);
            ivType = itemView.findViewById(R.id.ivListType);
            theme = itemView.getContext().getTheme();
            ll = itemView.findViewById(R.id.llBg);
        }
    }
}
