package com.example.reviewphim;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reviewphim.Object.DBHelper;
import com.example.reviewphim.Object.Film;
import com.example.reviewphim.Object.People;

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

public class ChitietPhim extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ActorRVAdapter adapter;
    TextView name, release, rate, season_time, episode, genre, director, gioithieu;
    ImageView imageView;
    ArrayList<People> peoples = new ArrayList<>();
    CheckBox save, watched;
    DBHelper dbHelper;
    ScrollView scrollView;
    ProgressBar loading;
    Film film = new Film();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_phim);
        //find view
        imageView = findViewById(R.id.chitietphim_image);
        name = findViewById(R.id.chitietphim_name);
        release = findViewById(R.id.chitietphim_release);
        rate = findViewById(R.id.chitietphim_rate);
        season_time = findViewById(R.id.chitietphim_season_time);
        episode = findViewById(R.id.chitietphim_episode);
        genre = findViewById(R.id.chitietphim_genre);
        director = findViewById(R.id.chitietphim_director);
        recyclerView = findViewById(R.id.chititetphim_rv);
        gioithieu = findViewById(R.id.chitietphim_nd);
        save = findViewById(R.id.chitietphim_save);
        watched = findViewById(R.id.chitietphim_watched);
        scrollView = findViewById(R.id.chitietphim_scrollview);
        loading = findViewById(R.id.chitietphim_loading);

        Intent intent = getIntent();

        //Setting up TOOLBAR
        toolbar = findViewById(R.id.chitietphim_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_backarrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //add Database
        dbHelper = new DBHelper(getApplicationContext());

        //Get infomation
        Integer id = intent.getIntExtra("id", 0);
        String from = intent.getStringExtra("from");
        int filmtype = intent.getIntExtra("filmtype", 0);


        if (from.equals("saved")) {
            film = dbHelper.GetFilm(id);

            peoples = dbHelper.GetFilmActors(film.getIDFilm());
            name.setText(film.getTenFilm());
            imageView.setImageBitmap(film.getAnhFilm());
            release.setText(film.getNgayChieu());
            rate.setText(film.getDanhGia());
            director.setText(film.getDaoDienFilm());
            genre.setText(film.getTheLoaiFilm());
            if (film.getLoaiFilm()==0){
                season_time.setText(film.getThoiLuongFilm());
            }else{
                season_time.setText(film.getMuaFilm());
                episode.setText(film.getSoTapFilm());
            }
            gioithieu.setText(film.getGioiThieuFilm());
            save.setChecked(true);
            if (film.getDaXem() == 1)
                watched.setChecked(true);
            if (dbHelper.CheckDaXem(film.getIDFilm())) {
                save.setChecked(true);
            }
            loading.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);

        } else {
            String input = id.toString();
            if (filmtype == 0) {
                GetMovieDetail get = new GetMovieDetail();
                get.execute(input);
                film = get.GetFilm();
            } else {
                GetTVDetail get = new GetTVDetail();
                get.execute(input);
            }
        }


        //save
        Film finalFilm = film;
        save.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setResult(RESULT_OK);

                DeleteFilm deleteFilm = new DeleteFilm();
                AddFilm addFilm = new AddFilm();
                if (!b) {
                    deleteFilm.execute();
                    Toast.makeText(getApplicationContext(),"Đã bỏ lưu",Toast.LENGTH_SHORT);
                    watched.setChecked(false);
                } else {
                    addFilm.execute();
                    Toast.makeText(getApplicationContext(),"Đã lưu",Toast.LENGTH_SHORT);
                }

            }
        });

        //watched
        watched.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setResult(RESULT_OK);
                AddFilm addFilm = new AddFilm();
                if (!b) {
                    dbHelper.SetDaXem(finalFilm.getIDFilm(), 0);
                } else {
                    addFilm.execute();
                    dbHelper.SetDaXem(finalFilm.getIDFilm(), 1);
                    save.setChecked(true);
                }
            }
        });

        //set RV
        if (from.equals("saved")) {
            peoples = dbHelper.GetFilmActors(id);
        } else {
            GetActors getActors = new GetActors();
            String[] strings = new String[2];
            if (filmtype == 0) {
                strings[0] = "movie";
            } else {
                strings[0] = "tv";
            }
            strings[1] = id.toString();
            getActors.execute(strings);

        }
        adapter = new ActorRVAdapter(peoples);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent1 = new Intent(ChitietPhim.this, ChiTietPeople.class);
                intent1.putExtra("id", peoples.get(position).getIDPeople());
                if (from.equals("other"))
                 intent1.putExtra("from","other");
                else
                    intent1.putExtra("from","saved");
                startActivity(intent1);
            }
        }));
    }

    private class GetMovieDetail extends AsyncTask<String, Void, Film> {
        Film film = new Film();

        public Film GetFilm() {
            return film;
        }

        @Override
        protected Film doInBackground(String... strings) {
            String getdetail = "https://api.themoviedb.org/3/movie/" + strings[0] + "?api_key=4ff40b787b9cfad21269e0fa386e7a9e&language=vi";
            URL url;
            try {
                url = new URL(getdetail);
                InputStream inputStream = url.openStream();
                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String downloadedstring;
                for (String line; (line = r.readLine()) != null; ) {
                    stringBuilder.append(line).append("/n");
                }
                inputStream.close();
                downloadedstring = stringBuilder.toString();
                JSONObject fullJson = new JSONObject(downloadedstring);
                film.setIDFilm(fullJson.getInt("id"));
                film.setLoaiFilm(0);
                film.setTenFilm(fullJson.getString("title"));
                film.setTenAnhFilm(fullJson.getString("poster_path"));
                url = new URL("https://image.tmdb.org/t/p/w500" + fullJson.getString("poster_path"));
                inputStream = url.openStream();
                film.setAnhFilm(BitmapFactory.decodeStream(inputStream));
                inputStream.close();
                film.setDanhGia(String.valueOf(fullJson.getDouble("vote_average")));
                film.setNgayChieu(fullJson.getString("release_date"));
                int runtime = fullJson.getInt("runtime");
                String time = String.valueOf(runtime / 60 + "h" + runtime % 60 + "m");
                film.setThoiLuongFilm(time);

                JSONArray genres = fullJson.getJSONArray("genres");
                String genre = "";
                for (int i = 0; i < genres.length(); ++i) {
                    JSONObject jsonObject = genres.getJSONObject(i);
                    genre += jsonObject.getString("name");
                    if (i == genres.length() - 1)
                        genre += ".";
                    else
                        genre += ",";
                }
                film.setTheLoaiFilm(genre);
                film.setGioiThieuFilm(fullJson.getString("overview"));
                return film;
            } catch (MalformedURLException | JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Film ref) {
            super.onPostExecute(film);
            name.setText(film.getTenFilm());
            imageView.setImageBitmap(film.getAnhFilm());
            release.setText(film.getNgayChieu());
            rate.setText(film.getDanhGia());
            director.setText(film.getDaoDienFilm());
            genre.setText(film.getTheLoaiFilm());
            season_time.setText(film.getThoiLuongFilm());
            gioithieu.setText(film.getGioiThieuFilm());
            if (dbHelper.CheckSavedFilm(film.getIDFilm())) {
                save.setChecked(true);
            }
            if (dbHelper.CheckDaXem(film.getIDFilm())) {
                save.setChecked(true);
            }
            loading.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private class GetTVDetail extends AsyncTask<String, Void, Film> {
        Film film = new Film();

        @Override
        protected Film doInBackground(String... strings) {
            String getdetail = "https://api.themoviedb.org/3/tv/" + strings[0] + "?api_key=4ff40b787b9cfad21269e0fa386e7a9e";
            URL url;
            try {
                url = new URL(getdetail);
                InputStream inputStream = url.openStream();
                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String downloadedstring;
                for (String line; (line = r.readLine()) != null; ) {
                    stringBuilder.append(line).append("/n");
                }
                inputStream.close();
                downloadedstring = stringBuilder.toString();

                JSONObject fullJson = new JSONObject(downloadedstring);

                film.setIDFilm(fullJson.getInt("id"));
                film.setLoaiFilm(1);
                film.setTenFilm(fullJson.getString("name"));
                film.setTenAnhFilm(fullJson.getString("poster_path"));
                url = new URL("https://image.tmdb.org/t/p/w500" + fullJson.getString("poster_path"));
                inputStream = url.openStream();
                film.setAnhFilm(BitmapFactory.decodeStream(inputStream));
                inputStream.close();
                film.setDanhGia(String.valueOf(fullJson.getDouble("vote_average"))+"/10");
                film.setNgayChieu(fullJson.getString("first_air_date"));
                film.setMuaFilm(fullJson.getInt("number_of_seasons"));
                film.setSoTapFilm(fullJson.getInt("number_of_episodes"));

                JSONArray genres = fullJson.getJSONArray("genres");
                String genre = "";
                for (int i = 0; i < genres.length(); ++i) {
                    JSONObject jsonObject = genres.getJSONObject(i);
                    genre += jsonObject.getString("name");
                    if (i == genres.length() - 1)
                        genre += ".";
                    else
                        genre += ",";
                }
                film.setTheLoaiFilm(genre);
                film.setGioiThieuFilm(fullJson.getString("overview"));

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Film ref) {
            super.onPostExecute(film);
            name.setText(film.getTenFilm());
            imageView.setImageBitmap(film.getAnhFilm());
            release.setText(film.getNgayChieu());
            rate.setText(film.getDanhGia());
            director.setText(film.getDaoDienFilm());
            genre.setText(film.getTheLoaiFilm());

            gioithieu.setText(film.getGioiThieuFilm());
            if (dbHelper.CheckSavedFilm(film.getIDFilm())) {
                save.setChecked(true);
            }
            if (dbHelper.CheckDaXem(film.getIDFilm())) {
                save.setChecked(true);
            }
            loading.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private class GetActors extends AsyncTask<String, Void, ArrayList<People>> {
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            adapter.notifyDataSetChanged();
        }


        @Override
        protected ArrayList<People> doInBackground(String... strings) {
            String getmovieActor = "https://api.themoviedb.org/3/movie/" + strings[1] + "/credits?api_key=4ff40b787b9cfad21269e0fa386e7a9e&language=vi";
            String getTVACTOR = "https://api.themoviedb.org/3/tv/" + strings[1] + "/credits?api_key=4ff40b787b9cfad21269e0fa386e7a9e&language=vi";
            URL url = null;
            try {
                if (strings[0].equals("movie"))
                    url = new URL(getmovieActor);
                else
                    url = new URL(getTVACTOR);

                InputStream inputStream = url.openStream();
                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String downloadedstring;
                for (String line; (line = r.readLine()) != null; ) {
                    stringBuilder.append(line).append("/n");
                }
                inputStream.close();
                downloadedstring = stringBuilder.toString();
                JSONObject fullJson = new JSONObject(downloadedstring);
                JSONArray casts = fullJson.getJSONArray("cast");
                for (int i = 0;i<casts.length(); ++i) {
                    if (i<5){
                        JSONObject cast = casts.getJSONObject(i);
                        People people = new People();
                        people.setIDPeople(cast.getInt("id"));
                        people.setTen(cast.getString("name"));
                        people.setTenAnh(cast.getString("profile_path"));
                        url = new URL("https://image.tmdb.org/t/p/w500" + cast.getString("profile_path"));
                        inputStream = url.openStream();
                        people.setAnh(BitmapFactory.decodeStream(inputStream));
                        inputStream.close();
                        people.setTenVaiDien(cast.getString("character"));
                        peoples.add(people);
                        publishProgress();
                    }

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

    }

    private class AddFilm extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            dbHelper.AddFilm(film, peoples);
            return null;
        }
    }

    private class DeleteFilm extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            dbHelper.DeleteFilm(film);
            return null;
        }
    }
}