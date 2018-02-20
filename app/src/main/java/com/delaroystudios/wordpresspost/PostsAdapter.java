package com.delaroystudios.wordpresspost;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.delaroystudios.wordpresspost.data.PostsContract;
import com.delaroystudios.wordpresspost.data.Posts;

/**
 * Created by delaroy on 2/20/18.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    Cursor dataCursor;
    Context context;
    int id;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date, description, price;
        public ImageView thumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.title);
            date = (TextView) itemView.findViewById(R.id.dateTime);
            description = (TextView) itemView.findViewById(R.id.description);


            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(context, DetailsPage.class);
                        intent.putExtra("POSTS_ID", getItem(pos).id);
                        intent.putExtra("POSTS_LINK", getItem(pos).link);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    public PostsAdapter(Activity mContext, Cursor cursor) {
        dataCursor = cursor;
        context = mContext;
    }

    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item, parent, false);
        return new ViewHolder(cardview);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(PostsAdapter.ViewHolder holder, int position) {


        dataCursor.moveToPosition(position);

        int id = dataCursor.getInt(dataCursor.getColumnIndex(PostsContract.AppEntry._ID));
        String date = dataCursor.getString(dataCursor.getColumnIndex(PostsContract.AppEntry.COLUMN_DATE));
        String title = dataCursor.getString(dataCursor.getColumnIndex(PostsContract.AppEntry.COLUMN_TITLE));
        String link = dataCursor.getString(dataCursor.getColumnIndex(PostsContract.AppEntry.COLUMN_LINK));
        String excerpt = dataCursor.getString(dataCursor.getColumnIndex(PostsContract.AppEntry.COLUMN_EXCERPT));

        holder.date.setText(date);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            holder.description.setText(Html.fromHtml(excerpt, Html.FROM_HTML_MODE_COMPACT));
            holder.name.setText(Html.fromHtml(title, Html.FROM_HTML_MODE_COMPACT));
        }else{
            holder.description.setText(Html.fromHtml(excerpt));
            holder.name.setText(Html.fromHtml(title));
        }

    }


    public Cursor swapCursor(Cursor cursor) {
        if (dataCursor == cursor) {
            return null;
        }
        Cursor oldCursor = dataCursor;
        this.dataCursor = cursor;
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }

    @Override
    public int getItemCount() {
        return (dataCursor == null) ? 0 : dataCursor.getCount();
    }

    public Posts getItem(int position) {
        if (position < 0 || position >= getItemCount()) {
            throw new IllegalArgumentException("Item position is out of adapter's range");
        } else if (dataCursor.moveToPosition(position)) {
            return new Posts(dataCursor);
        }
        return null;
    }
}




