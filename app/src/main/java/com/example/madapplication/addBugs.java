package com.example.madapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class addBugs extends AppCompatActivity {
    private EditText title, desc;
    private Button add;
    private Context context;
    private DbHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bugs);

        title = findViewById(R.id.editTextTitle);
        desc = findViewById(R.id.editTextDescription);
        add = findViewById(R.id.buttonAdd);
        context = this;
        dbHandler = new DbHandler(context);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,addBugs.class));
                String userTitle = title.getText().toString();
                String userDesc = desc.getText().toString();


                Bugs bugs = new Bugs(userTitle, userDesc);
                dbHandler.addBugs(bugs);


            }
        });
    }
}