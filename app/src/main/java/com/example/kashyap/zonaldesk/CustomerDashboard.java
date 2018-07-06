package com.example.kashyap.zonaldesk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class CustomerDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String PREFS_NAME = "PreferencesFile";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);
        mDrawerLayout = findViewById(R.id.drawer_layout);


        mToggle = new ActionBarDrawerToggle(CustomerDashboard.this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.NavigationView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home) {
            Toast.makeText(this, "This is home", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.Status) {
            Intent intent = new Intent(CustomerDashboard.this, WebTrial.class);
            intent.putExtra("Activity", "Customer");
            intent.putExtra("URL", "https://faq.whatsapp.com/en/android/26000031/");
            startActivity(intent);
        }
        if (id == R.id.contact) {
            Intent phone = new Intent(Intent.ACTION_DIAL);
            phone.setData(Uri.parse("tel:8073194842"));
            startActivity(phone);
        }
        if (id == R.id.history) {
            Intent intent = new Intent(CustomerDashboard.this, WebTrial.class);
            intent.putExtra("Activity", "Customer");
            intent.putExtra("URL", "https://www.google.co.in/search?q=history&oq=history&aqs=chrome..69i57.3041j0j4&sourceid=chrome&ie=UTF-8");
            startActivity(intent);
        }
        if (id == R.id.log) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CustomerDashboard.this);
            builder.setTitle(R.string.app_name);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage("Do you really want to LogOut?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //new LoginScreen().forgetUser();
                    forgetUser();
                    finish();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

            //To do later
        }
        return false;
    }

    public void IntentToFeedback(View view) {
        Intent intent = new Intent(CustomerDashboard.this, FeedbackForm.class);
        startActivity(intent);
    }

    public void ServiceRequestIntent(View view) {
        Intent intent = new Intent(CustomerDashboard.this, ServiceRequest.class);
        startActivity(intent);
    }

    public void forgetUser() {
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .edit()
                .putString(PREF_USERNAME, null)
                .putString(PREF_PASSWORD, null)
                .apply();
    }

    public void MapsIntent(View view) {

        Intent intent = new Intent(CustomerDashboard.this,MapsActivity.class);
        startActivity(intent);
    }
}
