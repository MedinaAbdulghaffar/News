package com.example.nerosoft.news;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NewsAsyncTaskLoader extends android.support.v4.content.AsyncTaskLoader<ArrayList<News>> {
    public ArrayList<News> arrayListOfNews = new ArrayList<>();
    String uriString;

    public NewsAsyncTaskLoader(Context context, String uriString) {
        super(context);
        this.uriString=uriString;

    }

    @Override
    public ArrayList<News> loadInBackground() {
        StringBuilder jsonData = new StringBuilder();
        HttpURLConnection httpURLConnection;
        InputStream inputStream;
        InputStreamReader inputStreamReader;
        BufferedReader read;
        String line;


        try {
            URL url = new URL(uriString);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            read = new BufferedReader(inputStreamReader);
            line = read.readLine();

            while (line != null) {
                jsonData.append(line);
                line = read.readLine();
            }

            JSONObject root = new JSONObject(jsonData.toString());
            JSONObject response = root.getJSONObject("response");

            JSONArray results = response.getJSONArray("results");
            if (response.has("results")) {
                results = response.getJSONArray("results");
            }

            String author = new String();
            String sectionName = new String();
            String publicationDate = new String();
            String image = new String();
            String webUrl = new String();
            String title = new String();

            for (int i = 0; i < results.length(); i++) {

                JSONObject element = results.getJSONObject(i);
                if (element.has("sectionName")) {
                    sectionName = element.getString("sectionName");
                }

                if (element.has("webPublicationDate")) {
                    publicationDate = element.getString("webPublicationDate");
                }
                JSONObject subObject = element.getJSONObject("fields");
                if (element.has("fields")) {
                    subObject = element.getJSONObject("fields");
                }
                if (subObject.has("byline")) {
                    author = subObject.getString("byline");
                }
                if (subObject.has("thumbnail")) {
                    image = subObject.getString("thumbnail");
                }
                if (element.has("webUrl")) {
                    webUrl = element.getString("webUrl");
                }
                if (element.has("webTitle")) {
                    title = element.getString("webTitle");
                }

                arrayListOfNews.add(new News(author, sectionName, publicationDate, image, webUrl, title));
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return arrayListOfNews;
    }


}
