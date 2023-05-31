package com.example.reviewphim.Main.Saved;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.reviewphim.ActorRVAdapter;
import com.example.reviewphim.ChiTietPeople;
import com.example.reviewphim.ChitietPhim;
import com.example.reviewphim.PeopleRVAdapter;
import com.example.reviewphim.Main.Saved.Film.Saved_MovieRVAdapter;
import com.example.reviewphim.Main.Saved.Film.Saved_TVRVAdapter;
import com.example.reviewphim.Object.DBHelper;
import com.example.reviewphim.Object.Film;
import com.example.reviewphim.Object.People;
import com.example.reviewphim.R;
import com.example.reviewphim.RecyclerItemClickListener;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Savedfrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Savedfrag extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;
    private RadioGroup radioGroup;
    private DBHelper dbHelper ;
    
    private Saved_MovieRVAdapter saved_movieRVAdapter;
    private Saved_TVRVAdapter saved_tvrvAdapter;
    private PeopleRVAdapter peopleRVAdapter,actorRVAdapter;

    private ArrayList<People> dienViens;
    private ArrayList<People> daoDiens ;
    private ArrayList<Film> movies;
    private ArrayList<Film> tv;
    private RecyclerView dvrv;
    private RecyclerView dtrv;

    public Savedfrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Savedfrag.
     */
    // TODO: Rename and change types and number of parameters
    public static Savedfrag newInstance(String param1, String param2) {
        Savedfrag fragment = new Savedfrag();
        Bundle args = new Bundle();
 
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false);
    }


    public void resetAdapter(){
                actorRVAdapter = new PeopleRVAdapter(dbHelper.GetALLActors());
                dvrv.setAdapter(actorRVAdapter);

                peopleRVAdapter = new PeopleRVAdapter(dbHelper.GetALLDirectors());
                dtrv.setAdapter(peopleRVAdapter);

                saved_movieRVAdapter = new Saved_MovieRVAdapter(dbHelper.GetALLMovie());
                recyclerView.setAdapter(saved_tvrvAdapter);

                saved_tvrvAdapter = new Saved_TVRVAdapter(dbHelper.GetALLTVShow());
                recyclerView.setAdapter(saved_tvrvAdapter);


    }
    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // your operation....
                       resetAdapter();
                    }
                }
            });




    public void launchChiTietPhim(Intent intent) {
        launchSomeActivity.launch(intent);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        radioGroup = view.findViewById(R.id.saved_radiogroup);
        recyclerView = view.findViewById(R.id.saved_phimrv);
        linearLayout = view.findViewById(R.id.saved_people);
        dvrv = view.findViewById(R.id.saved_actorrv);
        dtrv = view.findViewById(R.id.saved_directorrv);


        dbHelper = new DBHelper(getContext());
        dbHelper.createTable();

        // Movie and TVShow
        movies = dbHelper.GetALLMovie();
        tv = dbHelper.GetALLTVShow();

        if (movies!=null)
         saved_movieRVAdapter = new Saved_MovieRVAdapter(movies);
        if (tv!=null)
         saved_tvrvAdapter = new Saved_TVRVAdapter(tv);

        recyclerView.setLayoutManager(new FlexboxLayoutManager(getContext()));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent ;
                    intent = new Intent(recyclerView.getContext(), ChitietPhim.class);
                    int id = 0 ;
                    int filmtype = 0;
                    if (recyclerView.getAdapter() == saved_movieRVAdapter){
                        id = movies.get(position).getIDFilm();
                        filmtype = 0;
                    }
                    else if (recyclerView.getAdapter() == saved_tvrvAdapter){
                        id = tv.get(position).getIDFilm();
                        filmtype = 1;
                    }
                    intent.putExtra("filmtype",filmtype);
                    intent.putExtra("id",id);

                intent.putExtra("from","saved");
                launchChiTietPhim(intent);
            }
        }));
        //People
        daoDiens = dbHelper.GetALLDirectors();
        dienViens = dbHelper.GetALLActors();

        if (daoDiens!=null)
            actorRVAdapter = new PeopleRVAdapter(dienViens);
        if (dienViens!=null)
            peopleRVAdapter = new PeopleRVAdapter(daoDiens);

        dvrv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        dvrv.setAdapter(actorRVAdapter);
        dtrv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        dtrv.setAdapter(peopleRVAdapter);

        RecyclerItemClickListener rvonclick = new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(),ChiTietPeople.class);
                if (recyclerView.getAdapter()==actorRVAdapter) {
                    intent.putExtra("id", dienViens.get(position).getIDPeople());
                }else{
                    intent.putExtra("id", daoDiens.get(position).getIDPeople());
                }
                intent.putExtra("from","saved");
                launchChiTietPhim(intent);
            }
        });
        dvrv.addOnItemTouchListener(rvonclick);
        dtrv.addOnItemTouchListener(rvonclick);

        RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int checked = radioGroup.getCheckedRadioButtonId();
                switch (checked){
                    case R.id.saved_moviebtn:{
                        recyclerView.setAdapter(saved_movieRVAdapter);
                        linearLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        break;
                    }
                    case R.id.saved_tvseriesbtn:{
                        recyclerView.setAdapter(saved_tvrvAdapter);
                        linearLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        break;
                    }
                    case R.id.saved_peoplebtn:{
                        recyclerView.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
        };
        radioGroup.setOnCheckedChangeListener(listener);
        radioGroup.check(R.id.saved_moviebtn);
    }



}