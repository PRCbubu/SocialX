package com.example.socialx.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.socialx.Database.UserDatabase;
import com.example.socialx.HolderAndAdapter.recyclerViewAdapter;
import com.example.socialx.R;
import com.example.socialx.REST.APIClient;

import com.example.socialx.REST.onFetchDataListener;
import com.example.socialx.dao.UserDao;
import com.example.socialx.model.NewsAPI_Response;
import com.example.socialx.model.NewsAPI_Article;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

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

    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;

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
        client.getNewsHeadlines(listener, "gb", "general", null);

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
        countries.put("Britain", "GB");
        countries.put("America", "US");

        List<String> countryNames = new ArrayList<>(countries.keySet());
        Log.i("Countries", countryNames.toString() + "\n");


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
                    category = matchRequired(categoryNames, query);
                }

                String countryToRemove = matchRequired(countryNames, query.toLowerCase());
                String categoryToRemove = matchRequired(categoryNames, query);

                if (countryToRemove != null) {
                    query = query.replace(countryToRemove, "");
                }
                if (categoryToRemove != null) {
                    query = query.replace(categoryToRemove, "");
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

        //Google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        Button button = findViewById(R.id.SignOut);
        button.setText("log Out");

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SharedPreferences sharedPreferences1 = getSharedPreferences("LogIn", MODE_PRIVATE);
                SharedPreferences sharedPreferences2 = getSharedPreferences("SignUp", MODE_PRIVATE);

                boolean buttonClicked2 = sharedPreferences2.getBoolean("SignUp", false);
                boolean buttonClicked1 = sharedPreferences1.getBoolean("LogIn", false);
                if(buttonClicked1 && buttonClicked2)
                    databaseLogOut();
                else
                    googleSignOut();
            }
        });

    }

    private void databaseLogOut()
    {
        LogIn_SignUp logInSignUp = new LogIn_SignUp();
        logInSignUp.logOut_Process();
        finish();
        startActivity(new Intent(NewsFeed.this, MainActivity.class));
    }

    private void googleSignOut()
    {
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                finish();
                startActivity(new Intent(NewsFeed.this, MainActivity.class));
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
