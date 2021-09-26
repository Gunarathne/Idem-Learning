package com.example.mad1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements VideoRVAdaptor.VideoClickInterface {

    private RecyclerView videoRV;
    private ProgressBar loadingPB;
    private FloatingActionButton addFAB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<VideoRVModal> videoRVModalArrayList;
    private RelativeLayout bottomSheetRL;
    private VideoRVAdaptor videoRVAdaptor;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        videoRV = findViewById(R.id.idRVVideo);
        loadingPB = findViewById(R.id.idPBloading);
        addFAB = findViewById(R.id.idAddFAB);
       firebaseDatabase = FirebaseDatabase.getInstance();
       databaseReference = firebaseDatabase.getReference("Videoos");
       videoRVModalArrayList = new ArrayList<>();
       videoRVAdaptor = new VideoRVAdaptor(videoRVModalArrayList,this,this);
       videoRV.setLayoutManager(new LinearLayoutManager(this));
       videoRV.setAdapter(videoRVAdaptor);
       bottomSheetRL = findViewById(R.id.idRLBSheet);
       mAuth = FirebaseAuth.getInstance();
       addFAB.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
             startActivity(new Intent(MainActivity2.this, AddVideoActivity.class));

           }
       });
       getAllVideo();
    }

    private void getAllVideo(){

        videoRVModalArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                videoRVModalArrayList.add(snapshot.getValue(VideoRVModal.class));
                videoRVAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                videoRVAdaptor.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                loadingPB.setVisibility(View.GONE);
                videoRVAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                loadingPB.setVisibility(View.GONE);
                videoRVAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    public void onVideoClick(int position) {

        displayBottomSheet(videoRVModalArrayList.get(position));


    }
    private void displayBottomSheet(VideoRVModal videoRVModal){

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog2,bottomSheetRL);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView videoNameTV = layout.findViewById(R.id.idTVVideoName);

        TextView videoSuitedForTV = layout.findViewById(R.id.idTVSuitedFor);
        TextView videoGradeTV = layout.findViewById(R.id.idTVGrade);
        ImageView videoIV = layout.findViewById(R.id.idIVVideo);
        Button editBtn = layout.findViewById(R.id.idBtnEdit);
        Button viewDetailsBtn = layout.findViewById(R.id.idBtnViewDetails);

        videoNameTV.setText(videoRVModal.getVideoName());

        videoSuitedForTV.setText("For : "+videoRVModal.getBestSuitedFor());
        videoGradeTV.setText("Grade : "+ videoRVModal.getVideoGrade());
        Picasso.get().load(videoRVModal.getVideoImg()).into(videoIV);


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity2.this, EditVideoActivity.class);
                i.putExtra("video", videoRVModal);
                startActivity(i);
            }
        });

        viewDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i  = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(videoRVModal.getVideoLink()));
                startActivity((i));


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.idLogOut:
                Toast.makeText(this, "User Logged Out", Toast.LENGTH_SHORT).show();

                mAuth.signOut();
                Intent i = new Intent(MainActivity2.this,LoginActivity.class);
                startActivity(i);
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}