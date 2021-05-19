package es.ucm.fdi.animalcare.feature.user.books;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.animalcare.R;

public class BooksActivity extends AppCompatActivity {
    private int BOOK_LOADER_ID = 10;
    private BookLoaderCallbacks bookLoaderCallbacks = new BookLoaderCallbacks(this, this);
    private RecyclerView recyclerView;
    private BooksResultListAdapter adapter;
    private List<BookInfo> booksData;
    private EditText authors, title;
    private RadioGroup radioGroup;
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if(loaderManager.getLoader(BOOK_LOADER_ID) != null){
            loaderManager.initLoader(BOOK_LOADER_ID,null, bookLoaderCallbacks);
        }

        booksData = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view_books);
        adapter = new BooksResultListAdapter(this, booksData);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        searchBooks();
    }

    public void searchBooks(){
        String queryString = "cuidado de mascotas";

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
}