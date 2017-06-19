package com.example.android.booklist;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Book>> {

    public static final String BOOK_QUERY_URL = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=1";
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list_activity);
        if(isConnected == true){
            mProgress = (ProgressBar) findViewById(R.id.loading_spinner);
            getLoaderManager().initLoader(0, null, this);
        } else {
            Log.v("BoolListActivity", "isConnected == false");
            final ListView bookListView = (ListView) findViewById(R.id.book_list);
            bookListView.setEmptyView(findViewById(R.id.book_empty_list_view));
        }
    }

    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> data) {

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {

    }
}
