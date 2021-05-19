package es.ucm.fdi.animalcare.feature.user.books;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookInfo {
    String title, authors;
    URL infoLink;
    private static final String TAG = BookInfo.class.getSimpleName();

    static List<BookInfo> fromJsonResponse (String s){
        List<BookInfo> list = new ArrayList<BookInfo>();
        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            int i = 0;
            String title = null;
            String authors = null;

            while (i < itemsArray.length()) {
                authors = "";

                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                title = volumeInfo.getString("title");
                JSONArray author = volumeInfo.getJSONArray("authors");
                for (int j = 0; j < author.length(); ++j){
                    authors += author.getString(j);
                    if (j != author.length()-1){
                        authors += ", ";
                    }
                }
                String link = volumeInfo.getString("infoLink");

                Log.d(TAG, "Title: " + title);
                Log.d(TAG, "Authors: " + authors);
                Log.d(TAG, "Info link: " + link);

                BookInfo bi = new BookInfo();
                bi.title = title;
                bi.authors = authors;
                bi.infoLink = new URL(link);

                list.add(bi);

                i++;
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            return list;
        }
    }

}
