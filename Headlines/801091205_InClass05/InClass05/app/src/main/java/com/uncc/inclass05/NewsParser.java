/*
a. Assignment # : In Class 05
b. File Name : 801091205_InClass05.zip
c. Name : Pawan Ramesh Bole.
*/

package com.uncc.inclass05;

import android.util.MalformedJsonException;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.JSONArray;

public class NewsParser {

    static ArrayList<News> newsList = new ArrayList<News>();

    static ArrayList<News> parseNews(InputStream in) throws MalformedJsonException, IOException
    {
        try {
            String json = IOUtils.toString(in, "UTF8");
            JSONObject root = new JSONObject(json);
            JSONArray newsArray = root.getJSONArray("articles");
            for (int i=0;i<newsArray.length();i++)
            {
                JSONObject newsJson = newsArray.getJSONObject(i);
                News news = new News();
                news.title = newsJson.getString("title");
                String dateStr = newsJson.getString("publishedAt");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                news.publishedAt = sdf.parse(dateStr);
                news.urlToImage = newsJson.getString("urlToImage");
                news.description = newsJson.getString("description");

                newsList.add(news);
            }
        }
        catch (Exception e)
        {

        }
        finally {

        }
        return newsList;
    }
}
