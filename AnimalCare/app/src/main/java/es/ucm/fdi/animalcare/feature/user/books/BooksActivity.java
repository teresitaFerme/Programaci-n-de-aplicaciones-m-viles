package es.ucm.fdi.animalcare.feature.user.books;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.data.App;
import es.ucm.fdi.animalcare.feature.toolbar.ToolBarManagement;

public class BooksActivity extends ToolBarManagement  {
    private int BOOK_LOADER_ID = 10;
    private BookLoaderCallbacks bookLoaderCallbacks = new BookLoaderCallbacks(this, this);
    private RecyclerView recyclerView;
    private BooksResultListAdapter adapter;
    private List<BookInfo> booksData;
    private EditText query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        ConstraintLayout toolbar = findViewById(R.id.toolbar);
        setUpToolbar();

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if(loaderManager.getLoader(BOOK_LOADER_ID) != null){
            loaderManager.initLoader(BOOK_LOADER_ID,null, bookLoaderCallbacks);
        }

        TextView change_password_toolbar_title = findViewById(R.id.change_password_toolbar_title);
        change_password_toolbar_title.setText(App.getApp().getResources().getString(R.string.books_activity_title));
        Button button_search = findViewById(R.id.button_search);
        button_search.setText(App.getApp().getResources().getString(R.string.books_activity_title));

        booksData = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view_books);
        adapter = new BooksResultListAdapter(this, booksData);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        query = findViewById(R.id.query);
        query.setText(App.getApp().getResources().getString(R.string.cuidado_de_mascotas));
    }

    public void searchBooks(View view){
        String queryString = query.getText().toString();

        Bundle queryBundle = new Bundle();
        queryBundle.putString(BookLoaderCallbacks.EXTRA_QUERY, queryString);
        LoaderManager.getInstance(this)
                .restartLoader(BOOK_LOADER_ID, queryBundle, bookLoaderCallbacks);
    }

    public void updateBooksResultList(List<BookInfo> bookInfos){
        adapter.setBooksData(bookInfos);
        adapter.notifyDataSetChanged();
    }

    public void goBack(View view){
        finish();
    }

    /*public void resultsNonEmpty(){
        status.setText("Results");
    }

    public void resultsEmpty(){
        status.setText("No Results Found");
    }*/

    public void setUpToolbar() {
        super.setUpToolbar(R.id.button_toolbar_user);
    }
}