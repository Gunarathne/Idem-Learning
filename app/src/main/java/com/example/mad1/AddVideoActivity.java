package com.example.mad1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddVideoActivity extends AppCompatActivity {

    private TextInputEditText videoNameEdt, videoGradeEdt, videoSuitedForEdt, videoImgEdt, videoLinkEdt;
    private Button addvideoBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String videoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);
        videoNameEdt = findViewById(R.id.idEdtVideoName);
        videoGradeEdt = findViewById(R.id.idEdtGrade);
        videoSuitedForEdt = findViewById(R.id.idEdtVideoSuitedFor);
        videoImgEdt = findViewById(R.id.idEdtVideoImageLink);
        videoLinkEdt = findViewById(R.id.idEdtVideoLink);
        loadingPB = findViewById(R.id.idPBloading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Videoos");
        addvideoBtn = findViewById(R.id.idBtnAddVideo);

        addvideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingPB.setVisibility(View.VISIBLE);
                String videoName = videoNameEdt.getText().toString();
                String videoGrade = videoGradeEdt.getText().toString();
                String suitedFor = videoSuitedForEdt.getText().toString();
                String videoImg = videoImgEdt.getText().toString();
                String videoLink = videoLinkEdt.getText().toString();
                videoID = videoName;
                VideoRVModal videoRVModal = new VideoRVModal(videoName,videoGrade,suitedFor,videoImg, videoLink, videoID);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        loadingPB.setVisibility(View.GONE);
                        databaseReference.child(videoID).setValue(videoRVModal);
                        Toast.makeText(AddVideoActivity.this, "Video Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddVideoActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(AddVideoActivity.this, "Err is" +error.toString(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }
}