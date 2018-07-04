package com.example.kashyap.zonaldesk;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundProcessUserCheck extends AsyncTask<String, Void, String> {


    Context context;
    AlertDialog alertDialog;
    String result = "Wait";

    BackgroundProcessUserCheck(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {


        String userCheckURL;//= "http://192.168.0.108:80/ZonalDeskScripts/FakeUserCheck.php";
        String operation = params[0];

        String IP = readFromFile(context);
        userCheckURL = "http://" + IP + ":80/ZonalDeskScripts/FakeUserCheck.php";


        if (operation.equals("UserCheck")) {

            Log.d("BackgroundUserCheck", "inside class :" + result);

            String phone = params[1];
            String email = params[2];

            try {

                URL url = new URL(userCheckURL);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String postData;

                postData = URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8") + "&"
                        + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");

                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));


                //String result = "";
                result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.d("BackgroundUserCheck", "Before return :" + result);
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
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
        alertDialog.setTitle("User Check");
    }

    @Override
    protected void onPostExecute(String result) {
        //super.onPostExecute(aVoid);
        Log.d("BackgroundUserCheck", "postexec :" + result);
        this.result = result;
        if (result.equals("No")) {
            alertDialog.setMessage("EmailID / Phone is already registered!");
            //alertDialog.show();
            Toast.makeText(context, "EmailID / Phone is already registered!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public String getResult() {
        Log.d("BackgroundUserCheck", "in getResult :" + result);
        return result;
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
