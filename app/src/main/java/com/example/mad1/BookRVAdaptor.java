package com.example.mad1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookRVAdaptor extends RecyclerView.Adapter<BookRVAdaptor.ViewHolder> {
    private ArrayList<BookRVModal> bookRVModalArrayList;
    private Context context;
    int lastPos = -1;
    private BookClickInterface bookClickInterface;

    public BookRVAdaptor(ArrayList<BookRVModal> bookRVModalArrayList, Context context, BookClickInterface bookClickInterface) {
        this.bookRVModalArrayList = bookRVModalArrayList;
        this.context = context;
        this.bookClickInterface = bookClickInterface;
    }

    @NonNull
    @Override
    public BookRVAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookRVAdaptor.ViewHolder holder, int position) {

        BookRVModal bookRVModal = bookRVModalArrayList.get(position);
        holder.bookNameTV.setText(bookRVModal.getBookName());
        holder.bookAuthorTV.setText("Author. "+ bookRVModal.getBookAuthor());
        Picasso.get().load(bookRVModal.getBookImg()).into(holder.bookIV);
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookClickInterface.onBookClick(position);

            }
        });
    }

    private void setAnimation(View itemView, int position){

        if (position>lastPos){
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }

    }

    @Override
    public int getItemCount() {
        return bookRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView bookNameTV, bookAuthorTV;
        private ImageView bookIV;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            bookNameTV = itemView.findViewById(R.id.idTVBookName);
            bookAuthorTV = itemView.findViewById(R.id.idTVAuthor);
            bookIV = itemView.findViewById(R.id.idIVBook);


        }
    }

    public interface BookClickInterface {
        void onBookClick(int position);
    }
}
