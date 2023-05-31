package com.example.reviewphim.RVAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reviewphim.Object.Film;
import com.example.reviewphim.R;

import java.util.ArrayList;

public class FilmsOfPeopleAdapter extends RecyclerView.Adapter<FilmsOfPeopleAdapter.FilmOfPeopleVH> {
    ArrayList<Film> films = new ArrayList<>();
    public FilmsOfPeopleAdapter(ArrayList<Film> films) {
        this.films = films;

    }

    @NonNull
    @Override
    public FilmOfPeopleVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filmsofpeople_vh,parent,false);
        return new FilmOfPeopleVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmOfPeopleVH holder, int position) {
        holder.date.setText(films.get(position).getNgayChieu());
        holder.name.setText(films.get(position).getTenFilm());
    }

    @Override
    public int getItemCount() {
        if (films!=null)
            return films.size();
        return 0;
    }

    public class FilmOfPeopleVH extends RecyclerView.ViewHolder {
        TextView date,name;
        public FilmOfPeopleVH(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.filmsofpeople_date);
            name = itemView.findViewById(R.id.filmsofpeople_name);
        }
    }
}
