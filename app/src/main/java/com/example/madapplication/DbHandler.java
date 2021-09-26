package com.example.madapplication;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.nio.Buffer;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "bugs";
    private static final String TABLE_NAME = "bugs";

    // Column names
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";


    public DbHandler(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String TABLE_CREATE_QUERY = "CREATE TABLE "+TABLE_NAME+" " +
                "("
                +ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +TITLE + " TEXT,"
                +DESCRIPTION + " TEXT" +")";

        /*
            CREATE TABLE todo (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT,
            started TEXT,finished TEXT); */

        db.execSQL(TABLE_CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        // Drop older table if existed
        db.execSQL(DROP_TABLE_QUERY);
        // Create tables again
        onCreate(db);
    }

    // Add a single bug
    public void addBugs(Bugs bugs){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE,bugs.getTitle());
        contentValues.put(DESCRIPTION, bugs.getDesc());


        //save to table
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        // close database
        sqLiteDatabase.close();
    }


    // Get all bugs into a list
    public List<Bugs> getAllBugs(){

        List<Bugs> Bugs = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME;

        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do {
                // Create new Bugs object
                Bugs bugs = new Bugs();

                bugs.setId(cursor.getInt(0));
                bugs.setTitle(cursor.getString(1));
                bugs.setDesc(cursor.getString(2));


                getAllBugs().add(bugs);
            }while (cursor.moveToNext());
        }
        return getAllBugs();
    }

    // Delete item
    public void deleteBugs(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,"id =?",new String[]{String.valueOf(id)});
        db.close();
    }

    // Get a single bugs
    public Bugs getSingleBugs(int id){
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(TABLE_NAME,new String[]{ID,TITLE,DESCRIPTION},
                ID + "= ?",new String[]{String.valueOf(id)}
                ,null,null,null);

        Bugs bugs;
        if(cursor != null){
            cursor.moveToFirst();
            bugs = new Bugs(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            return bugs;
        }
        return null;
    }

    // Update a single bug
    public int updateSingleBug(Bugs bugs){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE,bugs.getTitle());
        contentValues.put(DESCRIPTION, bugs.getDesc());


        int status = db.update(TABLE_NAME,contentValues,ID +" =?",
                new String[]{String.valueOf(bugs.getId())});

        db.close();
        return status;
    }
}
