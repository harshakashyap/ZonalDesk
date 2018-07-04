package com.example.kashyap.zonaldesk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.concurrent.TimeUnit;

public class LoginScreen extends AppCompatActivity {

    public static final String PREFS_NAME = "PreferencesFile";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";
    CallbackManager callbackManager;
    TextView email, pswd;
    String emailID, password;
    Boolean rememberMe;
    Intent intent;
    CheckBox checkBox;
    String result;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen2);


        email = findViewById(R.id.email);
        pswd = findViewById(R.id.pswd);
        checkBox = findViewById(R.id.checkBox);

        Intent intentFromRegister = getIntent();
        email.setText(intentFromRegister.getStringExtra("email"));


        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        emailID = sharedPreferences.getString(PREF_USERNAME, null);
        password = sharedPreferences.getString(PREF_PASSWORD, null);

        Log.d("SP", "onCreate: after assignment ");


        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        startActivity(intent);
                        System.exit(0);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        if (emailID != null && password != null) {

            Log.d("SP", "onCreate: in login func." + emailID + " " + password);

            loginFunction();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void login(View view) {


        //if(emailID == null || password == null) {


        emailID = email.getText().toString();
        password = pswd.getText().toString();
        rememberMe = checkBox.isChecked();

        email.setText("");
        pswd.setText("");

        if (rememberMe) {

            getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                    .edit()
                    .putString(PREF_USERNAME, emailID)
                    .putString(PREF_PASSWORD, password)
                    .apply();

            loginFunction();


        } else {
            forgetUser();
            loginFunction();
        }

    }

       /* else{
            loginFunction();
        }*/


    // }

    public void register(View view) {
        Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
        startActivity(intent);
    }

    public void forgetUser() {
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .edit()
                .putString(PREF_USERNAME, null)
                .putString(PREF_PASSWORD, null)
                .apply();
    }

    public void loginFunction() {

        BackgroundProcessLogin backgroundProcessLogin = new BackgroundProcessLogin(this);
        backgroundProcessLogin.execute("Login", emailID, password);
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        result = backgroundProcessLogin.getResult();

        if (result.equals("Customer")) {  //Tip is wrong Try both always
            Toast.makeText(this, "Welcome " + result, Toast.LENGTH_SHORT).show();

            intent = new Intent(LoginScreen.this, CustomerDashboard.class);
            startActivity(intent);
        } else if (result.equals("Manager")) {
            Toast.makeText(this, "Welcome " + result, Toast.LENGTH_SHORT).show();
            intent = new Intent(LoginScreen.this, ManagementDashboard.class);
            startActivity(intent);

        } else if (result.equals("Engineer")) {
            Toast.makeText(this, "Welcome " + result, Toast.LENGTH_SHORT).show();
            intent = new Intent(LoginScreen.this, EngineersDashboard.class);
            startActivity(intent);

        } else {
            Toast.makeText(LoginScreen.this, "" + result, Toast.LENGTH_SHORT).show();
        }

    }
}

