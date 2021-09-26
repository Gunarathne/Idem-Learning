package com.example.mad1;

import android.os.Parcel;
import android.os.Parcelable;

public class BookRVModal implements Parcelable {
    private String bookName;
    private String bookDescription;
    private String bookAuthor;
    private String bestSuitedFor;
    private String bookImg;
    private String bookLink;
    private String bookID;

    public BookRVModal(){


    }

    protected BookRVModal(Parcel in) {
        bookName = in.readString();
        bookDescription = in.readString();
        bookAuthor = in.readString();
        bestSuitedFor = in.readString();
        bookImg = in.readString();
        bookLink = in.readString();
        bookID = in.readString();
    }

    public static final Creator<BookRVModal> CREATOR = new Creator<BookRVModal>() {
        @Override
        public BookRVModal createFromParcel(Parcel in) {
            return new BookRVModal(in);
        }

        @Override
        public BookRVModal[] newArray(int size) {
            return new BookRVModal[size];
        }
    };

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBestSuitedFor() {
        return bestSuitedFor;
    }

    public void setBestSuitedFor(String bestSuitedFor) {
        this.bestSuitedFor = bestSuitedFor;
    }

    public String getBookImg() {
        return bookImg;
    }

    public void setBookImg(String bookImg) {
        this.bookImg = bookImg;
    }

    public String getBookLink() {
        return bookLink;
    }

    public void setBookLink(String bookLink) {
        this.bookLink = bookLink;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public BookRVModal(String bookName, String bookDescription, String bookAuthor, String bestSuitedFor, String bookImg, String bookLink, String bookID) {
        this.bookName = bookName;
        this.bookDescription = bookDescription;
        this.bookAuthor = bookAuthor;
        this.bestSuitedFor = bestSuitedFor;
        this.bookImg = bookImg;
        this.bookLink = bookLink;
        this.bookID = bookID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(bookName);
        parcel.writeString(bookDescription);
        parcel.writeString(bookAuthor);
        parcel.writeString(bestSuitedFor);
        parcel.writeString(bookImg);
        parcel.writeString(bookLink);
        parcel.writeString(bookID);
    }
}
