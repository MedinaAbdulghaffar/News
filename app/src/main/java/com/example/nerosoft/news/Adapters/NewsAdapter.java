package com.example.nerosoft.news.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nerosoft.news.News;
import com.example.nerosoft.news.R;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<News> newsArrayList;
    PlaceHolder placeHolder;


    public NewsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<News> newsArrayList) {
        super(context, resource, newsArrayList);
        this.context = context;
        this.resource = resource;
        this.newsArrayList = newsArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(resource, parent, false);
            placeHolder = new PlaceHolder();
            placeHolder.title = convertView.findViewById(R.id.title_tv);
            placeHolder.author = convertView.findViewById(R.id.author_tv);
            placeHolder.sectionName = convertView.findViewById(R.id.section_name_tv);
            placeHolder.publicationDate = convertView.findViewById(R.id.publication_date_tv);
            placeHolder.image = convertView.findViewById(R.id.news_iv);
            convertView.setTag(placeHolder);
        } else {
            placeHolder = (PlaceHolder) convertView.getTag();
        }

        News news = newsArrayList.get(position);
        placeHolder.title.setText(news.getTitle());
        placeHolder.author.setText(news.getAuthor());
        placeHolder.sectionName.setText(news.getSectionName());
        placeHolder.publicationDate.setText(news.getPublicationDate());
        Glide.with(placeHolder.image).load(news.getImage()).into(placeHolder.image);

        return convertView;
    }

    class PlaceHolder {
        ImageView image;
        TextView author;
        TextView sectionName;
        TextView publicationDate;
        TextView title;
    }

}



