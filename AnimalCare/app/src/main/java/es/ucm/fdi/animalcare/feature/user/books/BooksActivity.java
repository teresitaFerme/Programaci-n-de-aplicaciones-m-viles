package es.ucm.fdi.animalcare.feature.user.books;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;
import es.ucm.fdi.animalcare.data.App;
import es.ucm.fdi.animalcare.data.User;
import es.ucm.fdi.animalcare.feature.calendar.CalendarActivity;
import es.ucm.fdi.animalcare.feature.pets.PetsActivity;
import es.ucm.fdi.animalcare.feature.settings.SettingsActivity;
import es.ucm.fdi.animalcare.feature.toolbar.ToolBarManagement;
import es.ucm.fdi.animalcare.feature.upcoming.UpcomingActivity;

public class BooksActivity extends BaseActivity implements ToolBarManagement {
    private int BOOK_LOADER_ID = 10;
    private User user;
    private BookLoaderCallbacks bookLoaderCallbacks = new BookLoaderCallbacks(this, this);
    private RecyclerView recyclerView;
    private BooksResultListAdapter adapter;
    private List<BookInfo> booksData;
    private EditText query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        user = (User) getIntent().getSerializableExtra("user");


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
        findViewById(R.id.button_toolbar_pets).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_upcoming).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_settings).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_calendar).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_user).getBackground().setTint(getResources().getColor(R.color.white));
    }

    @Override
    public void launchFromToolbar(View view) {
        if(view.getId() != R.id.button_toolbar_user){
            Intent intent;
            switch (view.getId()){
                case R.id.button_toolbar_pets:
                    intent = new Intent(this, PetsActivity.class);
                    break;
                case R.id.button_toolbar_upcoming:
                    intent = new Intent(this, UpcomingActivity.class);
                    break;
                case R.id.button_toolbar_calendar:
                    intent = new Intent(this, CalendarActivity.class);
                    break;
                default:
                    intent = new Intent(this, SettingsActivity.class);
                    break;

            }
            intent.putExtra("user", user);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }
}