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

public class MainActivity extends AppCompatActivity implements BookRVAdaptor.BookClickInterface {

    private RecyclerView bookRV;
    private ProgressBar loadingPB;
    private FloatingActionButton addFAB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<BookRVModal> bookRVModalArrayList;
    private RelativeLayout bottomSheetRL;
    private BookRVAdaptor bookRVAdaptor;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bookRV = findViewById(R.id.idRVBook);
        loadingPB = findViewById(R.id.idPBloading);
        addFAB = findViewById(R.id.idAddFAB);
       firebaseDatabase = FirebaseDatabase.getInstance();
       databaseReference = firebaseDatabase.getReference("Books");
       bookRVModalArrayList = new ArrayList<>();
       bookRVAdaptor = new BookRVAdaptor(bookRVModalArrayList,this,this);
       bookRV.setLayoutManager(new LinearLayoutManager(this));
       bookRV.setAdapter(bookRVAdaptor);
       bottomSheetRL = findViewById(R.id.idRLBSheet);
       mAuth = FirebaseAuth.getInstance();
       addFAB.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
             startActivity(new Intent(MainActivity.this, AddBookActivity.class));

           }
       });
       getAllBook();
    }

    private void getAllBook(){

        bookRVModalArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                bookRVModalArrayList.add(snapshot.getValue(BookRVModal.class));
                bookRVAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                bookRVAdaptor.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                loadingPB.setVisibility(View.GONE);
                bookRVAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                loadingPB.setVisibility(View.GONE);
                bookRVAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    public void onBookClick(int position) {

        displayBottomSheet(bookRVModalArrayList.get(position));


    }
    private void displayBottomSheet(BookRVModal bookRVModal){

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog,bottomSheetRL);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView bookNameTV = layout.findViewById(R.id.idTVBookName);
        TextView bookDescTV = layout.findViewById(R.id.idTVDescription);
        TextView bookSuitedForTV = layout.findViewById(R.id.idTVSuitedFor);
        TextView bookAuthorTV = layout.findViewById(R.id.idTVAuthor);
        ImageView bookIV = layout.findViewById(R.id.idIVBook);
        Button editBtn = layout.findViewById(R.id.idBtnEdit);
        Button viewDetailsBtn = layout.findViewById(R.id.idBtnViewDetails);

        bookNameTV.setText(bookRVModal.getBookName());
        bookDescTV.setText(bookRVModal.getBookDescription());
        bookSuitedForTV.setText("For : "+bookRVModal.getBestSuitedFor());
        bookAuthorTV.setText("Author : by "+ bookRVModal.getBookAuthor());
        Picasso.get().load(bookRVModal.getBookImg()).into(bookIV);


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, EditBookActivity.class);
                i.putExtra("book", bookRVModal);
                startActivity(i);
            }
        });

        viewDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i  = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(bookRVModal.getBookLink()));
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
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}