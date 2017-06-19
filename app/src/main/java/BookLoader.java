import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import static com.example.android.booklist.BookListActivity.BASE_BOOK_QUERY_URL;

/**
 * Created by djp on 6/19/17.
 */

public class BookLoader extends AsyncTaskLoader<ArrayList<Book>> {
    public BookLoader(Context context){
        super(context);
        Log.v("BookLoader", "constructing BookLoader");
    }
    @Override
    protected void onStartLoading(){
        Log.v("BookLoader", "onStartLoading");
        forceLoad();
    }

    @Override
    public ArrayList<Book> loadInBackground(){
        Log.v("BookLoader", "loadInBackground started");
        if(BASE_BOOK_QUERY_URL == null){
            Log.v("BookLoader", "loadInBackground URL is null");
            return null;
        }
        final String bookData = QueryUtils.fetchBookData(BASE_BOOK_QUERY_URL);
        final ArrayList<Book> books = QueryUtils.extractBooks(bookData);
        Log.v("BookLoader", "value of books ArrayList: " + books);
        return books;
    }
}
