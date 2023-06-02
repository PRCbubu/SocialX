package com.example.socialx.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NewsAPI_Article implements Serializable
{
    @SerializedName("source")
    private  NewsAPI_Sources sources;
    @SerializedName("title")
    private String Title;
    @SerializedName("description")
    private String Description;
    @SerializedName("publishedAt")
    private String PublishedAt;

    @SerializedName("urlToImage")
    private String NewsImage;

    public String getTitle()
    {
        return Title;
    }

    public void setTitle(String title)
    {
        Title = title;
    }

    public String getDescription()
    {
        return Description;
    }

    public void setDescription(String description)
    {
        Description = description;
    }

    public String getPublishedAt()
    {
        return PublishedAt;
    }

    public void setPublishedAt(String publishedAt)
    {
        PublishedAt = publishedAt;
    }

    public String getNewsImage()
    {
        return NewsImage;
    }

    public void setNewsImage(String newsImage)
    {
        NewsImage = newsImage;
    }

    public NewsAPI_Sources getSources()
    {
        return sources;
    }

    public void setSources(NewsAPI_Sources sources)
    {
        this.sources = sources;
    }
}
