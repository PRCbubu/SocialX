package com.example.socialx.REST;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.socialx.R;
import com.example.socialx.model.NewsAPI_Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient
{
    Context context;
    public static final String BASE_URL = "https://newsapi.org/v2/";
    private static Retrofit retrofit = null;

    public APIClient(Context context)
    {
        this.context = context;
    }

    public void getNewsHeadlines(onFetchDataListener<NewsAPI_Response> listener, String country, String category, String query)
    {
        NewsAPI_UserEndPoints userEndPoints = APIClient.getClient().create(NewsAPI_UserEndPoints.class);
        Call<NewsAPI_Response> call = userEndPoints.callHeadlines(country, category, query, context.getString(R.string.api_key));
        try
        {
            call.enqueue(new Callback<>()
            {
                @Override
                public void onResponse(@NonNull Call<NewsAPI_Response> call, @NonNull Response<NewsAPI_Response> response)
                {
                    if(!response.isSuccessful())
                    {
                        Toast.makeText(context, "Error!!", Toast.LENGTH_SHORT).show();
                    }

                    assert response.body() != null;
                    listener.onFetchData(response.body().getArticles(), response.message());
                }

                @Override
                public void onFailure(@NonNull Call<NewsAPI_Response> call, @NonNull Throwable t)
                {
                    listener.onError("Request failed!!");
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
