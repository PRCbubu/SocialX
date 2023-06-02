package com.example.socialx.REST;

import com.example.socialx.model.NewsAPI_Article;

import java.util.List;

public interface onFetchDataListener<NewsAPI_Response>
{
    void onFetchData(List<NewsAPI_Article> list, String message);
    void onError(String message);
}
