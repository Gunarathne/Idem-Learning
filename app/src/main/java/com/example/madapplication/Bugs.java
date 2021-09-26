package com.example.madapplication;

public class Bugs {
    private  int id;
    private String title, desc;

    public Bugs(){

    }

    public Bugs(int id, String title, String desc) {
        this.id = id;
        this.title = title;
        this.desc = desc;
    }


    public Bugs( String title, String desc) {

        this.title = title;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}