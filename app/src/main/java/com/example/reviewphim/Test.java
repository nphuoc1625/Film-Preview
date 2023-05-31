package com.example.reviewphim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Test extends AppCompatActivity {
    TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        textView = findViewById(R.id.test_txt);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        String from = intent.getStringExtra("from");
        String filmtype = intent.getStringExtra("filmtype");

        String string = "from"+from+",id="+id+"filmtype ="+ filmtype;
        textView.setText(string);
    }
}