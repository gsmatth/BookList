package com.example.android.booklist;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

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

        //bind object data to views
        TextView titleView = (TextView) listItemView.findViewById(book_title);
        String mTitleString = currentBookObject.getTitle();
        titleView.setText(mTitleString);

//        TextView locationLineTwoView = (TextView) listItemView.findViewById(R.id.location_line_2);
//        String mInitialStringTwo = currentEarthquakeObject.getLocation();
//        if (mInitialStringTwo.contains("of") == true){
//            int offset = mInitialString.indexOf("of") + 2;
//            mSecondaryString = mInitialString.substring(offset + 1);
//            locationLineTwoView.setText(mSecondaryString);


//        TextView eventDateView = (TextView) listItemView.findViewById(R.id.event_date);
//        eventDateView.setText(currentEarthquakeObject.getEventDate());
//
//        TextView eventTimeView = (TextView) listItemView.findViewById(R.id.event_time);
//        eventTimeView.setText(currentEarthquakeObject.getEventTime());
//        //return listItemView(earthquake_item) to caller, which is the ListView

        return listItemView;

    }
}