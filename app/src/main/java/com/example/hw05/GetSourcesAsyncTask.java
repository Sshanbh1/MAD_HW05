package com.example.hw05;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetSourcesAsyncTask extends AsyncTask<String, Void, ArrayList<Sources>> {
    ArrayList<Sources> result = new ArrayList<>();
    IGetSourcesAsyncTask getSourcesAsyncTask;

    public GetSourcesAsyncTask(IGetSourcesAsyncTask getSourcesAsyncTask) {
        this.getSourcesAsyncTask = getSourcesAsyncTask;
    }

    @Override
    protected ArrayList<Sources> doInBackground(String... params) {

        HttpURLConnection connection = null;

        try {
            Log.d("Bagh" ,params[0]);
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String json = IOUtils.toString(connection.getInputStream(), "UTF8");

                JSONObject root = new JSONObject(json);
                JSONArray sources = root.getJSONArray("sources");
                for (int i=0;i<sources.length();i++) {
                    JSONObject sourceJson = sources.getJSONObject(i);
                    Sources source = new Sources();
                    source.setName(sourceJson.getString("name"));
                    source.setId(sourceJson.getString("id"));
                    result.add(source);
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
    protected void onPostExecute(ArrayList<Sources> result) {
        if(result != null){
            getSourcesAsyncTask.getSourceArrayList(result);
        }
    }

    public interface IGetSourcesAsyncTask{
        void getSourceArrayList(ArrayList<Sources> newsArrayList);
    }

}
