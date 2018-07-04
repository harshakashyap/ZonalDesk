package com.example.kashyap.zonaldesk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class FeedbackForm extends AppCompatActivity {

    Button submit;
    EditText suggestion;
    Intent Email;
    RatingBar rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_form);

        submit = findViewById(R.id.Submit);
        suggestion = findViewById(R.id.Suggestion);
        rating = findViewById(R.id.question1bar);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Email = new Intent(Intent.ACTION_SEND);
                Email.putExtra(Intent.EXTRA_EMAIL, new String[]{"sagniklahiri5@gmail.com"});
                Email.putExtra(Intent.EXTRA_SUBJECT, "Regarding feedback suggestion");
                Email.putExtra(Intent.EXTRA_TEXT, suggestion.getText());
                Email.setType("message/rfc822");
                startActivity(Intent.createChooser(Email, "Choose an Email client"));
            }
        });
    }
}
