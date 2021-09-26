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

public class EditBookActivity extends AppCompatActivity {

    private TextInputEditText bookNameEdt, bookAuthorEdt, bookSuitedForEdt, bookImgEdt, bookLinkEdt, bookDescEdt;
    private Button updateBookBtn, deleteBookBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String bookID;
    private BookRVModal bookRVModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        bookNameEdt = findViewById(R.id.idEdtBookName);
        bookAuthorEdt = findViewById(R.id.idEdtAuthor);
        bookSuitedForEdt = findViewById(R.id.idEdtBookSuitedFor);
        bookImgEdt = findViewById(R.id.idEdtBookImageLink);
        bookLinkEdt = findViewById(R.id.idEdtBookLink);
        bookDescEdt = findViewById(R.id.idEdtBookDescription);
        loadingPB = findViewById(R.id.idPBloading);
        updateBookBtn = findViewById(R.id.idBtnUpdateBook);
        deleteBookBtn = findViewById(R.id.idBtnDeleteBook);
        firebaseDatabase = FirebaseDatabase.getInstance();
        bookRVModal = getIntent().getParcelableExtra("book");

        if(bookRVModal !=null){
            bookNameEdt.setText(bookRVModal.getBookName());
            bookAuthorEdt.setText(bookRVModal.getBookAuthor());
            bookSuitedForEdt.setText(bookRVModal.getBestSuitedFor());
            bookImgEdt.setText(bookRVModal.getBookImg());
            bookLinkEdt.setText(bookRVModal.getBookLink());
            bookDescEdt.setText(bookRVModal.getBookDescription());
            bookID = bookRVModal.getBookID();

        }

        databaseReference = firebaseDatabase.getReference("Books").child(bookID);
        updateBookBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String bookName = bookNameEdt.getText().toString();
                String bookAuthor = bookAuthorEdt.getText().toString();
                String suitedFor = bookSuitedForEdt.getText().toString();
                String bookImg = bookImgEdt.getText().toString();
                String bookLink = bookLinkEdt.getText().toString();
                String bookDesc = bookDescEdt.getText().toString();

                Map<String,Object> map = new HashMap<>();
                map.put("bookName",bookName);
                map.put("bookDescription",bookDesc);
                map.put("bookAuthor",bookAuthor);
                map.put("bestSuitedFor",suitedFor);
                map.put("bookImg",bookImg);
                map.put("bookLink",bookLink);
                map.put("bookID", bookID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        loadingPB.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditBookActivity.this, "Book updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditBookActivity.this,MainActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(EditBookActivity.this, "Fail to Update Book", Toast.LENGTH_SHORT).show();


                    }
                });


            }
        });

        deleteBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              deleteBook();
            }
        });

    }
    private void deleteBook(){
        databaseReference.removeValue();
        Toast.makeText(this, "Book Deleted", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditBookActivity.this,MainActivity.class));

    }

}