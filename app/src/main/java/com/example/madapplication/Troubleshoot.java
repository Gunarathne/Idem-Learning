package com.example.madapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class Troubleshoot extends AppCompatActivity {
   private Button add;
   private ListView listView;
   Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troubleshoot);
        add = findViewById(R.id.add);
        listView = findViewById(R.id.bugList);


            button = findViewById(R.id.button_back1);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
    }
});}}