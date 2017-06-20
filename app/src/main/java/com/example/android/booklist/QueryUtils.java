package com.example.android.booklist;
import android.util.Log;

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

    private QueryUtils(){

    }
    public static String FetchBookData(String requestURL){
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        URL url = createUrl(requestURL);

        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        } catch(IOException e) {
            Log.e(LOG_TAG, "Error fetching book data ", e);
        }
        return jsonResponse;
    }

    public static URL createUrl(String stringURL){
        URL url = null;
        try{
            url = new URL(stringURL);
        }catch(MalformedURLException e){
            Log.e(LOG_TAG, "Error creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if(url == null){
            Log.v(LOG_TAG, "url is null, quitting makeHttpRequest");
            return jsonResponse;
        }
        Log.v(LOG_TAG, "url is not null, entering makeHttpRequest");
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving the book JSON results, ", e);
        } finally {
            if(urlConnection!= null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream (InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static ArrayList<Book> extractBooks(String bookData) {


        // Create an empty ArrayList that we can start adding books to
        ArrayList<Book> books = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject bookObject = new JSONObject(bookData);
            JSONArray features = bookObject.getJSONArray("features");
            for(int i = 0; i < features.length(); i++){
                JSONObject tempFeaturesObject = features.getJSONObject(i);
                JSONObject tempPropertiesObject = tempFeaturesObject.getJSONObject("properties");
                double magnitude = tempPropertiesObject.getDouble("mag");
                String place = tempPropertiesObject.getString("place");
                Long time = tempPropertiesObject.getLong("time");
                String earthquakeUrl = tempPropertiesObject.getString("url");
//                Log.v("QueryUtils", "earthquakeUrl:  " + earthquakeUrl);
                earthquakes.add(new Earthquake(magnitude, place, time, earthquakeUrl));
//                Log.v("QueryUtils", "value of earthQuakes" + earthquakes);
            }

            // build up a list of Earthquake objects with the corresponding data.

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }
}