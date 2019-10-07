package com.example.hw05;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsListViewAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<News> news;

        public NewsListViewAdapter(Context context, ArrayList<News> news) {
            this.context = context;
            this.news = news;
        }

    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public Object getItem(int position) {
        return news.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(
                    R.layout.listview_custom, parent, false);
        }

        Log.d("Bagh Image", news.get(position).getUrlToImage());

        TextView authorName = convertView.findViewById(R.id.tv_authorName);
        TextView newsTitle = convertView.findViewById(R.id.tv_newsTitle);
        ImageView articleImage = convertView.findViewById(R.id.iv_newsImage);
        TextView publisedAt = convertView.findViewById(R.id.tv_publishdate);

        authorName.setText(news.get(position).getAuthor());
        newsTitle.setText(news.get(position).getTitle());
        publisedAt.setText(news.get(position).getPublishedAt());
        Picasso.get().load(news.get(position).getUrlToImage()).into(articleImage);

        return convertView;
    }
}
