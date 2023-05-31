package com.example.reviewphim.Main.Saved.Film;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reviewphim.Object.Film;
import com.example.reviewphim.R;

import java.util.ArrayList;

public class Saved_TVRVAdapter extends RecyclerView.Adapter<Saved_TVRVAdapter.Saved_TVVH> {

    ArrayList<Film> arrayList = new ArrayList<>();
    public Saved_TVRVAdapter(ArrayList<Film> arrayList) {
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public Saved_TVVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_tvseries_rvholder,parent,false);
        return new Saved_TVVH(view);
    }

    @Override
    public int getItemCount() {
        if (arrayList!=null)
        return arrayList.size();
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull Saved_TVVH holder, int position) {
        Film items = arrayList.get(position);
        holder.imageView.setImageBitmap(items.getAnhFilm());
        holder.title.setText(items.getTenFilm());
        holder.rate.setText(items.getDanhGia().toString()+"/10");
        holder.season.setText("Mùa: "+items.getMuaFilm());
        holder.episodes.setText("Tập: "+items.getSoTapFilm());
        if(items.getDaXem()==1)
            holder.watched.setVisibility(View.VISIBLE);
    }

    class Saved_TVVH extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title,rate,season,episodes,watched;
        public Saved_TVVH(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.saved_tv_image);
            title = itemView.findViewById(R.id.saved_tv_title);
            rate = itemView.findViewById(R.id.saved_tv_rate);
            season = itemView.findViewById(R.id.saved_tv_season);
            episodes = itemView.findViewById(R.id.saved_tv_episode);
            watched = itemView.findViewById(R.id.saved_tv_watched);
        }
    }
}
