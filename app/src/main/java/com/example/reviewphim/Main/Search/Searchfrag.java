package com.example.reviewphim.Main.Search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.reviewphim.R;
import com.example.reviewphim.RecyclerItemClickListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Searchfrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Searchfrag extends Fragment {
    Button apply,clear;
    SearchView searchView;
    RadioGroup type;
    RecyclerView rv;
    int checked =0;
    SearchRVAdapter adapter;
    ArrayList<Genre> genres = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Searchfrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Searchfrag.
     */
    // TODO: Rename and change types and number of parameters
    public static Searchfrag newInstance(String param1, String param2) {
        Searchfrag fragment = new Searchfrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_searchfrag, container, false);
    }
    

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = view.findViewById(R.id.search_searchview);
        type = view.findViewById(R.id.search_radiogroup_loai);
        rv = view.findViewById(R.id.discover_rv);

//        //apply button
//        apply.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast toast = Toast.makeText(getContext(),"Okee",Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        });
        //search view
       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String s) {
               return false;
           }

           @Override
           public boolean onQueryTextChange(String s) {
               if (s.isEmpty()){
                   if (checked==0)
                   apply.setEnabled(false);
               }
               else
                   apply.setEnabled(true);
               return false;
           }
       });



        //Recyler View



    }



}