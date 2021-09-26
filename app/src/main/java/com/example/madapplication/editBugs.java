package com.example.madapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class editBugs extends AppCompatActivity {
    private EditText title, desc;
    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bugs);

        title = findViewById(R.id.editTextBugTitle);
        desc = findViewById(R.id.editTextBugDescription);
        edit = findViewById(R.id.buttonEdit);


    }
}