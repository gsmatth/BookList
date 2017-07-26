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

    public void getBookList(View view){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if(isConnected == true){
            Log.v("BookListActivity", "isConnected == TRUE");
            ListView bookListView = (ListView) findViewById(R.id.book_list);
            bookListView.setVisibility(View.GONE);
            ProgressBar loadProgressIndicator = (ProgressBar) findViewById(R.id.loading_spinner);
            loadProgressIndicator.setVisibility(View.VISIBLE);
            mProgress = loadProgressIndicator;

//            mProgress = (ProgressBar) findViewById(R.id.loading_spinner);
            String BASE_BOOK_QUERY_URL = "https://www.googleapis.com/books/v1/volumes?q=";
            int searchId = R.id.book_query_text_input;
            EditText searchTermObject = (EditText)  findViewById(searchId);
            String searchTermString = searchTermObject.getText().toString();
            String requestUrl = BASE_BOOK_QUERY_URL + searchTermString;
            Log.v("BookListActivity", "The full url string is: " + requestUrl);
            FULL_QUERY_URL = requestUrl;
            Log.v("BookListActivity", "The FULL_QUERY_URL is: " + FULL_QUERY_URL);
            getLoaderManager().restartLoader(0, null, this);
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
        emptyListViewText.setVisibility(View.VISIBLE);

        updateUI(books);

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {

    }

    private void updateUI(ArrayList books){
        Log.v("BookListActivity", "updateUI entered" );
        Log.v("BookListActivity", "value of books parameter passed into updateUI: " + books);
        final ListView bookListView = (ListView) findViewById(R.id.book_list);
        Log.v("BookListActivity", "updateUI entered, bookListView: " + bookListView);
        bookListView.setEmptyView(findViewById(R.id.empty_book_list_view));
        final BookAdapter itemsAdapter = new BookAdapter(BookListActivity.this, books);
        Log.v("BookListActivity", "updateUI entered, itemsAdapter: " + itemsAdapter);
        bookListView.setAdapter(itemsAdapter);

    }
}
