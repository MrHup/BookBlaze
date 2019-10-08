package com.hubpsace.bookblaze;
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

    public String give_year(){ // returns just the year from the date
        return "";
    }
}
