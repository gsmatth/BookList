package com.example.android.booklist;

/**
 * Created by djp on 6/20/17.
 */

public class Book {
    private String mTitle;

    public Book(String title){
        mTitle = title;
    }

    @Override
    public String toString(){
        return "Book{" +
                "mTitle=" + mTitle +
                '}';
    }
}


