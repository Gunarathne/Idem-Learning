package com.example.mad1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class OpenPage extends AppCompatActivity {
    private Button openBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_page);

        openBtn = findViewById(R.id.openbtn);
        openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OpenPage.this, RegisterActivity.class));
            }
        });
    }
}
