package com.note.indiatodo.dto;

import java.util.Date;
import java.util.LinkedHashMap;

public class ArticlesNews {
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private Date publishedAt;
    private LinkedHashMap<String,String> source;

    public LinkedHashMap<String, String> getSource() {
        return source;
    }

    public void setSource(LinkedHashMap<String, String> source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }
}
