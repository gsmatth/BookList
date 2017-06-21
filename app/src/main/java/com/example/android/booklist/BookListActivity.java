package com.example.android.booklist;

import android.app.LoaderManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.content.Loader;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.value;

public class BookListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Book>> {

    public static final String BASE_BOOK_QUERY_URL = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=4";
    //public static final String BOOK_QUERY_URL = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=1&fields=kind,items(title)";
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list_activity);
        if(isConnected == true){
            Log.v("BookListActivity", "isConnected == TRUE");
            mProgress = (ProgressBar) findViewById(R.id.loading_spinner);
            getLoaderManager().initLoader(0, null, this);
        } else {
            Log.v("BookListActivity", "isConnected == false");
            final ListView bookListView = (ListView) findViewById(R.id.book_list);
            bookListView.setEmptyView(findViewById(R.id.empty_book_list_view));
        }
    }

    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int id, Bundle args) {
        Log.v("BookListActivity", "entered onCreateLoader");
        return new BookLoader(BookListActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> books) {
        Log.v("BookListActivity", "onLoadFinished called");
        if(books == null){
            Log.v("BookListActivity", "onLoadFinished was passed a null value for books parameter");
            return;
        }
        Log.v("BooksListActivity", "value of books parameter passed to onLoadFinished: " + books);
        ProgressBar loadProgressIndicator = (ProgressBar) findViewById(R.id.loading_spinner);
        loadProgressIndicator.setVisibility(View.GONE);

        TextView emptyListViewText = (TextView) findViewById(R.id.empty_book_list_view);
        emptyListViewText.setText("No books found matching your search term");

        updateUI(books);

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {

    }

    private void updateUI(ArrayList books){
        Log.v("BookListActivity", "updateUI entered");
        final ListView bookListView = (ListView) findViewById(R.id.book_list);
        Log.v("BookListActivity", "updateUI entered, bookListView: " + bookListView);
        bookListView.setEmptyView(findViewById(R.id.empty_book_list_view));
        final BookAdapter itemsAdapter = new BookAdapter(BookListActivity.this, books);
        Log.v("BookListActivity", "updateUI entered, itemsAdapter: " + itemsAdapter);
        bookListView.setAdapter(itemsAdapter);

    }
}
