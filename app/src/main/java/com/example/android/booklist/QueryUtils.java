package com.example.android.booklist;

import android.util.Log;;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;


/**
 * Created by djp on 6/19/17.
 */

public final class QueryUtils {
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {

    }


    public static String fetchBookData(String requestURL) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        URL url = createUrl(requestURL);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
//            Log.v("QueryUtils", "JSON response is: " + jsonResponse);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error fetching book data ", e);
        }
        return jsonResponse;
    }

    public static URL createUrl(String stringURL) {
        URL url = null;
        try {
            url = new URL(stringURL);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            Log.v(LOG_TAG, "url is null, quitting makeHttpRequest");
            return jsonResponse;
        }
        Log.v(LOG_TAG, "url is not null, entering makeHttpRequest");
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the book JSON results, ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static ArrayList<Book> extractBooks(String bookData) {
        ArrayList<Book> books = new ArrayList<>();

        try {
            JSONObject bookObject = new JSONObject(bookData);
            JSONArray items = bookObject.getJSONArray("items");
            String tempTitleString = "";
            String tempPublisherString = "";
            String authorsList = "";
            for (int i = 0; i < items.length(); i++) {
                JSONObject tempItemsObject = items.getJSONObject(i);
                JSONObject tempVolumeInfoObject = tempItemsObject.getJSONObject("volumeInfo");

                if (!tempVolumeInfoObject.has("title")) {
                    tempTitleString = "No Title Listed";
                } else {
                    tempTitleString = tempVolumeInfoObject.getString("title");
                }

                if (!tempVolumeInfoObject.has("publisher")) {
                    tempPublisherString = "No Publisher Listed";
                } else {
                    tempPublisherString = tempVolumeInfoObject.getString("publisher");
                }

                if (!tempVolumeInfoObject.has("authors")) {
                    authorsList = "No Authors Listed";
                } else {
                    JSONArray tempAuthorsJSONArray = tempVolumeInfoObject.getJSONArray("authors");
                    authorsList = tempAuthorsJSONArray.join(", ").replaceAll("\"", "");
                }

//                Log.v("QueryUtils", "tempTitleString:  " + tempTitleString);
//                Log.v("QueryUtils", "tempPublisherString:  " + tempPublisherString);
//                Log.v("QueryUtils", "authorlist:  " + authorsList);

                // build up a list of Book objects with the corresponding data.
                books.add(new Book(tempTitleString, tempPublisherString, authorsList));
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the book JSON results", e);
        }

        return books;
    }

}
