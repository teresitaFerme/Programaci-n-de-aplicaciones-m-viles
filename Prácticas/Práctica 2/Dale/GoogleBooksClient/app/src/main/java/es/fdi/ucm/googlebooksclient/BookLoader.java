package es.fdi.ucm.googlebooksclient;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<BookInfo>> {

    final String BASE_URL= "https://www.googleapis.com/books/v1/volumes?";
    final String QUERY_PARAM= "q";
    final String MAX_RESULTS= "maxResults";
    final String PRINT_TYPE= "printType";
    String queryString;
    String printType;

    public BookLoader(@NonNull Context context, String queryString, String printType) {
        super(context);
        this.queryString = queryString;
        this.printType = printType;
    }

    @Override
    public void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<BookInfo> loadInBackground() {
        return BookInfo.fromJsonResponse(getBookInfoJson(queryString, printType));
    }

    public String convertIsToString(InputStream stream) throws IOException {
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

    public String getBookInfoJson(String queryString, String printType){
        HttpURLConnection conn = null;
        InputStream is = null;
        Uri builtURI = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, queryString)
                .appendQueryParameter(MAX_RESULTS, "10")
                .appendQueryParameter(PRINT_TYPE, printType)
                .build();
        try{
            URL requestURL= new URL(builtURI.toString());
            conn = (HttpURLConnection) requestURL.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();
            String contentAsString = convertIsToString(is);
            return contentAsString;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(conn != null)
                conn.disconnect();
            if (is != null) {
                try{
                    is.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
