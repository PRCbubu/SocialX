package com.example.socialx.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsAPI_Response
{
    @SerializedName("status")
    private String status;

    @SerializedName("totalResults")
    private int count;

    @SerializedName("articles")
    List<NewsAPI_User> articles;

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

    public List<NewsAPI_User> getArticles()
    {
        return articles;
    }

    public void setArticles(List<NewsAPI_User> articles)
    {
        this.articles = articles;
    }
}
