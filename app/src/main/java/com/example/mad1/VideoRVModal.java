package com.example.mad1;

import android.os.Parcel;
import android.os.Parcelable;

public class VideoRVModal implements Parcelable {
    private String videoName;
    private String videoGrade;
    private String bestSuitedFor;
    private String videoImg;
    private String videoLink;
    private String videoID;

    public VideoRVModal(){


    }

    protected VideoRVModal(Parcel in) {
        videoName = in.readString();
        videoGrade = in.readString();
        bestSuitedFor = in.readString();
        videoImg = in.readString();
        videoLink = in.readString();
        videoID = in.readString();
    }

    public static final Creator<VideoRVModal> CREATOR = new Creator<VideoRVModal>() {
        @Override
        public VideoRVModal createFromParcel(Parcel in) {
            return new VideoRVModal(in);
        }

        @Override
        public VideoRVModal[] newArray(int size) {
            return new VideoRVModal[size];
        }
    };

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }


    public String getVideoGrade() {
        return videoGrade;
    }

    public void setVideoGrade(String videoGrade) {
        this.videoGrade = videoGrade;
    }

    public String getBestSuitedFor() {
        return bestSuitedFor;
    }

    public void setBestSuitedFor(String bestSuitedFor) {
        this.bestSuitedFor = bestSuitedFor;
    }

    public String getVideoImg() {
        return videoImg;
    }

    public void setVideoImg(String videoImg) {
        this.videoImg = videoImg;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public VideoRVModal(String videoName, String videoGrade, String bestSuitedFor, String videoImg, String videoLink, String videoID) {
        this.videoName = videoName;
        this.videoGrade = videoGrade;
        this.bestSuitedFor = bestSuitedFor;
        this.videoImg = videoImg;
        this.videoLink = videoLink;
        this.videoID = videoID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(videoName);
        parcel.writeString(videoGrade);
        parcel.writeString(bestSuitedFor);
        parcel.writeString(videoImg);
        parcel.writeString(videoLink);
        parcel.writeString(videoID);
    }
}
