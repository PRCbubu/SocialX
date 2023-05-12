package com.example.socialx.Activities;

import static com.example.socialx.Database.UserDatabase.getDatabase;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialx.Database.UserDatabase;
import com.example.socialx.R;
import com.example.socialx.entities.UserDetails;
import com.google.android.material.card.MaterialCardView;

import java.util.List;


public class MainActivity extends AppCompatActivity
{
    Button SignUp, LogIn;

    Button login_signup_button;

    MaterialCardView Signup_page, login_page;
    TextView RegisterNow, SignIn_text;

    //Email and password for Login Details
    EditText Email, Password;

    //These are for signup process
    EditText Name, Email2, mobileNo, Password2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String title = "Social";
        String subtitle = "X";
        String formattedTitle = title + "<br><big>" + subtitle + "</big></b>";
        //setTitle(formattedTitle);
        setTitle(HtmlCompat.fromHtml(formattedTitle, HtmlCompat.FROM_HTML_MODE_LEGACY));

        //Buttons for login and Sign up pages
        SignUp = (Button) findViewById(R.id.signup);
        LogIn = (Button) findViewById(R.id.login);

        //Button for signup or login
        login_signup_button = findViewById(R.id.login_signup_button);

        //layouts defined here because to be used in onClick buttons
        login_page = findViewById(R.id.login_page);
        Signup_page = findViewById(R.id.Signup_page);

        //TextViews when they're clicked, the same actions are performed as clicking tbe login/signup buttons
        RegisterNow = findViewById(R.id.RegisterNow);
        SignIn_text = findViewById(R.id.SignIn_text);

        //EmailID and password for Login
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);

        //Details for SignUp process
        Name = findViewById(R.id.Name);
        mobileNo = findViewById(R.id.mobileNo);
        Email2 = findViewById(R.id.Email2);
        Password2 = findViewById(R.id.Password2);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.MainRed)));

        }
//
        RegisterNow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(login_page.getVisibility() != View.GONE)
                    signUp(view);
            }
        });

        SignIn_text.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(Signup_page.getVisibility() != View.GONE)
                    logIn(view);
            }
        });
    }

    public void logIn(View view)
    {
        SignUp.setBackground(null);
        LogIn.setBackground(getResources().getDrawable(R.drawable.selected_card));
        login_page.setVisibility(View.VISIBLE);
        Signup_page.setVisibility(View.GONE);
        login_signup_button.setText(getResources().getText(R.string.login));
        login_signup_button.setTag(getResources().getText(R.string.login));

        String email = Email.getText().toString();
        String password = Password.getText().toString();

        UserDetails userDetails = new UserDetails();

        userDetails.setEmail(email);
        userDetails.setPassword(password);

        login_signup_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                boolean flag = verifyLogIn(userDetails);

                if(!flag)
                {
                    Toast.makeText(getApplicationContext(), "Invalid UserName or Password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this, NewsFeed.class);
                    startActivity(intent);
                }
            }
        });

    }

    public void signUp(View view)
    {
        LogIn.setBackground(null);
        SignUp.setBackground(getResources().getDrawable(R.drawable.selected_card));
        login_page.setVisibility(View.GONE);
        Signup_page.setVisibility(View.VISIBLE);
        login_signup_button.setText(getResources().getText(R.string.sign_up));
        login_signup_button.setTag(getResources().getText(R.string.sign_up));

        UserDetails userDetails = new UserDetails();

        userDetails.setName(Name.getText().toString());
        userDetails.setNumber(mobileNo.getText().toString());
        userDetails.setEmail(Email2.getText().toString());
        userDetails.setPassword(Password2.getText().toString());

        class DatabaseTask extends AsyncTask<Void, Void, Void>
        {

            @Override
            protected Void doInBackground(Void... voids)
            {
                getDatabase(getApplicationContext()).userDao().insertDetails(userDetails);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused)
            {
                super.onPostExecute(unused);
                logIn(view);
            }
        }

        login_signup_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new DatabaseTask().execute();
            }
        });
    }

    public void GoogleLogIn(View view)
    {
    }

    public void FacebookLogin(View view)
    {
    }

    private boolean verifyLogIn(UserDetails userDetails)
    {

        class DatabaseTask extends AsyncTask<Void, Void, Void>
        {
            public static List<UserDetails> userDetails1;

            @Override
            protected Void doInBackground(Void... voids)
            {
                userDetails1 = UserDatabase.getDatabase(getApplicationContext()).userDao().getEmailAndPassword(userDetails.getEmail(), userDetails.getPassword());
                return null;
            }

        }

        new DatabaseTask().execute();
        // User exists
        // User does not exist
        return DatabaseTask.userDetails1.contains(userDetails);
    }
}