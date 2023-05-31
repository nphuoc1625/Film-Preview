package com.example.reviewphim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.reviewphim.Main.Home.HomeRVAdapter;
import com.example.reviewphim.Object.DBHelper;
import com.example.reviewphim.Object.Film;
import com.example.reviewphim.Object.People;
import com.example.reviewphim.RVAdapter.FilmsOfPeopleAdapter;

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

public class ChiTietPeople extends AppCompatActivity {
    ArrayList<Film> films = new ArrayList<>();
    Toolbar tb;
    NestedScrollView scrollView ;
    ProgressBar progressBar;
    ImageView imageView;
    TextView name,story,known,gender,DOB,POB;
    RecyclerView recyclerView;
    FilmsOfPeopleAdapter adapter;
    Button more;
    People people = new People();
    Context context = this;
    DBHelper db = new DBHelper(context);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_people);
        //Find view
        tb = findViewById(R.id.chitietpeople_tb);
        scrollView = findViewById(R.id.chitietpeople_scrollView);
        progressBar = findViewById(R.id.chitietpeople_loading);
        imageView = findViewById(R.id.chitietpeople_image);
        name = findViewById(R.id.chitietpeople_name);
        story = findViewById(R.id.chitietpeople_story);
        known = findViewById(R.id.chitietpeople_known);
        gender = findViewById(R.id.chitietpeople_gender);
        DOB = findViewById(R.id.chitietpeople_BD);
        POB = findViewById(R.id.chitietpeople_POB);

        more  = findViewById(R.id.chitietpeople_more);
        recyclerView = findViewById(R.id.chitietpeople_rv);

        //Setting toolbar
        tb.setNavigationIcon(R.drawable.ic_backarrow);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });

        tb.inflateMenu(R.menu.save_people);
        tb.getMenu().getItem(0).setVisible(false);
        tb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.save_people){
                    if (!db.CheckSavedPeople(people.getIDPeople())){
                        db.AddPeople(people,films);
                        tb.getMenu().getItem(0).setTitle("Đã lưu");
                    }else
                        if(db.CheckSavedPeople(people.getIDPeople())){
                        DeletePeople deletePeople = new DeletePeople();
                        deletePeople.execute(people.getIDPeople());
                        tb.getMenu().getItem(0).setTitle("Lưu");
                    }

                }
                return false;
            }
        });

        //Setting Button
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietPeople.this,AllPhim.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        Integer i = intent.getIntExtra("id",0);
        if (intent.getStringExtra("from").equals("other")){
            String id = i.toString();
            GetPPDetail getPPDetail = new GetPPDetail();
            getPPDetail.execute(id);

            GetPPFilm getPPFilm = new GetPPFilm();
            getPPFilm.execute(id);
        }else{
            people = db.GetPeople(i);
            films = db.GetActorFilms(i);
            setInfo();
            setRV();
        }


    }

    private class DeletePeople extends AsyncTask<Integer,Void,Void>{
        @Override
        protected Void doInBackground(Integer... integers) {
            db.DeletePeople(integers[0]);
            return null;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
    private class GetPPDetail extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String link = "https://api.themoviedb.org/3/person/"+strings[0]+"?api_key=4ff40b787b9cfad21269e0fa386e7a9e";
            try {
                URL url = new URL(link);
                InputStream inputStream = url.openStream();
                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String downloadedstring;
                for (String line; (line = r.readLine()) != null; ) {
                    stringBuilder.append(line).append("/n");
                }
                inputStream.close();
                downloadedstring = stringBuilder.toString();
                JSONObject oj = new JSONObject(downloadedstring);
                people.setIDPeople(oj.getInt("id"));
                people.setTen(oj.getString("name"));
                people.setTenAnh(oj.getString("profile_path"));
                url = new URL("https://image.tmdb.org/t/p/w500" + oj.getString("profile_path"));
                inputStream = url.openStream();
                people.setAnh(BitmapFactory.decodeStream(inputStream));
                inputStream.close();
                people.setTieuSu(oj.getString("biography"));
                people.setTenVaiDien(oj.getString("known_for_department"));
                people.setGioiTinh(oj.getInt("gender"));
                people.setNgaySinh(oj.getString("birthday"));
                people.setNoiSinh(oj.getString("place_of_birth"));

                publishProgress();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           setInfo();
        }
    }

    private void setInfo() {
        imageView.setImageBitmap(people.getAnh());
        name.setText(people.getTen());
        story.setText(people.getTieuSu());
        known.setText(people.getTenVaiDien());
        if (people.getGioiTinh() == 2)
            gender.setText("Nam");
        else
        if (people.getGioiTinh() == 1)
            gender.setText("Nữ");
        DOB.setText(people.getNgaySinh());
        POB.setText(people.getNoiSinh());
        if (db.CheckSavedPeople(people.getIDPeople()))
            tb.getMenu().getItem(0).setTitle("Đã lưu");

        progressBar.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
    }
    private void setRV() {
        adapter = new FilmsOfPeopleAdapter(films);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
        tb.getMenu().getItem(0).setVisible(true);
    }
    private  class  GetPPFilm extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String link = "https://api.themoviedb.org/3/person/"+strings[0]+"/combined_credits?api_key=4ff40b787b9cfad21269e0fa386e7a9e";
            try {
                URL url = new URL(link);
                InputStream inputStream = url.openStream();
                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String downloadedstring;
                for (String line; (line = r.readLine()) != null; ) {
                    stringBuilder.append(line).append("/n");
                }
                inputStream.close();
                downloadedstring = stringBuilder.toString();
                JSONObject oj = new JSONObject(downloadedstring);
                JSONArray casts = oj.getJSONArray("cast");
                for (int i = 0;i<10;++i){
                    films.add(new Film());
                    JSONObject cast = casts.getJSONObject(i);
                    String type = cast.getString("media_type");
                    if (type.equals("movie")){
                        films.get(i).setNgayChieu(cast.getString("release_date")+" :");
                        films.get(i).setTenFilm(cast.getString("title"));
                    }else{
                        films.get(i).setNgayChieu(cast.getString("first_air_date"));
                        films.get(i).setTenFilm(cast.getString("name"));
                    }
                }

                publishProgress();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            setRV();
        }
    }


}