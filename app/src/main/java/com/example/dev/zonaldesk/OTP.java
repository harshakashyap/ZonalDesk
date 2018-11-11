package com.example.dev.zonaldesk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {

    String name, phone, email, password, OTP;
    EditText ET_OTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        Intent registerIntent = getIntent();
        name = registerIntent.getStringExtra("name");
        phone = registerIntent.getStringExtra("phone");
        email = registerIntent.getStringExtra("email");
        password = registerIntent.getStringExtra("password");
        OTP = registerIntent.getStringExtra("OTP");
        ET_OTP = findViewById(R.id.ET_OTP);


    }

    public void onClickConfirm(View view) {


        if (ET_OTP.getText().toString().equals(OTP)) {

            Toast.makeText(this, "Please wait while your account is being created.", Toast.LENGTH_LONG).show();


            BackgroundProcessRegister backgroundProcessRegister = new BackgroundProcessRegister(this);
            backgroundProcessRegister.execute("Register", name, phone, email, password);

            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(OTP.this, LoginScreen.class);
            intent.putExtra("email", email);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Wrong. Please re-enter correct OTP", Toast.LENGTH_SHORT).show();
        }


    }
}
