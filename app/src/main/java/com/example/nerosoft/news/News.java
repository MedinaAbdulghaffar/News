package com.example.nerosoft.news;

import java.net.URL;

public class News {
    private String Author;
    private String SectionName;
    private String PublicationDate;
    private String Image;
    private String WebUrl;


    public News(String author, String sectionName, String publicationDate, String image, String webUrl) {
        Author = author;
        SectionName = sectionName;
        PublicationDate = publicationDate;
        Image = image;
        WebUrl = webUrl;
    }

    public String getWebUrl() {
        return WebUrl;
    }

    public String getAuthor() {
        return Author;
    }

    public String getSectionName() {
        return SectionName;
    }

    public String getPublicationDate() {
        return PublicationDate;
    }

    public String getImage() {
        return Image;
    }


}
