package com.example.socialx.REST;

import com.example.socialx.model.NewsAPI_User;

import java.util.List;

public interface onFetchDataListener<NewsAPI_Response>
{
    void onFetchData(List<NewsAPI_User> list, String message);
    void onError(String message);
}
