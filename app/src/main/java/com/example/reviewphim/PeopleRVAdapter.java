package com.example.reviewphim;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reviewphim.Object.People;

import java.util.ArrayList;

public class PeopleRVAdapter extends RecyclerView.Adapter<PeopleRVAdapter.DirectorVH> {
    public ArrayList<People> arrayList;
    public PeopleRVAdapter(ArrayList<People> daoDiens){
        arrayList = daoDiens;
    }
    @NonNull
    @Override
    public DirectorVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.actor_rvholder,parent,false);
        return new DirectorVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DirectorVH holder, int position) {
        People people = arrayList.get(position);
        holder.imageView.setImageBitmap(people.getAnh());
        holder.name.setText(people.getTen());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class DirectorVH extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        public DirectorVH(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.rvholder_actor_img);
            name = itemView.findViewById(R.id.rvholder_actor_name);
        }
    }
}
