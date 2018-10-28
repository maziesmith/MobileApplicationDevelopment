/*
a. Assignment # : In Class 05
b. File Name : 801091205_InClass05.zip
c. Name : Pawan Ramesh Bole.
*/

package com.uncc.inclass05;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import  android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsData{
    TextView textViewTitle;
    TextView textViewDate;
    ImageView imageViewNewsImage;
    TextView textViewDescriptionData;
    ProgressBar progressBarLoadData;
    ImageView imageViewPrev;
    ImageView imageViewNext;
    int currentPosition;
    ArrayList<News> newsList;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Buzzfeed Headlines");

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDate = findViewById(R.id.textViewDate);
        imageViewNewsImage = findViewById(R.id.imageViewNewsImage);
        textViewDescriptionData = findViewById(R.id.textViewDescriptionData);
        progressBarLoadData = findViewById(R.id.progressBarLoadData);
        imageViewPrev= findViewById(R.id.imageViewPrev);
        imageViewNext = findViewById(R.id.imageViewNext);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setTitle("Loading").setView(inflater.inflate(R.layout.dialog_bar, null));
        dialog = builder.create();
        dialog.show();


        new NewsAsyncTask(MainActivity.this).execute("https://newsapi.org/v2/top-headlines?\n" +
                "sources=buzzfeed&apiKey=3e918825a3c44bbb9c181b193e9b9a43");

        imageViewPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPosition == 0)
                {
                    Toast.makeText(MainActivity.this,"This is the first news",Toast.LENGTH_LONG).show();
                }
                else
                {
                    currentPosition = currentPosition - 1;
                    populateData(newsList);

                }

            }
        });

        imageViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPosition == newsList.size()-1)
                {
                    Toast.makeText(MainActivity.this,"This is the last news",Toast.LENGTH_LONG).show();
                }
                else
                {
                    currentPosition = currentPosition + 1;
                }

                populateData(newsList);
            }
        });

        findViewById(R.id.buttonQuit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void populateData(ArrayList<News> newsList) {
        this.newsList = newsList;
        News news = newsList.get(currentPosition);
        textViewTitle.setText(news.title);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String sCertDate = dateFormat.format(news.publishedAt);
        textViewDate.setText(sCertDate);
        Picasso.get().load(news.urlToImage).into(imageViewNewsImage);
        textViewDescriptionData.setText(news.description);
        dialog.hide();
        progressBarLoadData.setVisibility(View.INVISIBLE);
    }


}

interface NewsData{
    void populateData(ArrayList<News> news);
}
