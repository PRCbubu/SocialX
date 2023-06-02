package com.example.socialx.REST;

import com.example.socialx.model.NewsAPI_Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPI_UserEndPoints
{
    @GET("top-headlines")
    Call<NewsAPI_Response> callHeadlines
            (
                    @Query("country") String country,
                    @Query("category") String category,
                    @Query("q") String query,
                    @Query("apiKey") String api_key
            );

}