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

import java.util.HashMap;
import java.util.Map;

public class EditVideoActivity extends AppCompatActivity {

    private TextInputEditText videoNameEdt, videoGradeEdt, videoSuitedForEdt, videoImgEdt, videoLinkEdt;
    private Button updateVideoBtn, deleteVideoBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String videoID;
    private VideoRVModal videoRVModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_video);
        videoNameEdt = findViewById(R.id.idEdtVideoName);
        videoGradeEdt = findViewById(R.id.idEdtGrade);
        videoSuitedForEdt = findViewById(R.id.idEdtVideoSuitedFor);
        videoImgEdt = findViewById(R.id.idEdtVideoImageLink);
        videoLinkEdt = findViewById(R.id.idEdtVideoLink);
        loadingPB = findViewById(R.id.idPBloading);
        updateVideoBtn = findViewById(R.id.idBtnUpdateVideo);
        deleteVideoBtn = findViewById(R.id.idBtnDeleteVideo);
        firebaseDatabase = FirebaseDatabase.getInstance();
        videoRVModal = getIntent().getParcelableExtra("video");

        if(videoRVModal !=null){
            videoNameEdt.setText(videoRVModal.getVideoName());
            videoGradeEdt.setText(videoRVModal.getVideoGrade());
            videoSuitedForEdt.setText(videoRVModal.getBestSuitedFor());
            videoImgEdt.setText(videoRVModal.getVideoImg());
            videoLinkEdt.setText(videoRVModal.getVideoLink());
            videoID = videoRVModal.getVideoID();

        }

        databaseReference = firebaseDatabase.getReference("Videoos").child(videoID);
        updateVideoBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String videoName = videoNameEdt.getText().toString();
                String videoGrade = videoGradeEdt.getText().toString();
                String suitedFor = videoSuitedForEdt.getText().toString();
                String videoImg = videoImgEdt.getText().toString();
                String videoLink = videoLinkEdt.getText().toString();

                Map<String,Object> map = new HashMap<>();
                map.put("videoName",videoName);
                map.put("videoGrade",videoGrade);
                map.put("bestSuitedFor",suitedFor);
                map.put("videoImg",videoImg);
                map.put("videoLink",videoLink);
                map.put("videoID", videoID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        loadingPB.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditVideoActivity.this, "Video updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditVideoActivity.this,MainActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(EditVideoActivity.this, "Fail to Update Video", Toast.LENGTH_SHORT).show();


                    }
                });


            }
        });

        deleteVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              deleteVideo();
            }
        });

    }
    private void deleteVideo(){
        databaseReference.removeValue();
        Toast.makeText(this, "Video Deleted", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditVideoActivity.this,MainActivity.class));

    }

}