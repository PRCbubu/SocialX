package com.example.socialx.Activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.socialx.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
{
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton googleButton = findViewById(R.id.GoogleIcon);
        Picasso.get().load("https://developers.google.com/static/identity/images/g-logo.png").into(googleButton);

        String title = "Social";
        String subtitle = "X";
        String formattedTitle = title + "<br><big>" + subtitle + "</big></b>";
        //setTitle(formattedTitle);
        setTitle(HtmlCompat.fromHtml(formattedTitle, HtmlCompat.FROM_HTML_MODE_LEGACY));

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {

            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.MainRed)));
        }
        
        findViewById(R.id.login_signup_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, NewsFeed.class);

                startActivity(intent);
            }
        });

        //Google SignIn option
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

//        if(account != null)
//        {
//            navigateToSecondActivity();
//        }
        
        googleButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                signInFromGoogle();
            }
        });
    }

    private void signInFromGoogle()
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1010);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1010)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            finish();
            Intent intent = new Intent(MainActivity.this, NewsFeed.class);
            startActivity(intent);
            navigateToSecondActivity();

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Log.d("signInResult:failed code=", ""+ e.getStatusCode());
        }
    }

    private void navigateToSecondActivity()
    {

    }

}