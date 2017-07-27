package com.example.android.booklist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.android.booklist.R.id.book_authors;
import static com.example.android.booklist.R.id.book_publisher;
import static com.example.android.booklist.R.id.book_title;

/**
 * Created by djp on 6/19/17.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if (convertView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.book_list_item, parent, false);
        }

        Book currentBookObject = getItem(position);

        TextView titleView = (TextView) listItemView.findViewById(book_title);
        String mTitleString = currentBookObject.getTitle();
        titleView.setText(mTitleString);

        TextView publisherView = (TextView) listItemView.findViewById(book_publisher);
        String mPublisherString = currentBookObject.getPublisher();
        publisherView.setText(mPublisherString);

        TextView authorsView = (TextView) listItemView.findViewById(book_authors);
        String mAuthorsString = currentBookObject.getAuthors();
        authorsView.setText(mAuthorsString);

        return listItemView;

    }
}