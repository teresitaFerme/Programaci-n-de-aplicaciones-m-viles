package es.ucm.fdi.googlebooksclient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BookLoaderCallbacks bookLoaderCallbacks = new BookLoaderCallbacks();
    private EditText  mAuthor, mTitle;
    private Button mButton;
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuthor = findViewById(R.id.author_editText);
        mTitle = findViewById(R.id.title_editText);
        mRadioGroup = findViewById(R.id.radio_group);
        mButton = findViewById(R.id.search);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBooks(v);
            }
        });

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if(loaderManager.getLoader(0) != null){
            loaderManager.initLoader(0,null, bookLoaderCallbacks);
        }
    }

    public void searchBooks (View view){
        String queryString = String.valueOf(mAuthor.getText()) + " " + String.valueOf(mTitle.getText());
        RadioButton checked = (RadioButton) findViewById(mRadioGroup.getCheckedRadioButtonId());
        String printType  = String.valueOf(checked.getText());

        Bundle queryBundle = new Bundle();
        queryBundle.putString("queryString", queryString);
        queryBundle.putString("printType", printType);
        LoaderManager.getInstance(this)
                .restartLoader(0, queryBundle, bookLoaderCallbacks);
    }

    void updateBooksResultList(List<BookInfo> bookInfos){
        //llama a updateBooksResultList con una
        //lista cualquiera en onCreate, para ver que se visualiza la lista correctamente en la
        //interfaz.
    }
}