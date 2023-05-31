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

public class Saved_MovieRVAdapter extends RecyclerView.Adapter<Saved_MovieRVAdapter.Saved_MovieVH> {
    ArrayList<Film> arrayList = new ArrayList<>();

    public Saved_MovieRVAdapter(ArrayList<Film> arrayList) {
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public Saved_MovieVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_movie_rvholder,parent,false);
        return new Saved_MovieVH(view);
    }

    @Override
    public int getItemCount() {
        if (arrayList != null)
        return arrayList.size();
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull Saved_MovieVH holder, int position) {
        Film items = arrayList.get(position);
        holder.imageView.setImageBitmap(items.getAnhFilm());
        holder.title.setText(items.getTenFilm());
        holder.year.setText("Khởi chiếu: "+items.getNgayChieu());
        holder.duration.setText(items.getThoiLuongFilm());
        holder.rate.setText(items.getDanhGia()+"/10");
        if(items.getDaXem()==1)
            holder.watched.setVisibility(View.VISIBLE);

    }

    class Saved_MovieVH extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title,duration,rate,year,watched;
        public Saved_MovieVH(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.saved_movie_image);
            title = itemView.findViewById(R.id.saved_tv_title);
            duration = itemView.findViewById(R.id.saved_movie_duration);
            rate = itemView.findViewById(R.id.saved_movie_rate);
            year = itemView.findViewById(R.id.saved_movie_release);
            watched = itemView.findViewById(R.id.saved_movie_watched);
        }
    }
}
