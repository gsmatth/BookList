package com.example.android.booklist;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by djp on 6/20/17.
 */

public class Book {
    private String mTitle;
    private String mPublisher;
    private String mAuthors;

    public Book(String title, String publisher, String authors) {
        mTitle = title;
        mPublisher = publisher;
        mAuthors = authors;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public String getAuthors() {
        return mAuthors;
    }

    @Override
    public String toString() {
        return "Book{" +
                "mTitle=" + mTitle + " " +
                "mPublisher=" + mPublisher + " " +
                "mAuthors=" + mAuthors +
                '}';
    }
}


