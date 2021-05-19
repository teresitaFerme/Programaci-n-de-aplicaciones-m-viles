package es.ucm.fdi.animalcare.feature.user.books;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.List;

public class BookLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<BookInfo>>{
    static final String EXTRA_QUERY = "query";
    private Context context;
    private BooksActivity booksActivity;

    public BookLoaderCallbacks(Context context, BooksActivity activity){
        this.context = context;
        booksActivity = activity;
    }

    @NonNull
    @Override
    public Loader<List<BookInfo>> onCreateLoader(int id, @Nullable Bundle args){
        return new BookLoader(context, args.getString(EXTRA_QUERY));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<BookInfo>> loader, List<BookInfo> data) {
        booksActivity.updateBooksResultList(data);
        /*if (data.size() != 0) booksActivity.resultsNonEmpty();
        else booksActivity.resultsEmpty();*/
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<BookInfo>> loader) {}
}
