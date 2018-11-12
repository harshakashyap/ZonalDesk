package com.example.dev.zonaldesk;

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
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

public class BackgroundProcessConfirmEmail extends AsyncTask<String, Void, String> {

    Context context;
    AlertDialog alertDialog;
    String email;
    String OTP;
    String confirmURL;
    String IP;
    //confirmURL = "http://192.168.0.108:80/ZonalDeskScripts/FakeMail.php";

    BackgroundProcessConfirmEmail(Context context) {
        this.context = context;
    }


    @Override
    protected String doInBackground(String... params) {


        OTP = getSaltString();
        Log.d("OTP", "doInBackground: " + OTP);
        String operation = params[0];
        email = params[1];

        IP = readFromFile(context);
        confirmURL = "https://" + IP + "/ZonalDeskScripts/FakeMail.php";


        if (operation.equals("Confirm")) {

            try {

                URL url = new URL(confirmURL);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String postData;

                postData = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                        + URLEncoder.encode("OTP", "UTF-8") + "=" + URLEncoder.encode(OTP, "UTF-8");

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
                Log.d("OTP123",result);

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();


                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
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
        alertDialog.setTitle("Email Confirmation Status");
    }

    @Override
    protected void onPostExecute(String result) {
        //super.onPostExecute(aVoid);
        alertDialog.setMessage(result);
        alertDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public String getOTP() {
        return OTP;
    }

    protected String getSaltString() {
        //String SALTCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String SALTCHARS = "1234567890";

        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

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





