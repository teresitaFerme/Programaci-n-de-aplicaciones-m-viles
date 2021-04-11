package es.ucm.fdi.googlebooksclient;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<BookInfo>> {
    private final String BASE_URI = "https://www.googleapis.com/books/v1/volumes?";
    private final String QUERY_PARAM = "q";
    private final String MAX_RESULTS = "maxResults";
    private final String PRINT_TYPE = "printType";
    private static final String TAG = BookLoader.class.getSimpleName();
    private String queryString;
    private String printType;

    public BookLoader(@NonNull Context context, String queryString, String printType) {
        super(context);
        this.queryString = queryString;
        this.printType = printType;
    }

    @Nullable
    @Override
    public List<BookInfo> loadInBackground() {
        return BookInfo.fromJsonResponse(getBookInfoJson(queryString, printType));
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    public String convertInputToString(InputStream stream) throws IOException{

        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line + "\n");
        }
        if (builder.length() == 0) {
            return null;
        }
        return builder.toString();
    }

    String getBookInfoJson(String queryString, String printType){
        InputStream is = null;
        HttpURLConnection conn = null;

        Uri builtURI = Uri.parse(BASE_URI).buildUpon()
                .appendQueryParameter(QUERY_PARAM, queryString)
                .appendQueryParameter(MAX_RESULTS, "40")
                .appendQueryParameter(PRINT_TYPE, printType)
                .build();

        try {
            URL requestURL = new URL(builtURI.toString());
            conn = (HttpURLConnection) requestURL.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();
            int response = conn.getResponseCode();
            Log.d(TAG, "Response: " + String.valueOf(response));

            is = conn.getInputStream();
            String contentAsString = convertInputToString(is);
            Log.d(TAG, "Content: " + contentAsString);

            return contentAsString;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
