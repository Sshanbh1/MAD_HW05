package com.example.hw05;

import android.os.Parcel;
import android.os.Parcelable;

public class News implements Parcelable {
    String author;
    String title;
    String url;
    String urlToImage;
    String publishedAt;
    String source;

    public News() {

    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getAuthor());
        dest.writeString(this.getPublishedAt());
        dest.writeString(this.getTitle());
        dest.writeString(this.getUrl());
        dest.writeString(this.getUrlToImage());
        dest.writeString(this.getSource());
    }

    @Override
    public String toString() {
        return "News{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                '}';
    }

    //constructor used for parcel
    public News(Parcel parcel){
        this.author = parcel.readString();
        this.publishedAt = parcel.readString();
        this.title = parcel.readString();
        this.url = parcel.readString();
        this.urlToImage = parcel.readString();
        this.source = parcel.readString();
    }

    //creator - used when un-parceling our parcle (creating the object)
    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>(){

        @Override
        public News createFromParcel(Parcel parcel) {
            return new News(parcel);
        }

        @Override
        public News[] newArray(int size) {
            return new News[0] ;
        }
    };
}
