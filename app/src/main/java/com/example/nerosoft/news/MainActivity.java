package com.example.nerosoft.news;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.nerosoft.news.Adapters.NewsAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<News>> {

    TextView textView;
    ListView newsListView;
    ImageView noInternetImageView;
    public static final String BASE_NEWS_URL = "https://content.guardianapis.com/search?api-key=51e35a9b-f95b-41f3-af3a-f6e3c876d8df&show-fields=thumbnail,byline\n";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsListView = findViewById(R.id.news_lv);
        textView = findViewById(R.id.tv);
        noInternetImageView = findViewById(R.id.no_internet_iv);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        newsListView.setDivider(null);


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
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        String section =sharedPreferences.getString(getString(R.string.shared_preferences_key),"Sport");
        Uri baseUri=Uri.parse(BASE_NEWS_URL);
        Uri.Builder builder=baseUri.buildUpon();
        builder.appendQueryParameter("q",section);
        builder.build();
        return new NewsAsyncTaskLoader(this,builder.toString());
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

    public  boolean onCreateOptionsMenu(Menu menu){
     getMenuInflater().inflate(R.menu.options_menu,menu);
     return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.section_name_tv:
                Intent intent=new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (isNetworkConnected()) {
            android.support.v4.app.LoaderManager loaderManager = getSupportLoaderManager();
            android.support.v4.content.Loader<Object> loader= loaderManager.getLoader(0);
            if (loader==null) {
                loaderManager.initLoader(0, null, MainActivity.this).forceLoad();
            }
            else {
                loaderManager.restartLoader(0,null,MainActivity.this).forceLoad();
            }
        } else {
            noInternetImageView.setVisibility(View.VISIBLE);
        }
    }
}
