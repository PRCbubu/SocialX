package com.example.socialx.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import com.example.socialx.HolderAndAdapter.recyclerViewAdapter;
import com.example.socialx.R;
import com.example.socialx.REST.APIClient;

import com.example.socialx.REST.onFetchDataListener;
import com.example.socialx.model.NewsAPI_Response;
import com.example.socialx.model.NewsAPI_User;

import java.util.List;


public class NewsFeed extends AppCompatActivity
{
    RecyclerView recyclerView;
    recyclerViewAdapter adapter;

    private final onFetchDataListener<NewsAPI_Response> listener = new onFetchDataListener<NewsAPI_Response>()
    {
        @Override
        public void onFetchData(List<NewsAPI_User> list, String message)
        {
            shownews(list);
        }

        @Override
        public void onError(String message)
        {

        }
    };

    private void shownews(List<NewsAPI_User> list)
    {
        recyclerView = findViewById(R.id.recyclear_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new recyclerViewAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

       APIClient client = new APIClient(this);
       client.getNewsHeadlines(listener);
    }



}