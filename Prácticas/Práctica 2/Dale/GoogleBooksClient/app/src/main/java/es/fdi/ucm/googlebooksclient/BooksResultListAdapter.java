package es.fdi.ucm.googlebooksclient;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BooksResultListAdapter extends RecyclerView.Adapter<BooksResultListAdapter.ViewHolder> {
    private List<BookInfo> mBooksData;
    private LayoutInflater mInflater;

    public class ViewHolder extends RecyclerView.ViewHolder{
        BooksResultListAdapter mAdapter;
        TextView title, authors, link;

        public ViewHolder(@NonNull View itemView, BooksResultListAdapter booksResultListAdapter) {
            super(itemView);
            mAdapter = booksResultListAdapter;
            title = itemView.findViewById(R.id.title);
            authors = itemView.findViewById(R.id.authors);
            link = itemView.findViewById(R.id.infolink);
        }
    }

    public BooksResultListAdapter(Context context, List<BookInfo> bookList) {
        this.mInflater = LayoutInflater.from(context);
        this.mBooksData = bookList;
    }

    public void setBooksData(List<BookInfo> data){
        this.mBooksData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.bookinfo, parent, false);
        return new ViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksResultListAdapter.ViewHolder holder, int position) {
        BookInfo mCurrent = mBooksData.get(position);
        holder.title.setText(mCurrent.title);
        holder.authors.setText(mCurrent.authors);
        holder.link.setText(mCurrent.infoLink.toString());
    }

    @Override
    public int getItemCount() {
        return mBooksData.size();
    }

}
