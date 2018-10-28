/*
a. Assignment # : In Class 05
b. File Name : 801091205_InClass05.zip
c. Name : Pawan Ramesh Bole.
*/

package com.uncc.inclass05;

import java.util.Date;

public class News {
    String title;
    Date publishedAt;
    String urlToImage;
    String description;
    String content;
    String author;
    String url;

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", publishedAt=" + publishedAt +
                ", urlToImage='" + urlToImage + '\'' +
                ", description='" + description + '\'' +
                '}';
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
