package com.example.reviewphim.Main.Home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reviewphim.Object.Film;
import com.example.reviewphim.R;

import java.util.ArrayList;

public class HomeRVAdapter extends RecyclerView.Adapter<HomeRVAdapter.HomeRVH> {
    ArrayList<Film> films ;

    public HomeRVAdapter(ArrayList<Film> films) {
        this.films = films;
    }

    public ArrayList<Film> getFilms(){
        return films;
    }
    @NonNull
    @Override
    public HomeRVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_vh,parent,false);
        return new HomeRVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRVH holder, int position) {
        holder.imageView.setImageBitmap(films.get(position).getAnhFilm());
        holder.textView.setText(films.get(position).getTenFilm());
        holder.progressBar.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public class HomeRVH extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        ProgressBar progressBar;
        public HomeRVH(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.home_vh_img);
            textView = itemView.findViewById(R.id.home_vh_title);
            progressBar = itemView.findViewById(R.id.home_vh_progressbar);
        }
    }
}
