package com.example.android.booklist;

import android.app.LoaderManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.content.Loader;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Book>> {

    public static String FULL_QUERY_URL = null;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list_activity);
        getLoaderManager().initLoader(0, null, this);
    }

    public void getBookList(View view) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected == true) {
            ListView bookListView = (ListView) findViewById(R.id.book_list);
            bookListView.setVisibility(View.GONE);
            final TextView noConnectivityView = (TextView) findViewById(R.id.no_connectivity_view);
            noConnectivityView.setVisibility(View.GONE);
            ProgressBar loadProgressIndicator = (ProgressBar) findViewById(R.id.loading_spinner);
            loadProgressIndicator.setVisibility(View.VISIBLE);
            mProgress = loadProgressIndicator;

            String BASE_BOOK_QUERY_URL = "https://www.googleapis.com/books/v1/volumes?q=";
            int searchId = R.id.book_query_text_input;
            EditText searchTermObject = (EditText) findViewById(searchId);
            String searchTermString = searchTermObject.getText().toString();
            String requestUrl = BASE_BOOK_QUERY_URL + searchTermString;
            FULL_QUERY_URL = requestUrl;
            getLoaderManager().restartLoader(0, null, this);
        } else {
            final ListView bookListView = (ListView) findViewById(R.id.book_list);
            bookListView.setVisibility(View.GONE);
            final TextView noConnectivityView = (TextView) findViewById(R.id.no_connectivity_view);
            noConnectivityView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(BookListActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> books) {
        if (books == null) {
            return;
        }
        ProgressBar loadProgressIndicator = (ProgressBar) findViewById(R.id.loading_spinner);
        loadProgressIndicator.setVisibility(View.GONE);

        TextView emptyListViewText = (TextView) findViewById(R.id.empty_book_list_view);
        emptyListViewText.setVisibility(View.VISIBLE);

        updateUI(books);

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {

    }

    private void updateUI(ArrayList books) {
        final ListView bookListView = (ListView) findViewById(R.id.book_list);
        ;
        bookListView.setEmptyView(findViewById(R.id.empty_book_list_view));
        final BookAdapter itemsAdapter = new BookAdapter(BookListActivity.this, books);
        bookListView.setAdapter(itemsAdapter);

    }
}
