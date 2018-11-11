package com.example.dev.zonaldesk;

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
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public class BackgroundProcessLogin extends AsyncTask<String, Void, String> {

    Context context;
    AlertDialog alertDialog;
    String postData;
    String result;
    String IP;
    String loginURL;

    BackgroundProcessLogin(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String operation = params[0];

        //String loginURL = "http://10.0.2.2/FakeLogin.php";
        //String loginURL = "http://localhost/FakeLogin.php";
        //String loginURL = "http://192.168.0.108:80/ZonalDeskScripts/FakeScript.php";

        IP = readFromFile(context);
        loginURL = "https://" + IP + "/ZonalDeskScripts/FakeLogin.php";


        if (operation.equals("Login")) {
            try {
                String emailID = params[1];
                String passWord = params[2];

                URL url = new URL(loginURL);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                postData = URLEncoder.encode("emailID", "UTF-8") + "=" + URLEncoder.encode(emailID, "UTF-8") + "&" + URLEncoder.encode("passWord", "UTF-8") + "=" + URLEncoder.encode(passWord, "UTF-8");


                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                this.result = result;
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

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {
        //super.onPostExecute(aVoid);
        alertDialog.setMessage("Welcome" + result + "!");
        //alertDialog.show();
    }

    String getResult() {
        Log.d("BackgroundProcessLogin", "" + this.result);
        return this.result;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
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
