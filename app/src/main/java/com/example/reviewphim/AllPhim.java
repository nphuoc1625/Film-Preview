package com.example.reviewphim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.reviewphim.Main.Home.HomeRVAdapter;
import com.example.reviewphim.Object.Film;
import com.google.android.flexbox.FlexboxLayoutManager;

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

public class AllPhim extends AppCompatActivity {

    Toolbar tb  ;
    RecyclerView rv;
    HomeRVAdapter adapter;
    ArrayList<Film> arrayList;
    int count=0,page=1,maxresult=0;
    String gettrendingmovies = "https://api.themoviedb.org/3/trending/movie/week?api_key=4ff40b787b9cfad21269e0fa386e7a9e&language=vi   ";
    String gettrendingtvshows = "https://api.themoviedb.org/3/trending/tv/week?api_key=4ff40b787b9cfad21269e0fa386e7a9e&language=vi";
    String getpopularmovies = "https://api.themoviedb.org/3/movie/popular?api_key=4ff40b787b9cfad21269e0fa386e7a9e&language=vi";
    String getpopulartvshows = "https://api.themoviedb.org/3/tv/popular?api_key=4ff40b787b9cfad21269e0fa386e7a9e&language=vi";
    private static int firstVisibleInListview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_phim);
        tb = findViewById(R.id.allphim_tb);
        rv = findViewById(R.id.allphim_rv);

        //setting toolbar
        tb.setNavigationIcon(R.drawable.ic_backarrow);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //Intent info
        Intent intent = getIntent();
        String more = intent.getStringExtra("more");
        //Asyncs task
        ALLFilm_GetFilms get = new ALLFilm_GetFilms();
        get.execute(more);
        arrayList = get.films;
        //setting rv
        adapter = new HomeRVAdapter(arrayList);
        rv.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        rv.setLayoutManager(layoutManager);
        rv.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    System.out.println("idleing");
                    AllPhim.ALLFilm_GetFilms update = new ALLFilm_GetFilms();
                    if (layoutManager.findLastVisibleItemPosition()==arrayList.size()-1){
                        update.execute(more);
                    }
                }

            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


            }
        });

        //Onitemclick
        rv.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent1 = new Intent(getApplicationContext(),ChitietPhim.class);

                int filmtype ;
                if (arrayList.get(position).getLoaiFilm()==0)
                    filmtype = 0;
                else
                    filmtype = 1;
                intent1.putExtra("id",arrayList.get(position).getIDFilm());
                intent1.putExtra("from","other");
                intent1.putExtra("filmtype",filmtype);
                startActivity(intent1);
            }
        }));
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        rv.scrollToPosition(0);
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    class ALLFilm_GetFilms extends AsyncTask<String,Film,ArrayList<Film>> {


        @Override
        protected void onProgressUpdate(Film... films) {
            super.onProgressUpdate(films);
            adapter.notifyDataSetChanged();

        }



        ArrayList<Film> films = new ArrayList<>();
        @Override
        protected ArrayList<Film> doInBackground(String... strings) {
            URL url = null;
            try {

                switch (strings[0]){
                    case "trend_movie":{
                        url = new URL(gettrendingmovies);
                        break;
                    }
                    case "popu_movie":{
                        url = new URL(getpopularmovies);
                        break;
                    }
                    case "trend_tv":{
                        url = new URL(gettrendingtvshows);
                        break;
                    }
                    case "popu_tv":{
                        url = new URL(getpopulartvshows);
                    }
                }
                url = new URL(url.toString()+"&page="+page);
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
                maxresult = json10films.length();
                for (int i = 0 ; i<5&&count<maxresult;++i){
                    JSONObject jsonObject = json10films.getJSONObject(count);
                    Film film = new Film();
                    if (strings[0].equals("trend_movie")||strings[0].equals("popu_movie")){
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
                    System.out.println(film.getTenFilm());
                    arrayList.add(film);
                    publishProgress();
                    ++count;

                }
                if (count==maxresult){
                    page++;
                    count=0;
                }

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

    }
}