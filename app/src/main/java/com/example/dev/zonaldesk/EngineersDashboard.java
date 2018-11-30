package com.example.dev.zonaldesk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class EngineersDashboard extends AppCompatActivity {
    public static final String PREFS_NAME = "PreferencesFile";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";
    Button logout;
    ImageView imageView;
    LinearLayout l1, l2, l3, l4, l5, l6, l7, l8, l9;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineers_dashboard);
        logout = findViewById(R.id.logout_button);

        l1 = findViewById(R.id.layout1);
        l2 = findViewById(R.id.layout2);
        l3 = findViewById(R.id.layout3);
        l4 = findViewById(R.id.layout4);
        l5 = findViewById(R.id.layout5);
        l6 = findViewById(R.id.layout6);
        l7 = findViewById(R.id.layout7);
        l8 = findViewById(R.id.layout8);
        l9 = findViewById(R.id.layout9);

        intent = new Intent(EngineersDashboard.this, WebTrial.class);
        intent.putExtra("Activity", "Engineer");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EngineersDashboard.this);
                builder.setTitle(R.string.app_name);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("Do you really want to LogOut?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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
            }
        });

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("URL", "https://en.wikipedia.org/wiki/Customer");
                startActivity(intent);

            }
        });
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("URL", "https://en.wikipedia.org/wiki/Request");
                startActivity(intent);

            }
        });
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("URL", "https://en.wikipedia.org/wiki/Pending");
                startActivity(intent);

            }
        });
        l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("URL", "https://en.wikipedia.org/wiki/Priority");
                startActivity(intent);
            }
        });
        l5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("URL", "https://en.wikipedia.org/wiki/Respond");
                startActivity(intent);

            }
        });
        l6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("URL", "https://en.wikipedia.org/wiki/Information");
                startActivity(intent);

            }
        });
        l7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("URL", "https://en.wikipedia.org/wiki/View");
                startActivity(intent);

            }
        });
        l8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("URL", "https://en.wikipedia.org/wiki/Labourers");
                startActivity(intent);

            }
        });
        l9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("URL", "https://en.wikipedia.org/wiki/Call");
                startActivity(intent);

            }
        });
    }

    public void forgetUser() {
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .edit()
                .putString(PREF_USERNAME, null)
                .putString(PREF_PASSWORD, null)
                .apply();
    }
}
