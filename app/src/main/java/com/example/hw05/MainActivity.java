package com.example.hw05;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements GetSourcesAsyncTask.IGetSourcesAsyncTask{

    ListView lv_sourceList;
    ProgressBar pb_loading;
    TextView tv_loadingText;


    List<Sources> sourceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Main Activity");

        lv_sourceList = findViewById(R.id.lv_NewsList);
        pb_loading = findViewById(R.id.pb_loading);
        tv_loadingText= findViewById(R.id.tv_loadingText);

        pb_loading.setVisibility(View.VISIBLE);

        if(isConnected()) {
            tv_loadingText.setText("Loading Sources !!");
            new GetSourcesAsyncTask(MainActivity.this).execute("https://newsapi.org/v2/sources?apiKey=" + getString(R.string.apikey));
        } else {
            Toast.makeText(this, "Please Connect to the Internet !!", Toast.LENGTH_SHORT).show();
        }

        lv_sourceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isConnected()) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction("com.example.hw05.intent.action.VIEW");
                    sendIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    sendIntent.setType("array/source");
                    sendIntent.putExtra("source", sourceList.get(position));
                    // Verify that the intent will resolve to an activity
                    if (sendIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(sendIntent);
                    }
                }
            }
        });
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

    @Override
    public void getSourceArrayList(ArrayList<Sources> newsArrayList) {
        this.sourceList = newsArrayList;
        ArrayAdapter<Sources> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, sourceList);
        lv_sourceList.setAdapter(arrayAdapter);
        pb_loading.setVisibility(View.GONE);
        tv_loadingText.setVisibility(View.GONE);
    }

}
