package es.ucm.fdi.googlebooksclient;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.List;

public class BookLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<BookInfo>>{
    static final String EXTRA_QUERY = "query";
    static final String EXTRA_PRINT_TYPE = "printType";
    private Context context;
    private MainActivity mainActivity;

    public BookLoaderCallbacks(Context context, MainActivity activity){
        this.context = context;
        mainActivity = activity;
    }

    @NonNull
    @Override
    public Loader<List<BookInfo>> onCreateLoader(int id, @Nullable Bundle args){
        return new BookLoader(context, args.getString(EXTRA_QUERY), args.getString(EXTRA_PRINT_TYPE));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<BookInfo>> loader, List<BookInfo> data) {
        mainActivity.updateBooksResultList(data);
        if (data.size() != 0) mainActivity.resultsNonEmpty();
        else mainActivity.resultsEmpty();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<BookInfo>> loader) {}
}
