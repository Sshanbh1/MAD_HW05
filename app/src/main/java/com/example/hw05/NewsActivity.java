package com.example.hw05;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class NewsActivity extends AppCompatActivity {

    ListView lv_NewsList;
    ArrayList<News> localNewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        lv_NewsList = findViewById(android.R.id.list);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if ("com.example.hw05.intent.action.VIEW".equals(action) && type != null) {
            if ("array/news".equals(type)) {
                setTitle(intent.getStringExtra("title"));
                showNews(intent); // Handle View for News
            }
        }
    }

    void showNews(Intent intent) {
        this.localNewsList = intent.getParcelableArrayListExtra(MainActivity.KEY_NEWSLIST);
        NewsListViewAdapter adapter = new NewsListViewAdapter(NewsActivity.this, this.localNewsList);
        lv_NewsList.setAdapter(adapter);
        lv_NewsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openWebPage(localNewsList.get(position).getUrl(), localNewsList.get(position).getTitle());
            }
        });
    }

    public void openWebPage(String url, String title) {
        Intent intent = new Intent();
        intent.setAction("com.example.hw05.intent.action.VIEW");
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}