package com.hubpsace.bookblaze;

import android.util.Log;

import org.json.JSONObject;

public class Book {
    private String name;
    private String author;
    private String date;
    private String genre;

    public Book(JSONObject x){
        try{
            this.name = x.getString("name");
            this.author = x.getString("author");
            this.date = x.getString("date");
            this.genre = x.getString("genre");

            Log.d("main", "Object instantiated with: ["+ name + " by " + author + ", " + genre + ","+date+"];");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // returns just the year from the date
    //public String give_year(){ return ""; }

    @Override
    public String toString(){
        return this.name+", " + this.author + ", "+ this.genre;
    }
}
