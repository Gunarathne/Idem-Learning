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

public class VideoRVAdaptor extends RecyclerView.Adapter<VideoRVAdaptor.ViewHolder> {
    private ArrayList<VideoRVModal> videoRVModalArrayList;
    private Context context;
    int lastPos = -1;
    private VideoClickInterface videoClickInterface;

    public VideoRVAdaptor(ArrayList<VideoRVModal> videoRVModalArrayList, Context context, VideoClickInterface videoClickInterface) {
        this.videoRVModalArrayList = videoRVModalArrayList;
        this.context = context;
        this.videoClickInterface = videoClickInterface;
    }

    @NonNull
    @Override
    public VideoRVAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_rv_item2,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoRVAdaptor.ViewHolder holder, int position) {

        VideoRVModal videoRVModal = videoRVModalArrayList.get(position);
        holder.videoNameTV.setText(videoRVModal.getVideoName());
        holder.videoGradeTV.setText("Grade. "+ videoRVModal.getVideoGrade());
        Picasso.get().load(videoRVModal.getVideoImg()).into(holder.videoIV);
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoClickInterface.onVideoClick(position);

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
        return videoRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView videoNameTV, videoGradeTV;
        private ImageView videoIV;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            videoNameTV = itemView.findViewById(R.id.idTVVideoName);
            videoGradeTV = itemView.findViewById(R.id.idTVGrade);
            videoIV = itemView.findViewById(R.id.idIVVideo);


        }
    }

    public interface VideoClickInterface {
        void onVideoClick(int position);
    }
}
