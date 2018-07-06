package com.example.kashyap.zonaldesk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    ImageView zonal;
    TextView title;
    EditText ET_IP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        zonal = findViewById(R.id.zonal);
        title = findViewById(R.id.heading);
        ET_IP = findViewById(R.id.IP);
        //Button go;
        //go = findViewById(R.id.go);

        ET_IP.setText("192.168.137.1");




        /*Thread timer= new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    sleep(1000);
                    Intent intent=new Intent(MainActivity.this,LoginScreen.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();*/
    }

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("IP.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            //Toast.makeText(this, ""+getFilesDir(), Toast.LENGTH_SHORT).show();
            //Log.d("GG", "file at:-   "+getFilesDir());

        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void go(View view) {

        writeToFile(ET_IP.getText().toString(),this);

        Thread timer = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(10);
                    Intent intent = new Intent(MainActivity.this, LoginScreen.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();
    }
}