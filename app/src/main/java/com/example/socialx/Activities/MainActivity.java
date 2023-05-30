package com.example.socialx.Activities;

import static com.example.socialx.Database.UserDatabase.getDatabase;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialx.Database.UserDatabase;
import com.example.socialx.R;
import com.example.socialx.entities.UserEntities;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


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

        //to set the colour of actionbar colour to red
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.MainRed)));
        }

//
        //if(login_page.getVisibility() != View.GONE)
        RegisterNow.setOnClickListener(this::signUp);

        //if(Signup_page.getVisibility() != View.GONE)
        SignIn_text.setOnClickListener(this::logIn);
    }

    public void logIn(View view)
    {
        SignUp.setBackground(null);
        LogIn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.selected_card, null));
        login_page.setVisibility(View.VISIBLE);
        Signup_page.setVisibility(View.GONE);
        login_signup_button.setText(getResources().getText(R.string.login));
        login_signup_button.setTag(getResources().getText(R.string.login));

        String email = Email.getText().toString();
        String password = Password.getText().toString();

        ExecutorService service = Executors.newSingleThreadExecutor();
        UserDatabase userDatabase = getDatabase(this);

        //running the room database in the background
        service.execute(() -> {


            //Entry into the database
            UserEntities existingUser = userDatabase.userDao().findUserByEmailOrPassword(email, password);

            if (existingUser == null)
            {
                runOnUiThread(() -> Toast.makeText(this, "Email ID or Password is incorrect", Toast.LENGTH_SHORT).show());
            }
            else
            {
                try
                {
                    service.shutdown();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


                //giving a message out that the database is being used to add the details
                //After adding the details into the database, it is redirecting to the next activity
                runOnUiThread(() -> {


                    if (service.isTerminated())
                    {
                        Intent intent = new Intent(MainActivity.this, NewsFeed.class);
                        startActivity(intent);
                        //finish();
                    }


                });
            }



        });

    }

    public void signUp(View view)
    {
        LogIn.setBackground(null);
        SignUp.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.selected_card, null));
        login_page.setVisibility(View.GONE);
        Signup_page.setVisibility(View.VISIBLE);
        login_signup_button.setText(getResources().getText(R.string.sign_up));
        login_signup_button.setTag(getResources().getText(R.string.sign_up));

        ExecutorService service = Executors.newFixedThreadPool(10);
        UserDatabase userDatabase = getDatabase(this);

        //running the room database in the background
        login_signup_button.setOnClickListener(view1 -> service.execute(() -> {

            if (Name.getText().toString().isEmpty() || mobileNo.getText().toString().isEmpty() || Email2.getText().toString().isEmpty() || Password2.getText().toString().isEmpty()) {
                // Show an error message to the user
                runOnUiThread(() -> Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show());
            } else {
                //Entry into the database
                UserEntities existingUser = userDatabase.userDao().findUserByEmailOrNumber(Email2.getText().toString(), mobileNo.getText().toString());

                if (existingUser == null) {
                    userDatabase.userDao().insertDetails(new UserEntities(Email2.getText().toString(), Password2.getText().toString(), Name.getText().toString(), mobileNo.getText().toString()));
                } else {
                    runOnUiThread(() -> Toast.makeText(this, "Email or Number already exists", Toast.LENGTH_SHORT).show());
                }

                //giving a message out that the database is being used to add the details
                //After adding the details into the database, it is redirecting to the next activity
                runOnUiThread(() -> {
                    Toast.makeText(this, "Adding data...", Toast.LENGTH_SHORT).show();

                    try {
                        service.shutdown();
                        service.awaitTermination(10, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (service.isTerminated()) {
                        Intent intent = new Intent(MainActivity.this, NewsFeed.class);
                        startActivity(intent);
                        //finish();
                    }
                });
            }
        }));
    }

    public void GoogleLogIn(View view)
    {
    }

    public void FacebookLogin(View view)
    {
    }
}