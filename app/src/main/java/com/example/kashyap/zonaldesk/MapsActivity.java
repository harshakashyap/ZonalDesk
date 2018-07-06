package com.example.kashyap.zonaldesk;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private RequestQueue req;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("gg", "onCreate: oncreate");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
       // mMap = findViewById(R.id.map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Log.d("gg", "onCreate: oncreate");

        setUpMapIfNeeded();
        req=Volley.newRequestQueue(this);


    }
    LatLng loc;

    private void setUpMapIfNeeded() {




        if (mMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            if (mMap != null) {
                setUpMap();
            }

        }
    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

    }

    public void onSearch(View view)
    {
        List<Address> addressList = null;
        EditText location_tf = (EditText)findViewById( R.id.TFaddress);

        String location = location_tf.getText().toString();
        mMap.clear();
        if(location !=null|| !location.equals("") )
        {
            Geocoder geocoder = new Geocoder(this);
            try {
                 addressList=geocoder.getFromLocationName(location,1);
            } catch (IOException e) {
                e.printStackTrace();
            }

           /* if(addressList==null && addressList.size()<0)
            {
                Toast.makeText(MapsActivity.this, "invalid location!",
                        Toast.LENGTH_LONG).show();
            }*/
            Address address =addressList.get(0);

            LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
        //    MarkerOptions marker = new MarkerOptions().position(new LatLng(loc.latitude, loc.longitude)).title("Hello Maps");
           // marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.my_marker_icon));
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /*private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("MyCurrentloctionaddress", strReturnedAddress.toString());
            } else {
                Log.w("MyCurrentloctionaddress", "No Address!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("MyCurrentloctionaddress", "CanontgetAdress!");
        }
        return strAdd;
    }*/
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //   LatLng sydney = new LatLng(0, 0);
        //   mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //   mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setTrafficEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            //@Override
            public void onMapClick(LatLng point) {
               // String joinString1=point.latitude+","+point.longitude;
                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(point.latitude, point.longitude)).title(point.latitude+", "+point.longitude);
                mMap.clear();
                mMap.addMarker(marker);
                loc=point;
                // EditText location_tf = findViewById( R.id.TFaddress);
                JsonObjectRequest request = new JsonObjectRequest("https://maps.googleapis.com/maps/api/geocode/json?latlng=13.024,77.619&key=AIzaSyCLCFnTQx10m2nMhL1mD-KQXCVfEifJBX0", new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String address= response.getJSONArray("results").getJSONObject(0).getString("formatted_address");
                            EditText location_tf = findViewById( R.id.TFaddress);
                             location_tf.setText(address);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                req.add(request);



                // System.out.println(point.latitude+"---"+ point.longitude);
            }


        });
      //  double latitude= (double)loc.latitude;
      // String final_address=getCompleteAddressString((double)loc.latitude,(double)loc.longitude);



    }

}
