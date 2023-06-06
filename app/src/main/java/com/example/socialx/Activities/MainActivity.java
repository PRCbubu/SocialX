package com.example.socialx.Activities;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialx.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
{
    private static final int RC_SIGN_IN = 1010;
    private GoogleSignInClient mGoogleSignInClient;

    private Button login, signup, login_signup_button;

    private MaterialCardView Login_Page, Signup_Page;

    private  ActivityResultLauncher<Intent> signInLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String title = "Social";
        String subtitle = "X";
        String formattedTitle = title + "<br><big>" + subtitle + "</big></b>";
        setTitle(HtmlCompat.fromHtml(formattedTitle, HtmlCompat.FROM_HTML_MODE_LEGACY));

        //This is to change the actionbar into red colour
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.MainRed)));

        //Database Login Process
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        TextView registerNow = findViewById(R.id.RegisterNow);
        TextView signIn_Text = findViewById(R.id.SignIn_text);
        Login_Page = findViewById(R.id.Login_page);
        Signup_Page = findViewById(R.id.Signup_page);
        login_signup_button = findViewById(R.id.login_signup_button);

        login_signup_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                databaseLogIn();
            }
        });

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Login_Page.setVisibility(View.VISIBLE);
                login.setBackgroundResource(R.drawable.selected_card);
                signup.setBackground(null);
                Signup_Page.setVisibility(View.GONE);
                login_signup_button.setText(R.string.login);
                login_signup_button.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        databaseLogIn();
                    }
                });
            }
        });

        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Login_Page.setVisibility(View.GONE);
                signup.setBackgroundResource(R.drawable.selected_card);
                login.setBackground(null);
                Signup_Page.setVisibility(View.VISIBLE);
                Login_Page.setVisibility(View.GONE);
                login_signup_button.setText(R.string.sign_up);

                login_signup_button.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        databaseSignUp();
                    }
                });
            }
        });

        registerNow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                signup.performClick();
            }
        });

        signIn_Text.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                login.performClick();
            }
        });


        //Google SignIn option
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        ImageButton googleButton = findViewById(R.id.GoogleIcon);
        Picasso.get().load("https://developers.google.com/static/identity/images/g-logo.png").into(googleButton);


        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if(account != null)
            navigateToSecondActivity();
        
        googleButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                signInFromGoogle();
            }
        });

        signInLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>()
                {
                    @Override
                    public void onActivityResult(ActivityResult result)
                    {
                        // Handle the result here
                        if (result.getResultCode() == Activity.RESULT_OK)
                        {
                            Intent data = result.getData();
                            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                            handleSignInResult(task);
                        }
                    }
                });

    }

    private void databaseSignUp()
    {
        //Logic for Signing up for the application
        EditText Name = findViewById(R.id.Name);
        EditText Email2 = findViewById(R.id.Email2);
        EditText mobileNo = findViewById(R.id.mobileNo);
        EditText Password2 = findViewById(R.id.Password2);
        CheckBox ToC = findViewById(R.id.checkbox);

        LogIn_SignUp logInSignUp = new LogIn_SignUp(Email2, Password2,Name, mobileNo, ToC, this);

        logInSignUp.signUp_Process(new LogIn_SignUp.SignUpProcessCallBack()
        {
            @Override
            public void onResult(int isSuccessRegister)
            {
                if(isSuccessRegister == 1)
                    navigateToSecondActivity();
                else if(isSuccessRegister == 0)
                    Toast.makeText(MainActivity.this, "Please enter all the required details", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Please check the checkbox", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void databaseLogIn()
    {
        //Logic for UserName and Password login

        EditText Email = findViewById(R.id.Email);
        EditText Password = findViewById(R.id.Password);

        LogIn_SignUp logInSignUp = new LogIn_SignUp(Email, Password, this);

        logInSignUp.signIn_Confirmation(isSignedIn ->
        {
            if(isSignedIn)
                navigateToSecondActivity();
            else
                Toast.makeText(MainActivity.this, "Please enter your correct EmailID and Password", Toast.LENGTH_SHORT).show();
        });

    }

    private void signInFromGoogle()
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        signInLauncher.launch(signInIntent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Log.i("Account", account.getDisplayName());

            navigateToSecondActivity();

        } catch (ApiException e) {
            Toast.makeText(this, "inside of ApiException", Toast.LENGTH_SHORT).show();
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Log.e("signInResult", "" + e.getStatusCode());
        }
    }



    private void navigateToSecondActivity()
    {
        finish();
        Intent intent = new Intent(MainActivity.this, NewsFeed.class);
        startActivity(intent);
    }

}