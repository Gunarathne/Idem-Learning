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

public class AddBookActivity extends AppCompatActivity {

    private TextInputEditText bookNameEdt, bookAuthorEdt, bookSuitedForEdt, bookImgEdt, bookLinkEdt, bookDescEdt;
    private Button addbookBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String bookID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        bookNameEdt = findViewById(R.id.idEdtBookName);
        bookAuthorEdt = findViewById(R.id.idEdtAuthor);
        bookSuitedForEdt = findViewById(R.id.idEdtBookSuitedFor);
        bookImgEdt = findViewById(R.id.idEdtBookImageLink);
        bookLinkEdt = findViewById(R.id.idEdtBookLink);
        bookDescEdt = findViewById(R.id.idEdtBookDescription);
        loadingPB = findViewById(R.id.idPBloading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Books");
        addbookBtn = findViewById(R.id.idBtnAddBook);

        addbookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingPB.setVisibility(View.VISIBLE);
                String bookName = bookNameEdt.getText().toString();
                String bookAuthor = bookAuthorEdt.getText().toString();
                String suitedFor = bookSuitedForEdt.getText().toString();
                String bookImg = bookImgEdt.getText().toString();
                String bookLink = bookLinkEdt.getText().toString();
                String bookDesc = bookDescEdt.getText().toString();
                bookID = bookName;
                BookRVModal bookRVModal = new BookRVModal(bookName,bookDesc,bookAuthor,suitedFor,bookImg,bookLink, bookID);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        loadingPB.setVisibility(View.GONE);
                        databaseReference.child(bookID).setValue(bookRVModal);
                        Toast.makeText(AddBookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddBookActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(AddBookActivity.this, "Err is" +error.toString(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }
}