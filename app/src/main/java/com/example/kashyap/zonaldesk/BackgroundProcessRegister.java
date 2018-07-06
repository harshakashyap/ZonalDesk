package com.example.kashyap.zonaldesk;

/**
 * Created by kashyap on 26/6/18.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundProcessRegister extends AsyncTask<String, Void, String> {

    Context context;
    AlertDialog alertDialog;

    BackgroundProcessRegister(Context context) {
        this.context = context;
    }


    @Override
    protected String doInBackground(String... params) {

        String registerURL;// = "http://192.168.0.108:80/ZonalDeskScripts/FakeRegister.php";
        String operation = params[0];

        String IP = readFromFile(context);
        registerURL = "http://" + IP + ":80/ZonalDeskScripts/FakeRegister.php";


        if (operation.equals("Register")) {
            try {

                String name = params[1];
                String phone = params[2];
                String email = params[3];
                String password = params[4];

                URL url = new URL(registerURL);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String postData;
                postData = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                        + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8") + "&"
                        + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String result = "";
                String line = "";
                Log.d("GG", "doInBackground: result0"+result);
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                    Log.d("GG", "doInBackground: result0"+result);
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.d("GG", "doInBackground: result0"+result);

                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return null;
    }

    protected String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("IP.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

}
