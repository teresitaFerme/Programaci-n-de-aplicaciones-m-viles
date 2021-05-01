package es.ucm.fdi.googlebooksclient;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BookResultsListAdapter extends RecyclerView.Adapter<BookResultsListAdapter.ViewHolder> {
    private ArrayList<BookInfo> mBooksData;

    public void setBooksData(List<BookInfo> data){
        mBooksData = (ArrayList<BookInfo>) data;
    }


    @NonNull
    @Override
    public BookResultsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BookResultsListAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
