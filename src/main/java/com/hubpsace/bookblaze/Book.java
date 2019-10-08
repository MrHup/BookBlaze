package com.hubpsace.bookblaze;

import android.util.Log;

import org.json.JSONObject;

public class Book {
    public String name;
    public String author;
    public String date;
    public String genre;

    public Book(String name, String author, String date, String genre){ // book constructor
        this.name=name;
        this.author=author;
        this.date=date;
        this.genre=genre;
    }

    public Book(JSONObject x){
        try{
            this.name = x.getString("name");
            this.author = x.getString("author");
            this.date = x.getString("date");
            this.genre = x.getString("genre");

            Log.d("main", "Object instantiated with: ["+ name + ", " + author + ", " + genre + "];");
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public String give_year(){ // returns just the year from the date
        return "";
    }

    @Override
    public String toString(){
        return this.name+", " + this.author + ", "+ this.genre;
    }
}
