package es.fdi.ucm.googlebooksclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int BOOK_LOADER_ID = 0;
    private BookLoaderCallbacks bookLoaderCallbacks = new BookLoaderCallbacks(this, this);
    private EditText authors, title;
    private RadioGroup radioGroup;
    private RecyclerView recyclerView;
    private BooksResultListAdapter booksResultListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.authors = findViewById(R.id.authors);
        this.title = findViewById(R.id.title);
        this.radioGroup = findViewById(R.id.radiogroup);
        this.recyclerView = findViewById(R.id.recyclerview);

        List<BookInfo> booksData = new ArrayList<>();
        booksResultListAdapter = new BooksResultListAdapter(this, booksData);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if(loaderManager.getLoader(BOOK_LOADER_ID) != null) {
            loaderManager.initLoader(BOOK_LOADER_ID,null, bookLoaderCallbacks);
        }

        recyclerView.setAdapter(booksResultListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void searchBooks (View view) {
        String authorString = this.authors.getText().toString();
        String titleString = this.title.getText().toString();
        String queryString = authorString + " " + titleString;

        RadioButton r = findViewById(this.radioGroup.getCheckedRadioButtonId());
        String printType = r.getText().toString();

        Bundle queryBundle = new Bundle();
        queryBundle.putString(BookLoaderCallbacks.QUERY_STRING, queryString);
        queryBundle.putString(BookLoaderCallbacks.PRINT_TYPE, printType);
        LoaderManager.getInstance(this).restartLoader(BOOK_LOADER_ID, queryBundle, bookLoaderCallbacks);
    }

    void updateBooksResultList(List<BookInfo> booksData){
        booksResultListAdapter.setBooksData(booksData);
        booksResultListAdapter.notifyDataSetChanged();
    }
}