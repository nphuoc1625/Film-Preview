package com.example.reviewphim.Main.Home;

import static com.example.reviewphim.R.drawable.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.reviewphim.AllPhim;
import com.example.reviewphim.ChitietPhim;
import com.example.reviewphim.Object.Film;
import com.example.reviewphim.R;
import com.example.reviewphim.RecyclerItemClickListener;
import com.example.reviewphim.Test;
import com.google.android.flexbox.FlexboxItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Homefrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Homefrag extends Fragment {
    Button btn_moretrend,btn_morepopu;
    RecyclerView rv_trend;
    RecyclerView rv_popu;
    RadioGroup radioGroup;
    ArrayList<Film> movie_trend = new ArrayList<>();
    ArrayList<Film> movie_popu= new ArrayList<>();
    ArrayList<Film> tv_trend= new ArrayList<>();
    ArrayList<Film> tv_popu= new ArrayList<>();

    HomeRVAdapter adapter_trend;
    HomeRVAdapter adapter_popu;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Homefrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Homefrag.
     */
    // TODO: Rename and change types and number of parameters
    public static Homefrag newInstance(String param1, String param2) {
        Homefrag fragment = new Homefrag();
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
        return inflater.inflate(R.layout.fragment_homefrag, container, false);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        radioGroup = view.findViewById(R.id.home_radiogroup);
        btn_moretrend = view.findViewById(R.id.home_more_trending);
        btn_morepopu = view.findViewById(R.id.home_more_popular);
        rv_trend = view.findViewById(R.id.home_rv_trending);
        rv_popu = view.findViewById(R.id.home_rv_popu);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                GetFilm getFilm = new GetFilm();
                if (i == R.id.home_moviebtn){
                    if(movie_popu.isEmpty()||movie_trend.isEmpty()){
                        //get trend movies
                        getFilm = new GetFilm();
                        getFilm.execute("00");
                        movie_trend = getFilm.getFilms();
                        //get popular movies
                        getFilm = new GetFilm();
                        getFilm.execute("01");
                        movie_popu = getFilm.getFilms();
                    }
                    adapter_trend = new HomeRVAdapter(movie_trend);
                    adapter_popu = new HomeRVAdapter(movie_popu);

                }
                if (i ==R.id.home_tvshowbtn){
                    if (tv_trend.isEmpty()||tv_popu.isEmpty()){
                        //get trend tvshow
                        getFilm = new GetFilm();
                        getFilm.execute("10");
                        tv_trend = getFilm.getFilms();
                        //get popular tvshow
                        getFilm = new GetFilm();
                        getFilm.execute("11");
                        tv_popu = getFilm.getFilms();
                    }
                    adapter_trend = new HomeRVAdapter(tv_trend);
                    adapter_popu = new HomeRVAdapter(tv_popu);
                }
                rv_trend.setAdapter(adapter_trend);
                rv_popu.setAdapter(adapter_popu);
                rv_trend.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                rv_popu.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

            }
        });
        radioGroup.check(R.id.home_moviebtn);
        //More button
        btn_moretrend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AllPhim.class);
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.home_moviebtn:{
                        intent.putExtra("more","trend_movie");
                        break;
                    }
                    case R.id.home_tvshowbtn:{
                        intent.putExtra("more","trend_tv");
                    }

                }

                startActivity(intent);
            }
        });
        btn_morepopu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AllPhim.class);
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.home_moviebtn:{
                        intent.putExtra("more","popu_movie");
                        break;
                    }
                    case R.id.home_tvshowbtn:{
                        intent.putExtra("more","popu_tv");
                    }

                }
                startActivity(intent);
            }
        });
        rv_trend.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), ChitietPhim.class);
                intent.putExtra("from","other");
                if (radioGroup.getCheckedRadioButtonId()==R.id.home_moviebtn){
                    intent.putExtra("id",movie_trend.get(position).getIDFilm());
                    intent.putExtra("filmtype",0);
                }
                else{
                    intent.putExtra("id",tv_trend.get(position).getIDFilm());
                    intent.putExtra("filmtype",1);
                }
                startActivity(intent);
            }
        }));
        rv_popu.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), ChitietPhim.class);
                intent.putExtra("from","other");
                if (radioGroup.getCheckedRadioButtonId()==R.id.home_moviebtn){
                    intent.putExtra("id",movie_popu.get(position).getIDFilm());
                    intent.putExtra("filmtype",0);
                }
                else{
                    intent.putExtra("id",tv_popu.get(position).getIDFilm());
                    intent.putExtra("filmtype",1);
                }
                startActivity(intent);
            }
        }));
    }

    private class GetFilm extends AsyncTask<String,Void,ArrayList<Film>>{

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            adapter_trend.notifyDataSetChanged();
            adapter_popu.notifyDataSetChanged();

        }

        ArrayList<Film> films = new ArrayList<>();
        @Override
        protected ArrayList<Film> doInBackground(String... strings) {
            String gettrendingmovies = "https://api.themoviedb.org/3/trending/movie/week?api_key=4ff40b787b9cfad21269e0fa386e7a9e&language=vi";
            String gettrendingtvshows = "https://api.themoviedb.org/3/trending/tv/week?api_key=4ff40b787b9cfad21269e0fa386e7a9e&language=vi";
            String getpopularmovies = "https://api.themoviedb.org/3/movie/popular?api_key=4ff40b787b9cfad21269e0fa386e7a9e&language=en-US&page=1";
            String getpopulartvshows = "https://api.themoviedb.org/3/tv/popular?api_key=4ff40b787b9cfad21269e0fa386e7a9e&language=en-US&page=1";
            URL url = null;
            try {
                switch (strings[0]){
                    case "00" :{//00 mean get trending movies
                        url = new URL(gettrendingmovies);
                    }
                    break;
                    case "01":{//01 mean get popular movies
                        url = new URL(getpopularmovies);
                    }
                    break;
                    case "10" :{//0 mean get trending tvshows
                        url = new URL(gettrendingtvshows);
                    }
                    break;
                    case "11" :{//0 mean get popular tvshows
                        url = new URL(getpopulartvshows);
                    }
                    break;
                }
                InputStream inputStream = url.openStream();
                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String downloadedstring;
                for(String line;(line = r.readLine())!= null;){
                    stringBuilder.append(line).append("/n");
                }
                inputStream.close();
                downloadedstring = stringBuilder.toString();
                JSONObject fullJson = new JSONObject(downloadedstring);
                //get 10 films
                JSONArray json10films = fullJson.getJSONArray("results");
                for (int i = 0 ; i<=4;++i){
                    JSONObject jsonObject = json10films.getJSONObject(i);
                    Film film = new Film();
                    if (strings[0].equals("00")||strings[0].equals("01"))
                    {
                        film.setTenFilm(jsonObject.getString("title"));
                        film.setLoaiFilm(0);
                    }

                    else{
                        film.setTenFilm(jsonObject.getString("name"));
                        film.setLoaiFilm(1);
                    }
                    film.setIDFilm(jsonObject.getInt("id"));
                    url = new URL("https://image.tmdb.org/t/p/w500"+jsonObject.getString("poster_path"));
                    inputStream = url.openStream();
                    film.setAnhFilm(BitmapFactory.decodeStream(inputStream));
                    inputStream.close();
                    films.add(film);
                    publishProgress();
                }
                return films;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }



            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Film> films) {
            super.onPostExecute(films);

        }
        public ArrayList<Film> getFilms(){
            return films;
        }
    }


}