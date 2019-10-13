package com.example.hw05;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class NewsActivity extends AppCompatActivity implements GetNewsAsyncTask.IGetNewsAsyncTask {

    ListView lv_NewsList;
    ArrayList<News> localNewsList;
    Sources newsSource;

    ProgressBar pb_loading;
    TextView tv_loadingText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        pb_loading = findViewById(R.id.pb_loading);
        tv_loadingText = findViewById(R.id.tv_loadingText);

        pb_loading.setVisibility(View.VISIBLE);
        tv_loadingText.setVisibility(View.VISIBLE);
        tv_loadingText.setText(R.string.loadStories);

        lv_NewsList = findViewById(android.R.id.list);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if ("com.example.hw05.intent.action.VIEW".equals(action) && type != null) {
            if ("array/source".equals(type)) {
                newsSource = (Sources) intent.getSerializableExtra("source");
                setTitle(newsSource.getName());
                RequestParams requestParams = new RequestParams();
                requestParams.addParameter("sources", newsSource.getId());
                if(isConnected()) {
                    new GetNewsAsyncTask(requestParams, NewsActivity.this).execute("https://newsapi.org/v2/top-headlines?apiKey=" + getString(R.string.apikey));
                } else {
                    Toast.makeText(this, "Please Connect to the Internet", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean isConnected(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());

        if(networkCapabilities != null)
        {
            if(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
            {
                return true;
            }
        }
        return false;
    }

    public void openWebPage(String url, String title) {
        Intent intent = new Intent();
        intent.setAction("com.example.hw05.intent.action.VIEW");
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        if(isConnected()) {
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }else {
            Toast.makeText(this, "Internet Not Connected", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void getNewsArrayList(final ArrayList<News> newsArrayList) {
        this.localNewsList = newsArrayList;
        if(this.localNewsList.size() > 0){
            pb_loading.setVisibility(View.GONE);
            tv_loadingText.setVisibility(View.GONE);
            NewsListViewAdapter adapter = new NewsListViewAdapter(NewsActivity.this, this.localNewsList);
            lv_NewsList.setAdapter(adapter);
            lv_NewsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("Bagh 123", localNewsList.get(position).getUrl());
                    openWebPage(localNewsList.get(position).getUrl(), localNewsList.get(position).getTitle());
                }
            });
        } else {
            Toast.makeText(this, "No News to View !!", Toast.LENGTH_SHORT).show();
        }
        
    }
}