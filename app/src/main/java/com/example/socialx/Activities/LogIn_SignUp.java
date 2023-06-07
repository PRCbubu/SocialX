package com.example.socialx.Activities;

import android.content.Context;
import android.os.Looper;
import android.os.Message;
import android.widget.CheckBox;
import android.widget.EditText;

import android.os.Handler;

import androidx.annotation.NonNull;

import com.example.socialx.Database.UserDatabase;
import com.example.socialx.entities.UserEntities;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class LogIn_SignUp
{
    private EditText Email;
    private EditText Password;

    private EditText MobileNo;

    private EditText Name;
    private CheckBox Toc;
    private Context context;
    UserDatabase userDatabase;

    ExecutorService service;
    Handler handler;

    protected interface SignInConfirmationCallBack
    {
        void onResult(boolean isSignedIn);
    }

    protected interface SignUpProcessCallBack
    {
        void onResult(int isSuccessRegister);
    }

    public LogIn_SignUp() {}

    public LogIn_SignUp(EditText email, EditText password, Context context)
    {
        Email = email;
        Password = password;
        this.context = context;
        userDatabase = UserDatabase.getDatabase(this.context);
    }

    public LogIn_SignUp(EditText email, EditText password, EditText name, EditText mobileNo, CheckBox toc, Context context)
    {
        Email = email;
        Password = password;
        Name = name;
        MobileNo = mobileNo;
        Toc = toc;
        this.context = context;
        userDatabase = UserDatabase.getDatabase(this.context);
    }

    public void signIn_Confirmation(SignInConfirmationCallBack callback)
    {
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        service = Executors.newCachedThreadPool();
        handler = new Handler(Looper.getMainLooper(), new Handler.Callback()
        {
            @Override
            public boolean handleMessage(@NonNull Message message)
            {
                return false;
            }
        });
        service.submit(new Runnable()
        {
            @Override
            public void run()
            {
                UserEntities entities = userDatabase.userDao().findUserByEmailAndPassword(email, password);
                userDatabase.userDao().updateLoggedInCheck(email, password, true);
                handler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        callback.onResult(entities != null);
                    }
                });
            }
        });
    }


    public void signUp_Process(SignUpProcessCallBack callBack)
    {
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        String mobileNo = MobileNo.getText().toString();
        String name = Name.getText().toString();

        service = Executors.newCachedThreadPool();

        //Pre_execute
        int result;
        if(Toc.isChecked())
        {
            if(!email.isEmpty() && !password.isEmpty() && !mobileNo.isEmpty() && !name.isEmpty())
            {
                result = 1;
            }
            else
                result = 0;
        }
        else
            result = -1;
        handler = new Handler(Looper.getMainLooper(), new Handler.Callback()
        {
            @Override
            public boolean handleMessage(@NonNull Message message)
            {
                return false;
            }
        });

        service.submit(new Runnable()
        {
            @Override
            public void run()
            {
                //onBackgroundTask
                userDatabase.userDao().insertDetails(new UserEntities(email,password,name,mobileNo, true));

                handler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        //onPostExecute
                        callBack.onResult(result);
                    }
                });
            }
        });
    }

    public void logOut_Process()
    {
        ExecutorService service = Executors.newCachedThreadPool();
        Handler handler = new Handler(Looper.getMainLooper());

        service.submit(new Runnable()
        {
            @Override
            public void run()
            {
               userDatabase.userDao().setToLogOut();
            }
        });
    }
}
