//package com.example.admin.a5dayweather;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.net.ConnectivityManager;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.constraint.ConstraintLayout;
//import android.support.design.widget.Snackbar;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.felipecsl.gifimageview.library.GifImageView;
//import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
//import com.google.android.gms.common.GooglePlayServicesRepairableException;
//import com.google.android.gms.common.api.Status;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.places.Place;
//import com.google.android.gms.location.places.ui.PlaceAutocomplete;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//
//public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//
//    TextView mDayDate, mTemperature, mDescription, mMax_Temp, mMin_Temp, mWind_Speed, mDegree, mCity, mCountry;
//    String DayDate, Temperature, Descreiption, Max_Temp, Min_Temp, Wind_Speed, Degree, City, Country;
//    RequestQueue queue;
//    ProgressDialog dialog;
//    ConstraintLayout layout1,layout2,layout3,layout4;
//    RecyclerView mRecycler;
//    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
//    ImageView msearch, myloc, imageView1, imageView2, imageView3, imageView4;
//    GifImageView imageView;
//    TextView mTemperature1, mDescription1, mDayDate1;
//    String Temperature1, DayDate1, Description1;
//
//    TextView mTemperature2, mDescription2, mDayDate2;
//    String Temperature2, DayDate2, Description2;
//
//    TextView mTemperature3, mDescription3, mDayDate3;
//    String Temperature3, DayDate3, Description3;
//
//    TextView mTemperature4, mDescription4, mDayDate4;
//    String Temperature4, DayDate4, Description4;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        mDayDate = findViewById(R.id.daydate);
//        mTemperature = findViewById(R.id.temperature);
//        mDescription = findViewById(R.id.description);
//        mMax_Temp = findViewById(R.id.maxtemp);
//        mMin_Temp = findViewById(R.id.mintemp);
//        mWind_Speed = findViewById(R.id.windspeed);
//        mDegree = findViewById(R.id.winddegreetext);
//        layout1 = findViewById(R.id.layout1);
//        layout2 = findViewById(R.id.layout2);
//        layout3 = findViewById(R.id.layout3);
//        layout4 = findViewById(R.id.layout4);
//        mCity = findViewById(R.id.location);
//        mCountry = findViewById(R.id.country);
//        msearch = findViewById(R.id.search);
//        myloc = findViewById(R.id.myloc);
//        imageView = findViewById(R.id.weatherimage);
//        imageView1 = findViewById(R.id.image1);
//        imageView2 = findViewById(R.id.image2);
//        imageView3 = findViewById(R.id.image3);
//        imageView4 = findViewById(R.id.image4);
//
//
//        mDayDate1 = findViewById(R.id.date1);
//        mDescription1 = findViewById(R.id.descri1);
//        mTemperature1 = findViewById(R.id.temp1);
//
//        mDayDate2 = findViewById(R.id.date2);
//        mDescription2 = findViewById(R.id.descri2);
//        mTemperature2 = findViewById(R.id.temp2);
//
//        mDayDate3 = findViewById(R.id.date3);
//        mDescription3 = findViewById(R.id.descri3);
//        mTemperature3 = findViewById(R.id.temp3);
//
//        mDayDate4 = findViewById(R.id.date4);
//        mDescription4 = findViewById(R.id.descri4);
//        mTemperature4 = findViewById(R.id.temp4);
//
//        layout1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Day 2", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        layout2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Day 3", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        layout3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Day 4", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        layout4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Day 5", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
////        mRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
////        mRecycler.setAdapter(new RecyclerAdapter(Image,Time,Temp,Max_temp,Min_temp));
//
//        Calendar calendar = Calendar.getInstance();
//        // calendar.add(Calendar.DATE,1);
//        DateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy \n \n " + "       hh:mm");
//        DayDate = sdf.format(calendar.getTime());
//        mDayDate.setText(DayDate);
//        dialog = new ProgressDialog(this);
//        dialog.setMessage("Please Wait...");
//        dialog.show();
//        queue = Volley.newRequestQueue(MainActivity.this);
//
//
//        CurrentLocation();
//
//        myloc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.show();
//                CurrentLocation();
//            }
//        });
//        msearch.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (msearch == v) {
//            if (isNetworkIsConnected()) {
//                FireSearchIntent();
//            } else {
//                Toast.makeText(this, "Check Internet Connection...", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    public boolean isNetworkIsConnected() {
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        return connectivityManager.getActiveNetworkInfo() != null;
//    }
//
//    public void FireSearchIntent() {
//        try {
//            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(this);
//            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
//        } catch (GooglePlayServicesRepairableException e) {
//            Toast.makeText(this, "Error :" + e, Toast.LENGTH_SHORT).show();
//        } catch (GooglePlayServicesNotAvailableException e) {
//            Toast.makeText(this, "Error :" + e, Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                Place place = PlaceAutocomplete.getPlace(this, data);
//                // mCity.setText(place.getAddress());
//                LatLng latLng = place.getLatLng();
//                if (isNetworkIsConnected()) {
//                    parseJSON(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude));
//                    dialog.show();
//                } else {
//                    Toast.makeText(this, "Check Internet COnnection", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                }
//            }
//        } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
//            Status status = PlaceAutocomplete.getStatus(this, data);
//            Toast.makeText(this, "Error " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
//        } else if (resultCode == RESULT_CANCELED) {
//            //This method cancel the operation
//        }
//    }
//
//
//    public void parseJSON(final String lat, final String lon) {
//        String url = "https://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&appid=5f4f71c288ab1d0a242ffad7c8a76a52&units=Metric";
//
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk");
//        final int time = Integer.parseInt(simpleDateFormat.format(calendar.getTimeInMillis()));
//
//
//        final StringRequest request = new StringRequest(url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray array1 = jsonObject.getJSONArray("list");
//
//                    JSONObject loc = jsonObject.getJSONObject("city");
//                    City = loc.optString("name");
//                    Country = loc.optString("country");
//
//
//                    Log.d("City", "City " + City);
//                    Log.d("Country", "Country " + Country);
//
//                    mCity.setText(City);
//                    mCountry.setText(Country);
//
//                    //Current
//                    for (int i = 0; i <= array1.length(); i++) {
//                        JSONObject mainobj = array1.getJSONObject(0);
//                        JSONObject main = mainobj.getJSONObject("main");
//
//                        Temperature = main.optString("temp");
//                        Min_Temp = main.optString("temp_min");
//                        Max_Temp = main.optString("temp_max");
//
//
//                        mTemperature.setText(Temperature);
//
//                        mMin_Temp.setText(Min_Temp);
//                        mMax_Temp.setText(Max_Temp);
//
//
//                        JSONObject object = array1.getJSONObject(0);
//                        JSONArray array2 = object.getJSONArray("weather");
//                        for (int i1 = 0; i1 <= array2.length(); i1++) {
//                            JSONObject object1 = array2.getJSONObject(0);
//                            Descreiption = object1.optString("main");
//                            Log.d("12345", "" + Descreiption);
//                            mDescription.setText(Descreiption);
//
//                            if (time >= 7 && time < 18) {
//                                if (object1.has("main")) {
//                                    Descreiption = object1.optString("main");
//                                    if (Descreiption.equals("Rain")) {
//
//                                        imageView.setImageResource(R.drawable.rain);
//
//                                    } else if (Descreiption.equals("Clear")) {
//                                        imageView.setImageResource(R.drawable.sunny);
//                                        //imageView.setBackgroundResource(R.drawable.sunny2);
//
//                                    } else if (Descreiption.equals("Clouds")) {
//                                        imageView.setImageResource(R.drawable.clouds);
//                                    } else if (Descreiption.equals("Snow"))
//                                    {
//                                        imageView.setImageResource(R.drawable.rainsnow);
//                                    }
//                                }
//
//                            } else {
//                                if (time > 18) {
//                                    if (object.has("main")) {
//                                        if (Descreiption.equals("Rain")) {
//                                            imageView.setImageResource(R.drawable.rain);
//
//                                        } else if (Descreiption.equals("Clear")) {
//                                            imageView.setImageResource(R.drawable.clear);
//                                        } else if (Descreiption.equals("Clouds")) {
//                                            imageView.setImageResource(R.drawable.partlycloudy1);
//                                        } else if (Descreiption.equals("Snow"))
//                                        {
//                                            imageView.setImageResource(R.drawable.rainsnow);
//                                        }
//                                    }
//                                }
//                            }
//
//                        }
//                        JSONObject object2 = mainobj.getJSONObject("wind");
//                        Wind_Speed = object2.optString("speed");
//                        Degree = object2.optString("deg");
//
//                        mWind_Speed.setText(Wind_Speed);
//                        mDegree.setText(Degree);
//                    }
//
//                    //day 2
//                    for (int i = 0; i <= array1.length(); i++) {
//                        JSONObject mainObj = array1.getJSONObject(9);
//                        JSONObject main = mainObj.getJSONObject("main");
//                        Temperature1 = main.optString("temp");
////                        Min_Temp = main.optString("temp_min");
////                        Max_Temp = main.optString("temp_max");
//
//                        mTemperature1.setText(Temperature1);
//
//                        // mMin_Temp.setText(Min_Temp);
//                        // mMax_Temp.setText(Max_Temp);
//
//                        JSONObject object1 = array1.getJSONObject(9);
//                        JSONArray array = object1.getJSONArray("weather");
//                        for (int i1 = 0; i1 <= array.length(); i1++) {
//                            JSONObject object = array.getJSONObject(0);
//                            Description1 = object.optString("main");
//                            mDescription1.setText(Description1);
//
//                            if (time >= 7 && time < 18) {
//                                if (object.has("main")) {
//                                    if (Description1.equals("Rain")) {
//                                        imageView1.setImageResource(R.drawable.rain);
//                                    } else if (Description1.equals("Clear")) {
//                                        imageView1.setImageResource(R.drawable.sunny);
//                                    } else if (Description1.equals("Clouds")) {
//                                        imageView1.setImageResource(R.drawable.clouds);
//                                    } else if (Description1.equals("Snow"))
//                                    {
//                                        imageView1.setImageResource(R.drawable.rainsnow);
//                                    }
//                                }
//                            } else {
//                                if (time > 18) {
//                                    if (object.has("main")) {
//                                        if (Description1.equals("Rain")) {
//                                            imageView1.setImageResource(R.drawable.rain);
//
//                                        } else if (Description1.equals("Clear")) {
//                                            imageView1.setImageResource(R.drawable.clear);
//                                        } else if (Description1.equals("Clouds")) {
//                                            imageView1.setImageResource(R.drawable.partlycloudy1);
//                                        }
//                                    }
//                                }
//                            }
//
//                        }
//
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.add(Calendar.DATE, 1);
//                        DateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy ");
//                        DayDate1 = sdf.format(calendar.getTime());
//                        mDayDate1.setText(DayDate1);
//
//
//                    }
//
//                    //day3
//                    for (int i = 0; i <= array1.length(); i++) {
//                        JSONObject mainObj = array1.getJSONObject(17);
//                        JSONObject main = mainObj.getJSONObject("main");
//                        Temperature2 = main.optString("temp");
//
//                        mTemperature2.setText(Temperature2);
//
//                        JSONObject object1 = array1.getJSONObject(17);
//                        JSONArray array = object1.getJSONArray("weather");
//                        for (int i1 = 0; i1 <= array.length(); i1++) {
//                            JSONObject object = array.getJSONObject(0);
//                            Description2 = object.optString("main");
//                            mDescription2.setText(Description2);
//
//                            if (time >= 7 && time < 18) {
//                                if (object.has("main")) {
//                                    if (Description2.equals("Rain")) {
//                                        imageView2.setImageResource(R.drawable.rain);
//                                    } else if (Description2.equals("Clear")) {
//                                        imageView2.setImageResource(R.drawable.sunny);
//                                    } else if (Description2.equals("Clouds")) {
//                                        imageView2.setImageResource(R.drawable.clouds);
//                                    } else if (Description2.equals("Snow"))
//                                    {
//                                        imageView2.setImageResource(R.drawable.rainsnow);
//                                    }
//                                }
//                            } else {
//                                if (time > 18) {
//                                    if (object.has("main")) {
//                                        if (Description2.equals("Rain")) {
//                                            imageView2.setImageResource(R.drawable.rain);
//
//                                        } else if (Description2.equals("Clear")) {
//                                            imageView2.setImageResource(R.drawable.clear);
//                                        } else if (Description2.equals("Clouds")) {
//                                            imageView2.setImageResource(R.drawable.partlycloudy1);
//                                        } else if (Description2.equals("Snow"))
//                                        {
//                                            imageView2.setImageResource(R.drawable.rainsnow);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.add(Calendar.DATE, 2);
//                        DateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
//                        DayDate2 = sdf.format(calendar.getTime());
//                        mDayDate2.setText(DayDate2);
//                    }
//
//                    //day4
//                    for (int i = 0; i <= array1.length(); i++) {
//                        JSONObject mainObj = array1.getJSONObject(24);
//                        JSONObject main = mainObj.getJSONObject("main");
//                        Temperature3 = main.optString("temp");
//
//                        mTemperature3.setText(Temperature3);
//
//                        JSONObject object1 = array1.getJSONObject(24);
//                        JSONArray array = object1.getJSONArray("weather");
//                        for (int i1 = 0; i1 <= array.length(); i1++) {
//                            JSONObject object = array.getJSONObject(0);
//                            Description3 = object.optString("main");
//                            mDescription3.setText(Description3);
//
//                            if (time >= 7 && time < 18) {
//                                if (object.has("main")) {
//                                    if (Description3.equals("Rain")) {
//                                        imageView3.setImageResource(R.drawable.rain);
//                                    } else if (Description3.equals("Clear")) {
//                                        imageView3.setImageResource(R.drawable.sunny);
//                                    } else if (Description3.equals("Clouds")) {
//                                        imageView3.setImageResource(R.drawable.clouds);
//                                    } else if (Description3.equals("Snow"))
//                                    {
//                                        imageView3.setImageResource(R.drawable.rainsnow);
//                                    }
//                                }
//                            } else {
//                                if (time > 18) {
//                                    if (object.has("main")) {
//                                        if (Description3.equals("Rain")) {
//                                            imageView3.setImageResource(R.drawable.rain);
//
//                                        } else if (Description3.equals("Clear")) {
//                                            imageView3.setImageResource(R.drawable.clear);
//                                        } else if (Description3.equals("Clouds")) {
//                                            imageView3.setImageResource(R.drawable.partlycloudy1);
//                                        } else if (Description3.equals("Snow"))
//                                        {
//                                            imageView3.setImageResource(R.drawable.rainsnow);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.add(Calendar.DATE, 3);
//                        DateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
//                        DayDate3 = sdf.format(calendar.getTime());
//                        mDayDate3.setText(DayDate3);
//                    }
//
//                    //day5
//                    for (int i = 0; i <= array1.length(); i++) {
//                        JSONObject mainObj = array1.getJSONObject(32);
//                        JSONObject main = mainObj.getJSONObject("main");
//                        Temperature4 = main.optString("temp");
//
//                        mTemperature4.setText(Temperature4);
//
//                        JSONObject object1 = array1.getJSONObject(32);
//                        JSONArray array = object1.getJSONArray("weather");
//                        for (int i1 = 0; i1 <= array.length(); i1++) {
//                            JSONObject object = array.getJSONObject(0);
//                            Description4 = object.optString("main");
//                            mDescription4.setText(Description4);
//                            if (time >= 7 && time <= 18) {
//                                if (object.has("main")) {
//                                    if (Description4.equals("Rain")) {
//                                        imageView4.setImageResource(R.drawable.rain);
//                                    } else if (Description4.equals("Clear")) {
//                                        imageView4.setImageResource(R.drawable.sunny);
//                                    } else if (Description4.equals("Clouds")) {
//                                        imageView4.setImageResource(R.drawable.clouds);
//                                    }
//                                    else if (Description4.equals("Snow"))
//                                    {
//                                        imageView4.setImageResource(R.drawable.rainsnow);
//                                    }
//                                }
//                            } else {
//                                if (time > 18) {
//                                    if (object.has("main")) {
//                                        if (Description4.equals("Rain")) {
//                                            imageView4.setImageResource(R.drawable.rain);
//
//                                        } else if (Description4.equals("Clear")) {
//                                            imageView4.setImageResource(R.drawable.clear);
//                                        } else if (Description4.equals("Clouds")) {
//                                            imageView4.setImageResource(R.drawable.partlycloudy1);
//                                        } else if (Description4.equals("Snow"))
//                                        {
//                                            imageView4.setImageResource(R.drawable.rainsnow);
//                                        }
//                                    }
//                                }
//                            }
//
//                        }
//
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.add(Calendar.DATE, 4);
//                        DateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
//                        DayDate4 = sdf.format(calendar.getTime());
//                        mDayDate4.setText(DayDate4);
//                    }
//
//                } catch (JSONException e1) {
//                    e1.printStackTrace();
//                }
//                dialog.dismiss();
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                dialog.dismiss();
//                Toast.makeText(MainActivity.this, "Some Error Occured....", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        queue.add(request);
//    }
//
//
//    private void CurrentLocation() {
//        final FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(MainActivity.this);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(this, "Location Not Found...", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        Task<Location> locationTask = client.getLastLocation();
//        if (locationTask != null) {
//            locationTask.addOnCompleteListener(new OnCompleteListener<Location>() {
//                @Override
//                public void onComplete(@NonNull Task<Location> task) {
//
//                    task.addOnSuccessListener(new OnSuccessListener<Location>() {
//                        @Override
//                        public void onSuccess(Location location) {
//                            parseJSON(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
//                        }
//                    });
//
//                    task.addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(MainActivity.this, "Location Not Found...,Enter Location Manually...", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            });
//
//        }
//    }
//
//
//}
