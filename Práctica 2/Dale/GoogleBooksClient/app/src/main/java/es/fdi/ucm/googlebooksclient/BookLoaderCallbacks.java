package es.fdi.ucm.googlebooksclient;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.List;

public class BookLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<BookInfo>> {


    public static final String QUERY_STRING = "query";
    public static final String PRINT_TYPE = "printType";
    private Context context;
    private MainActivity mainActivity;
    public BookLoaderCallbacks(Context context, MainActivity mainActivity){
        this.context = context;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public Loader<List<BookInfo>> onCreateLoader(int id, @Nullable Bundle args) {
        return new BookLoader(context, args.getString(QUERY_STRING), args.getString(PRINT_TYPE));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<BookInfo>> loader, List<BookInfo> data) {
        mainActivity.updateBooksResultList(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<BookInfo>> loader) {

    }
}
