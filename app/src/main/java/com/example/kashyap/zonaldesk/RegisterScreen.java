package com.example.kashyap.zonaldesk;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterScreen extends AppCompatActivity {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    EditText name;
    EditText phone;
    EditText confirm_password;
    EditText email;
    EditText password;
    TextInputLayout name_layout;
    TextInputLayout phone_layout;
    TextInputLayout address_layout;
    TextInputLayout email_layout;
    TextInputLayout password_layout;
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        return password.length() > 5;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Log.d("NotWorking", "1");
        name = findViewById(R.id.Name);
        phone = findViewById(R.id.Phone);
        confirm_password = findViewById(R.id.Address);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        Log.d("NotWorking", "2");
//E/MPlugin: Unsupported class: com.mediatek.common.telephony.IOnlyOwnerSimSupport
        name_layout = findViewById(R.id.name_text);
        phone_layout = findViewById(R.id.phone_text);
        address_layout = findViewById(R.id.address_text);
        email_layout = findViewById(R.id.email_text);
        password_layout = findViewById(R.id.password_text);
        Log.d("NotWorking", "3");
    }

    public void signup(View view) {
        Log.d("NotWorking", "First1");
        if (phone.getText().toString().length() != 10) {

            phone_layout.setError("Invalid phone number");
        } else if (!validateEmail(email.getText().toString())) {
            email_layout.setError("Invalid email address");
        } else if (!validatePassword(password.getText().toString())) {
            password_layout.setError("Password must contain atleast 6 characters");
        } else if (!(password.getText().toString().equals(confirm_password.getText().toString()))) {
            address_layout.setError("Password does not match");
        } else {
            Log.d("NotWorking", "Good1");
            phone_layout.setError(null);
            email_layout.setError(null);
            password_layout.setError(null);
            address_layout.setError(null);
            Log.d("NotWorking", "GoodGod1");


            BackgroundProcessUserCheck backgroundProcessUserCheck = new BackgroundProcessUserCheck(this);
            backgroundProcessUserCheck.execute("UserCheck", phone.getText().toString(), email.getText().toString());

            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String result = backgroundProcessUserCheck.getResult();

            Log.d("BackgroundUserCheck", "Before IF :" + result);

            if (result.equals("No")) {
                Toast.makeText(this, "EmailID / Phone already registered!", Toast.LENGTH_LONG).show();
                AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Registration Error");
                alertDialog.setMessage("EmailID / Phone is already registered!");
                alertDialog.show();
                //System.exit(0);
            } else {


                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                BackgroundProcessConfirmEmail backgroundProcessConfirmEmail = new BackgroundProcessConfirmEmail(this);
                backgroundProcessConfirmEmail.execute("Confirm", email.getText().toString());


                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                String OTP = backgroundProcessConfirmEmail.getOTP();

                Intent intent = new Intent(RegisterScreen.this, OTP.class);
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("phone", phone.getText().toString());
                intent.putExtra("email", email.getText().toString());
                intent.putExtra("password", password.getText().toString());
                intent.putExtra("OTP", OTP);

                name.setText("");
                phone.setText("");
                email.setText("");
                password.setText("");
                confirm_password.setText("");

                startActivity(intent);
            }





            /*Toast.makeText(this,"Registered Successfully",Toast.LENGTH_SHORT).show();
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            //System.exit(0);
        }
    }

    public void Cancel(View view) {
        System.exit(0);
    }
}