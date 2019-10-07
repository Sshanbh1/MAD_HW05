package com.example.hw05;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetNewsAsyncTask extends AsyncTask<String, Void, ArrayList<News>> {
    RequestParams requestParams;
    IGetNewsAsyncTask getNewsAsyncTask;
    ArrayList<News> result = new ArrayList<>();
    public GetNewsAsyncTask(RequestParams requestParams, IGetNewsAsyncTask getNewsAsyncTask) {
        this.requestParams = requestParams;
        this.getNewsAsyncTask = getNewsAsyncTask;
    }

    @Override
    protected ArrayList<News> doInBackground(String... params) {

        HttpURLConnection connection = null;

        try {
            URL url = new URL(requestParams.getEncodedUrl(params[0]));
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String json = IOUtils.toString(connection.getInputStream(), "UTF8");

                JSONObject root = new JSONObject(json);

                JSONArray articles = root.getJSONArray("articles");
                for (int i=0;i < articles.length();i++) {
                    JSONObject newsJson = articles.getJSONObject(i);
                    JSONObject source = newsJson.getJSONObject("source");
                    News news = new News();
                    news.setAuthor(newsJson.getString("author"));
                    news.setPublishedAt(newsJson.getString("publishedAt"));
                    news.setTitle(newsJson.getString("title"));
                    news.setUrl(newsJson.getString("url"));
                    news.setUrlToImage(newsJson.getString("urlToImage"));
                    news.setSource(source.getString("name"));
                    result.add(news);
                }
            }
        } catch (Exception e) {
            Log.d("Bagh", e.toString());
        } finally {
            connection.disconnect();
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<News> result) {
        if(result != null){
            getNewsAsyncTask.getNewsArrayList(result);
        }
    }

    public interface IGetNewsAsyncTask{
        void getNewsArrayList(ArrayList<News> newsArrayList);
    }
}
