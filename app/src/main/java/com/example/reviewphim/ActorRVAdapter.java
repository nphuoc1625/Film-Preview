package com.example.reviewphim;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reviewphim.Object.ImageUltilities;
import com.example.reviewphim.Object.People;

import java.util.ArrayList;

public class ActorRVAdapter extends RecyclerView.Adapter<ActorRVAdapter.ActorVH>
{
    ArrayList<People> arrayList;
    public ActorRVAdapter(ArrayList<People> dienViens) {
        arrayList = dienViens;
    }

    @NonNull
    @Override
    public ActorVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.actor_rvholder,parent,false);
        return new ActorVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorVH holder, int position) {
        People people = arrayList.get(position);
        holder.imageView.setImageBitmap(people.getAnh());
        holder.Tenthat.setText(people.getTen());
        holder.VaiDien.setText(people.getTenVaiDien());
    }

    @Override
    public int getItemCount() {
        if (arrayList!=null)
        return arrayList.size();
        return 0;
    }

    public class ActorVH extends RecyclerView.ViewHolder {
        ImageView   imageView;
        TextView Tenthat,VaiDien;
        public ActorVH(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.rvholder_actor_img);
            Tenthat = itemView.findViewById(R.id.rvholder_actor_name);
            VaiDien = itemView.findViewById(R.id.rvholder_actor_as);
        }
    }
}
