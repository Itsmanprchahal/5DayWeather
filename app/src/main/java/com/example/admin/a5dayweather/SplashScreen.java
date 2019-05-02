package com.example.admin.a5dayweather;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.felipecsl.gifimageview.library.GifImageView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SplashScreen extends AppCompatActivity {
    String DayDate, Temperature, Descreiption, Max_Temp, Min_Temp, Wind_Speed, Degree, City, Country;
    pl.droidsonroids.gif.GifImageView imageView;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imageView = findViewById(R.id.gifsplash);
        //CurrentLocation();

        queue = Volley.newRequestQueue(SplashScreen.this);

        Thread thread = new Thread()
        {
            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(5000);
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

    }

        public void parseJSON(final String lat, final String lon) {
            String url = "https://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&appid=5f4f71c288ab1d0a242ffad7c8a76a52&units=Metric";

            final Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk");
            final int time = Integer.parseInt(simpleDateFormat.format(calendar.getTimeInMillis()));


            final StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray array1 = jsonObject.getJSONArray("list");

                        JSONObject loc = jsonObject.getJSONObject("city");
                        //JSONObject date = jsonObject.getJSONObject("dt_txt");


                        City = loc.optString("name");
                        Country = loc.optString("country");


                        Log.d("City", "City " + City);
                        Log.d("Country", "Country " + Country);




                        //Current
                        for (int i = 0; i <= array1.length(); i++) {
                            JSONObject mainobj = array1.getJSONObject(0);
                            JSONObject main = mainobj.getJSONObject("main");

                            Temperature = main.optString("temp");
                            Min_Temp = main.optString("temp_min");
                            Max_Temp = main.optString("temp_max");


                            final JSONObject object = array1.getJSONObject(0);
                            JSONArray array2 = object.getJSONArray("weather");
                            for (int i1 = 0; i1 <= array2.length(); i1++) {
                                JSONObject object1 = array2.getJSONObject(0);
                                Descreiption = object1.optString("main");
                                // Log.d("12345", "" + Descreiption);


                                if (time >= 7 && time < 18) {
                                    if (object1.has("main")) {
                                        Descreiption = object1.optString("main");
                                        if (Descreiption.equals("Rain")) {

                                            imageView.setImageResource(R.drawable.gifyrain);

                                        } else if (Descreiption.equals("Clear")) {
                                            imageView.setImageResource(R.drawable.gifyrain);


                                        } else if (Descreiption.equals("Clouds")) {
                                            imageView.setImageResource(R.drawable.clouds);

                                        } else if (Descreiption.equals("Snow")) {
                                            imageView.setImageResource(R.drawable.rainsnow);
                                        }
                                    }

                                } else {
                                    if (time >= 18) {
                                        if (object1.has("main")) {
                                            if (Descreiption.equals("Rain")) {
                                                imageView.setImageResource(R.drawable.rain);


                                            } else if (Descreiption.equals("Clear")) {
                                                imageView.setImageResource(R.drawable.clear);

                                            } else if (Descreiption.equals("Clouds")) {
                                                imageView.setImageResource(R.drawable.partlycloudy1);

                                            } else if (Descreiption.equals("Snow")) {
                                                imageView.setImageResource(R.drawable.rainsnow);

                                            }
                                        }
                                    }
                                }
                            }


                            final JSONObject object2 = mainobj.getJSONObject("wind");
                            Wind_Speed = object2.optString("speed");
                            Degree = object2.optString("deg");


                            DayDate = mainobj.getString("dt_txt");
                            Log.d("date", "" + DayDate.substring(1, 3));

                            final String date = DayDate.substring(0, 10);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SplashScreen.this, "Some Error Occured....", Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(request);
        }

    private void CurrentLocation() {
        final FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(SplashScreen.this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location Not Found...", Toast.LENGTH_SHORT).show();
            return;
        }

        Task<Location> locationTask = client.getLastLocation();
        if (locationTask != null) {
            locationTask.addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {

                    task.addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            parseJSON(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
                        }
                    });

                    task.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SplashScreen.this, "Location Not Found...,Enter Location Manually...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

        }
    }


}
