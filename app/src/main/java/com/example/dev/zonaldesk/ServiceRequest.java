package com.example.dev.zonaldesk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ServiceRequest extends AppCompatActivity {

    Button Submit_problem;
    EditText problem_statement;
    RadioGroup list_of_problems;
    RadioButton problems;
    int counter = 999999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request);

        Submit_problem = findViewById(R.id.Submit_problem);
        problem_statement = findViewById(R.id.Statement);
        list_of_problems = findViewById(R.id.Radio_group);

        Submit_problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int problem_id = list_of_problems.getCheckedRadioButtonId();
                problems = findViewById(problem_id);
                counter++;

                Toast.makeText(ServiceRequest.this, "Ticket no is " + counter, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void feedback(View view) {
        Intent intent = new Intent(ServiceRequest.this, FeedbackForm.class);
        startActivity(intent);
    }
}
