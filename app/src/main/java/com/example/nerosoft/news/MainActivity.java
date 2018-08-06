package com.example.nerosoft.news;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nerosoft.news.Adapters.NewsAdapter;

import java.net.InetAddress;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<News>> {

    TextView textView;
    ListView newsListView;
    ImageView noInternetImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsListView = findViewById(R.id.news_lv);
        textView = findViewById(R.id.tv);
        noInternetImageView = findViewById(R.id.no_internet_iv);

        newsListView.setDivider(null);
        if (isNetworkConnected()) {
            android.support.v4.app.LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(0, null, MainActivity.this).forceLoad();
        } else {
            noInternetImageView.setVisibility(View.VISIBLE);
        }


        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = (News) parent.getItemAtPosition(position);
                Uri url = Uri.parse(news.getWebUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, url);
                startActivity(intent);
            }
        });


    }


    @NonNull
    @Override
    public android.support.v4.content.Loader<ArrayList<News>> onCreateLoader(int id, @Nullable Bundle args) {
        return new NewsAsyncTaskLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<ArrayList<News>> loader, ArrayList<News> data) {
        if (data.isEmpty()) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(R.string.empty_list_view);
        } else {
            NewsAdapter newsAdapter = new NewsAdapter(this, R.layout.layout_for_news_list_view, data);
            newsListView.setAdapter(newsAdapter);

        }
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<ArrayList<News>> loader) {

    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        return cm.getActiveNetworkInfo() != null;
    }


}
