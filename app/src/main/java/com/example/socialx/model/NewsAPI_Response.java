package com.example.socialx.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NewsAPI_Response implements Serializable
{
    @SerializedName("status")
    private String status;

    @SerializedName("totalResults")
    private int count;

    @SerializedName("articles")
    List<NewsAPI_Article> articles;

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public List<NewsAPI_Article> getArticles()
    {
        return articles;
    }

    public void setArticles(List<NewsAPI_Article> articles)
    {
        this.articles = articles;
    }
}
