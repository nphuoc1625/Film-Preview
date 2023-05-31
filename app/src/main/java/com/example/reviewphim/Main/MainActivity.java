package com.example.reviewphim.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RadioButton;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.reviewphim.Main.Discover.Discover;
import com.example.reviewphim.Main.Home.Homefrag;
import com.example.reviewphim.Main.Saved.Savedfrag;
import com.example.reviewphim.Main.Search.Searchfrag;
import com.example.reviewphim.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Homefrag home = new Homefrag();
    Searchfrag search = new Searchfrag();
    Savedfrag saved = new Savedfrag();
    Discover discover = new Discover();
    FragmentManager fragmentManager = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.main_bottomnav);

        bottomNavigationView.inflateMenu(R.menu.main_navmenu);
        bottomNavigationView.setItemIconTintList(null);


        fragmentManager.beginTransaction().add(R.id.main_fragholder,home,"home").commit();
        fragmentManager.beginTransaction().add(R.id.main_fragholder,search,"search").commit();
        fragmentManager.beginTransaction().add(R.id.main_fragholder,saved,"saved").commit();
        fragmentManager.beginTransaction().add(R.id.main_fragholder,discover,"discover").commit();
        fragmentManager.executePendingTransactions();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fragmentManager.beginTransaction().hide(home).commit();
                fragmentManager.beginTransaction().hide(search).commit();
                fragmentManager.beginTransaction().hide(saved).commit();
                fragmentManager.beginTransaction().hide(discover).commit();
                switch (item.getItemId()){
                    case R.id.main_bottomnav_home:
                        fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("home")).commit();
                        break;
                    case R.id.main_bottomnav_discover:
                        fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("discover")).commit();
                        break;
                    case R.id.main_bottomnav_search:
                        fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("search")).commit();
                        break;
                    case R.id.main_bottomnav_saved:
                        fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("saved")).commit();
                        fragmentManager.findFragmentByTag("saved").onResume();


                }
                fragmentManager.executePendingTransactions();
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.main_bottomnav_home);

        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }


}