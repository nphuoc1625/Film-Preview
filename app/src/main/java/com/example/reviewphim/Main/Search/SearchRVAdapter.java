package com.example.reviewphim.Main.Search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reviewphim.R;

import java.util.ArrayList;

public class SearchRVAdapter extends RecyclerView.Adapter<SearchRVAdapter.SearchRVHolder> {
    public SearchRVAdapter(ArrayList<Genre> arrayList) {
        this.arrayList = arrayList;
    }

    ArrayList<Genre> arrayList;

    @NonNull
    @Override
    public SearchRVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_rvholder,parent,false);
        return new SearchRVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRVHolder holder, int position) {
        Genre genre = arrayList.get(position);
        holder.btn.setText(genre.getName());
        holder.btn.setId(genre.getId());
        holder.btn.setChecked(genre.isChecked());

    }
    @Override
    public int getItemCount() {
        if (arrayList!=null)
        return arrayList.size();
        return 0;
    }

    public class SearchRVHolder extends RecyclerView.ViewHolder {
        CheckBox btn;
        public SearchRVHolder(@NonNull View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.search_rv_btn);
        }
    }
}
