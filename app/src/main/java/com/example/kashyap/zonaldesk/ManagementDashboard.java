package com.example.kashyap.zonaldesk;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class ManagementDashboard extends AppCompatActivity {

    public static final String PREFS_NAME = "PreferencesFile";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";
    CardView call_records;
    CardView performance_report;
    CardView call_history;
    CardView key_areas;
    CardView feedback;
    CardView rating;
    Intent intent;
    FloatingActionButton logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_dashboard);

        // logout=(FloatingActionButton)findViewById(R.id.logout);
        call_records = findViewById(R.id.card1);
        performance_report = findViewById(R.id.card2);
        call_history = findViewById(R.id.card3);
        key_areas = findViewById(R.id.card4);
        feedback = findViewById(R.id.card5);
        rating = findViewById(R.id.card6);
        intent = new Intent(ManagementDashboard.this, WebTrial.class);
        intent.putExtra("Activity", "Management");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log:
                // Toast.makeText(ManagementDashboard.this, "noob chirag", Toast.LENGTH_SHORT).show();
                forgetUser();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void rating(View view) {
        intent.putExtra("URL", "https://www.websiteplanet.com/optimal2/?tools=website-builders&targetid=kwd-296983900309&matchtype=b&device=c&campaignid=927451137&creative=272416449572&adgroupid=47028502020&feeditemid=&loc_physical_ms=9062045&loc_interest_ms=&network=s&devicemodel=&placement=&keyword=+make%20+web%20+page&target=&aceid=&adposition=1t1&[search]&&gclid=Cj0KCQjwjtLZBRDLARIsAKT6fXwHISOBxb9P2TLt288wLpxW8zrGQGWQIFAa7Lxr1rPHWVyUjMTNP24aAutCEALw_wcB");
        startActivity(intent);
    }

    public void Feedback(View view) {
        intent.putExtra("URL", "https://www.google.co.in/search?ei=ZMg1W5m0JJf6rQGut4GQDg&q=feedback&oq=feedback&gs_l=psy-ab.3..0i67k1j0j0i67k1j0l2j0i67k1j0l4.218176.219287.0.219578.8.8.0.0.0.0.224.778.0j4j1.5.0....0...1.1.64.psy-ab..3.5.775...0i131i67k1j0i131k1.0.LfO2gOlhCJ8");
        startActivity(intent);
    }

    public void Records(View view) {
        intent.putExtra("URL", "https://www.google.co.in/search?ei=Qck1W_DMCtS0rQG33oHgCg&q=Call+records&oq=Call+records&gs_l=psy-ab.3..0l10.1246326.1248650.0.1248874.12.11.0.0.0.0.301.1407.0j6j1j1.8.0....0...1.1.64.psy-ab..4.8.1404...0i131k1j0i67k1j0i3k1.0.sntCsy02OBU");
        startActivity(intent);
    }

    public void Performance(View view) {
        intent.putExtra("URL", "https://www.google.co.in/search?ei=I841W6HlJ8Xp9QPZvYPQDQ&q=performance&oq=performance&gs_l=psy-ab.3..0i67k1j0l9.88851.92555.0.92767.16.11.5.0.0.0.151.1000.0j7.7.0....0...1.1.64.psy-ab..4.12.1036...0i131k1.0.eLmVOwg-hkY");
        startActivity(intent);
    }

    public void CallHistory(View view) {
        intent.putExtra("URL", "https://www.google.co.in/search?ei=gc41W9XSIc-f9QOxoZ_YBw&q=Call+history&oq=Call+history&gs_l=psy-ab.3...175214.176741.0.177039.12.8.0.0.0.0.0.0..0.0....0...1.1.64.psy-ab..12.0.0....0.83GnLd9Ahng");
        startActivity(intent);
    }

    public void KeyAreas(View view) {
        intent.putExtra("URL", "https://www.google.co.in/search?ei=N881W_qKCcTgrQGg14fgCQ&q=Key+Areas&oq=Key+Areas&gs_l=psy-ab.3..0l10.105874.107179.0.107862.9.9.0.0.0.0.167.886.0j6.6.0....0...1.1.64.psy-ab..3.6.883...0i131k1j0i67k1j0i131i67k1j0i10k1.0.8-8qRIENqsw");
        startActivity(intent);
    }

    public void forgetUser() {
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .edit()
                .putString(PREF_USERNAME, null)
                .putString(PREF_PASSWORD, null)
                .apply();
    }



    /*public void logout(View view) {
        forgetUser();
        finish();
    }*/
}
