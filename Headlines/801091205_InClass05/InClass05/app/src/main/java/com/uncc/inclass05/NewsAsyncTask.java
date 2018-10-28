/*
a. Assignment # : In Class 05
b. File Name : 801091205_InClass05.zip
c. Name : Pawan Ramesh Bole.
*/

package com.uncc.inclass05;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NewsAsyncTask extends AsyncTask<String, Void, ArrayList<News>> {
    MainActivity currentActivity;
    public NewsAsyncTask(MainActivity currentActivity) {
        this.currentActivity = currentActivity;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(ArrayList<News> news) {
        super.onPostExecute(news);
        //currentActivity.progressBarLoadData.setVisibility(View.INVISIBLE);
        currentActivity.populateData(news);

    }

    @Override
    protected ArrayList<News> doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode= con.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK)
            {
                InputStream in = con.getInputStream();
                return NewsParser.parseNews(in);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}




