package com.example.nerosoft.news;

public class News {
    private String Author;
    private String SectionName;
    private String PublicationDate;
    private String Image;
    private String WebUrl;
    private String Title;


    public News(String author, String sectionName, String publicationDate, String image, String webUrl, String title) {
        Author = author;
        SectionName = sectionName;
        PublicationDate = publicationDate;
        Image = image;
        WebUrl = webUrl;
        Title = title;
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

    public String getTitle() {
        return Title;
    }
}
