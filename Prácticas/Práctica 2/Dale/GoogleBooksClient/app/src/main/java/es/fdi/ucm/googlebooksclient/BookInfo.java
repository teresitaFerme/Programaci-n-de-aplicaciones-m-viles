package es.fdi.ucm.googlebooksclient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookInfo {
    String title;
    String authors;
    URL infoLink;

    static List<BookInfo> fromJsonResponse(String s){
        List<BookInfo> books = new ArrayList<BookInfo>();

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            int i = 0;
            String title = "";
            String authors = "";
            String link = "";

            while (i < itemsArray.length()) {
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    title = volumeInfo.getString("title");
                    link = volumeInfo.getString("infoLink");
                    JSONArray authorArray = volumeInfo.getJSONArray("authors");
                    if (title != null && authorArray != null) {

                        for(int j = 0; j < authorArray.length(); ++j){
                            authors += authorArray.getString(j);
                            if (j != authorArray.length() - 1)
                                authors += ", ";
                        }

                        BookInfo bookInfo = new BookInfo();
                        bookInfo.title = title;
                        bookInfo.authors = authors;
                        bookInfo.infoLink = new URL(link);
                        books.add(bookInfo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finally {
            return books;
        }
    }
}
