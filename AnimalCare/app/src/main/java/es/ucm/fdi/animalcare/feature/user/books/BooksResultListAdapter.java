package es.ucm.fdi.animalcare.feature.user.books;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.animalcare.R;

public class BooksResultListAdapter extends RecyclerView.Adapter<BooksResultListAdapter.ViewHolder> {
    private List<BookInfo> mBooksData;
    private LayoutInflater mInflater;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        BooksResultListAdapter mAdapter;
        TextView title, authors, link;

        public ViewHolder(@NonNull View itemView, BooksResultListAdapter adapter) {
            super(itemView);
            mAdapter = adapter;
            title = itemView.findViewById(R.id.title);
            authors = itemView.findViewById(R.id.authors);
            link = itemView.findViewById(R.id.info_link);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(link.getText().toString()));
            v.getContext().startActivity(intent);
        }
    }

    public BooksResultListAdapter(Context context, List<BookInfo> data){
        mInflater = LayoutInflater.from(context);
        mBooksData = data;
    }

    public void setBooksData(List<BookInfo> data){
        mBooksData = data;
    }

    @NonNull
    @Override
    public BooksResultListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.bookinfo_item, parent, false);
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
