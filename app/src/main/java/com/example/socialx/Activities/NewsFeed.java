package com.example.socialx.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.socialx.HolderAndAdapter.recyclerViewAdapter;
import com.example.socialx.R;
import com.example.socialx.REST.APIClient;

import com.example.socialx.REST.onFetchDataListener;
import com.example.socialx.model.NewsAPI_Response;
import com.example.socialx.model.NewsAPI_Article;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NewsFeed extends AppCompatActivity
{
    RecyclerView recyclerView;
    recyclerViewAdapter adapter;

    SearchView searchView;
    CardView cardView;
    ProgressBar progressBar;

    private final onFetchDataListener<NewsAPI_Response> listener = new onFetchDataListener<>()
    {
        @Override
        public void onFetchData(List<NewsAPI_Article> list, String message)
        {
            if(list.isEmpty())
                Toast.makeText(NewsFeed.this, "No data found!", Toast.LENGTH_SHORT).show();
            else
            {
                shownews(list);
                progressBar.setVisibility(View.GONE);
                cardView.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onError(String message)
        {
            Toast.makeText(NewsFeed.this, "An error occurred", Toast.LENGTH_SHORT).show();
        }
    };

    private void shownews(List<NewsAPI_Article> list)
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

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.MainBlue)));

        // Get the window object.
        Window window = getWindow();

        // Set the status bar color.
        window.setStatusBarColor(getResources().getColor(R.color.MainBlue));

        progressBar = findViewById(R.id.loadingBar);
        progressBar.setVisibility(View.VISIBLE);
        cardView = (CardView) findViewById(R.id.search);

        APIClient client = new APIClient(NewsFeed.this);
        client.getNewsHeadlines(listener, "in", "general", null);

        searchView = findViewById(R.id.search_view);

        Map<String, String> countries = new HashMap<>();

        // Populate the map with country names and their 2-letter ISO codes
        for (Locale locale : Locale.getAvailableLocales())
        {
            String countryName = locale.getDisplayCountry().toLowerCase();
            String countryCode = locale.getCountry().toLowerCase();
            if (!countryName.isEmpty() && !countryCode.isEmpty())
            {
                countries.put(countryName, countryCode);
            }
        }
        List<String> countryNames = new ArrayList<>(countries.keySet());


        ArrayList<String> categoryNames = new ArrayList<>(Arrays.asList("business", "entertainment", "general", "health", "science", "sports", "technology"));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            String country;
            String category;

            @Override
            public boolean onQueryTextSubmit(String query)
            {
                if(matchRequired(countryNames, query.toLowerCase()) == null)
                {
                    country = "in";
                }
                else
                {
                    country = countries.get(matchRequired(countryNames, query.toLowerCase()));
                    Log.i("CountryNames:", matchRequired(countryNames, query.toLowerCase())+" : "+countries.get(matchRequired(countryNames, query.toLowerCase())));
                }

                if(matchRequired(categoryNames, query) == null)
                {
                    category = "general";
                }
                else
                {
                    Toast.makeText(NewsFeed.this,  ""+matchRequired(categoryNames, query), Toast.LENGTH_SHORT).show();
                    category = matchRequired(categoryNames, query);
                }

                client.getNewsHeadlines(listener, country, category, query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                return false;
            }
        });
    }

    private String matchRequired(List<String> list, String query)
    {
        String patternString = String.join("|", list);
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(query);

        if(matcher.find())
        {
            return "" + matcher.group();
        }
        else
        {
            return null;
        }

    }
}
