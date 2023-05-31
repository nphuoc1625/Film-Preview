package com.example.reviewphim.Main.Discover;

import android.os.AsyncTask;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.reviewphim.Main.Search.Genre;
import com.example.reviewphim.Main.Search.SearchRVAdapter;
import com.example.reviewphim.R;
import com.example.reviewphim.RecyclerItemClickListener;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.slider.RangeSlider;

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
 * Use the {@link Discover#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Discover extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SearchRVAdapter adapter;
    private ArrayList<Genre> movie = new ArrayList<>();
    private ArrayList<Genre> tv = new ArrayList<>();
    RecyclerView rv ;
    Button apply,clear ;
    TextView txtminyear,txtmaxyear,txtminrate,txtmaxrate;
    RadioGroup type;
    RangeSlider rate,year ;

    int checked =0;
    Integer minyear=1990,maxyear=2022,minrate=0,maxrate=10;
    String mdtype;
    public Discover() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Discover.
     */
    // TODO: Rename and change types and number of parameters
    public static Discover newInstance(String param1, String param2) {
        Discover fragment = new Discover();
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
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.discover_rv);
        apply = view.findViewById(R.id.discover_apdung);
        clear = view.findViewById(R.id.discover_clearbtn);
        year = view.findViewById(R.id.discover_year);
        rate = view.findViewById(R.id.discover_rate);
        type = view.findViewById(R.id.discover_type);
        txtminyear = view.findViewById(R.id.discover_minyear);
        txtmaxyear = view.findViewById(R.id.discover_maxyear);
        txtminrate = view.findViewById(R.id.discover_minrate);
        txtmaxrate = view.findViewById(R.id.discover_maxrate);

        //setting
        Getgenres getgenres = new Getgenres();
        getgenres.execute();
        //TYpe
        type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.discover_movie:{
                        mdtype = "movie";
                        adapter = new SearchRVAdapter(movie);
                        rv.setAdapter(adapter);
                        break;
                    }
                    case R.id.discover_tv:{
                        mdtype = "tv";
                        adapter = new SearchRVAdapter(tv);
                        rv.setAdapter(adapter);
                    }
                }
            }
        });
        type.check(R.id.discover_movie);

        //year slider
        year.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                 minyear = slider.getValues().get(0).intValue();
                maxyear  = slider.getValues().get(1).intValue();
                txtminyear.setText(minyear.toString());
                txtmaxyear.setText(maxyear.toString());
            }
        });
        //Rate slider
        rate.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                minrate = slider.getValues().get(0).intValue();
                maxrate  = slider.getValues().get(1).intValue();
                txtminrate.setText(minrate.toString());
                txtmaxrate.setText(maxrate.toString());
            }
        });

        //apply button
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mdtype.equals("movie"))
                System.out.println("\n" +
                        "https://api.themoviedb.org/3/discover/" + mdtype+
                        "?api_key=4ff40b787b9cfad21269e0fa386e7a9e&language=vi" +
                        "&sort_by=popularity.desc" +
                        "&include_adult=true" +
                        "&include_video=false" +
                        "&release_date.gte=" +minyear+
                        "&release_date.lte=" +maxyear+
                        "&vote_average.gte=" +minrate+
                        "&vote_average.lte=" +maxrate+
                        "&with_watch_monetization_types=flatrate");
                else
                    System.out.println("https://api.themoviedb.org/3/discover/tv?api_key=4ff40b787b9cfad21269e0fa386e7a9e&language=vi" +
                            "&sort_by=popularity.desc" +
                            "&first_air_date.gte=2015&first_air_date.lte=2020&page=1&vote_average.gte=5&with_watch_monetization_types=flatrate&with_status=0&with_type=0\n");
            }
        });

        //clear Checked
        clear = view.findViewById(R.id.discover_clearbtn);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checked=0;
                if (mdtype.equals("movie"))
                for (int i =0;i<movie.size();++i){
                    movie.get(i).setChecked(false);
                }else{
                    for (int i =0;i<tv.size();++i){
                        tv.get(i).setChecked(false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        rv.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        }));
    }

    private class Getgenres extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url =null;
                for (int i =0;i<2;++i){
                    if (i==0)
                        url = new URL("https://api.themoviedb.org/3/genre/movie/list?api_key=4ff40b787b9cfad21269e0fa386e7a9e&language=vi");
                    else
                        url = new URL("https://api.themoviedb.org/3/genre/tv/list?api_key=4ff40b787b9cfad21269e0fa386e7a9e&language=vi");
                    InputStream inputStream = url.openStream();
                    BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String downloadedstring;
                    for(String line;(line = r.readLine())!= null;){
                        stringBuilder.append(line).append("/n");
                    }
                    inputStream.close();
                    downloadedstring = stringBuilder.toString();
                    JSONObject oj = new JSONObject(downloadedstring);
                    JSONArray genrearr = oj.getJSONArray("genres");
                    ArrayList<Genre> genres = new ArrayList<>();
                    for (int j=0;j<genrearr.length();++j){
                        genres.add(new Genre());
                        genres.get(j).setId(genrearr.getJSONObject(j).getInt("id"));
                        genres.get(j).setName(genrearr.getJSONObject(j).getString("name"));
                    }
                    if (i==0)
                        movie = genres;
                    else
                        tv = genres;
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
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            FlexboxLayoutManager flm = new FlexboxLayoutManager(getContext());
            flm.setFlexWrap(FlexWrap.WRAP);
            flm.setJustifyContent(JustifyContent.FLEX_START);
            flm.setFlexDirection(FlexDirection.ROW);
            adapter = new SearchRVAdapter(movie);
            rv.setAdapter(adapter);
            rv.setLayoutManager(flm);
        }
    }

}