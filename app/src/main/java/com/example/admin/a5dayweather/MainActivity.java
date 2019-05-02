package com.example.admin.a5dayweather;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.LongDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.felipecsl.gifimageview.library.GifImageView;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mDayDate, mTemperature, mDescription, mMax_Temp, mMin_Temp, mWind_Speed, mDegree, mCity, mCountry;
    String DayDate, Temperature, Descreiption, Max_Temp, Min_Temp, Wind_Speed, Degree, City, Country;
    RequestQueue queue;
    ProgressDialog dialog;
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    ImageView msearch, myloc, imageView1;
    GifImageView imageView;
    ListView listView;
    public int j1;
    TextView mDate, mTempe, mDesc;
    ImageView getImageView1;
    ConstraintLayout layout, layout2;
    String latt, lonn;
    LocationManager locationManager;
    boolean GpsStatus;
    private GoogleApiClient googleApiClient;
    public static final int MY_PERMISSIONS_REQUEST_READ_LOCATION = 121;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDayDate = findViewById(R.id.daydate);
        mTemperature = findViewById(R.id.temperature);
        mDescription = findViewById(R.id.description);
        mMax_Temp = findViewById(R.id.maxtemp);
        mMin_Temp = findViewById(R.id.mintemp);
        mWind_Speed = findViewById(R.id.windspeed);
        mDegree = findViewById(R.id.winddegreetext);

        mCity = findViewById(R.id.location);
        // mCountry = findViewById(R.id.country);
        msearch = findViewById(R.id.search);
        myloc = findViewById(R.id.myloc);
        imageView = findViewById(R.id.weatherimage);
        imageView1 = findViewById(R.id.image1);
        listView = findViewById(R.id.listview);

        mDate = findViewById(R.id.date);
        mTempe = findViewById(R.id.temp);
        mDesc = findViewById(R.id.descri);
        getImageView1 = findViewById(R.id.image);
        layout = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);


//        Calendar calendar = Calendar.getInstance();
//        // calendar.add(Calendar.DATE,1);
//        DateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy \n \n " + "       hh:mm");
//        DayDate = sdf.format(calendar.getTime());
//        mDayDate.setText(DayDate);



        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_READ_LOCATION);

            } else {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_READ_LOCATION);

            }
        } else {
           CurrentLocation();
        }


        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait...");
        dialog.show();
        queue = Volley.newRequestQueue(MainActivity.this);

        Log.d("Citi", "Ciry :" + City);
        CurrentLocation();

        myloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                CurrentLocation();
            }
        });
        msearch.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (msearch == v) {
            if (isNetworkIsConnected()) {
                FireSearchIntent();
            } else {
                Toast.makeText(this, "Check Internet Connection...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean isNetworkIsConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }

    public void FireSearchIntent() {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            Toast.makeText(this, "Error :" + e, Toast.LENGTH_SHORT).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(this, "Error :" + e, Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                // mCity.setText(place.getAddress());
                LatLng latLng = place.getLatLng();
                if (isNetworkIsConnected()) {
                    parseJSON(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude));
                    dialog.show();
                } else {
                    Toast.makeText(this, "Check Internet COnnection", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
            Status status = PlaceAutocomplete.getStatus(this, data);
            Toast.makeText(this, "Error " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        } else if (resultCode == RESULT_CANCELED) {
            //This method cancel the operation
        }
    }


    public void parseJSON(final String lat, final String lon) {
        String url = "https://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&appid=966011a06d5d28592231659fafca3ac1&units=Metric";

        Log.d("lat2", lat);
        Log.d("lon2", lon);
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

                    JSONObject latlon = loc.optJSONObject("coord");

                    City = loc.optString("name");
                    Country = loc.optString("country");


                    Log.d("City", "City " + City);
                    Log.d("Country", "Country " + Country);

                    // mCity.setText(City);

                    latt = latlon.getString("lat");
                    lonn = latlon.getString("lon");

                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                    try {
                        List<Address> address = geocoder.getFromLocation(Double.valueOf(latt), Double.valueOf(lonn), 1);
                        mCity.setText(address.get(0).getLocality() + ", " + address.get(0).getAdminArea() + " , " + address.get(0).getCountryName());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // mCountry.setText(Country);


                    //Current
                    for (int i = 0; i <= array1.length(); i++) {
                        JSONObject mainobj = array1.getJSONObject(0);
                        JSONObject main = mainobj.getJSONObject("main");

                        Temperature = main.optString("temp");
                        Min_Temp = main.optString("temp_min");
                        Max_Temp = main.optString("temp_max");


                        mTemperature.setText(Temperature);

                        mMin_Temp.setText(Min_Temp);
                        mMax_Temp.setText(Max_Temp);


                        final JSONObject object = array1.getJSONObject(0);
                        JSONArray array2 = object.getJSONArray("weather");
                        for (int i1 = 0; i1 <= array2.length(); i1++) {
                            JSONObject object1 = array2.getJSONObject(0);
                            Descreiption = object1.optString("main");
                            // Log.d("12345", "" + Descreiption);
                            mDescription.setText(Descreiption);


                            if (time >= 7 && time < 18) {
                                if (object1.has("main")) {
                                    Descreiption = object1.optString("main");
                                    if (Descreiption.equals("Rain")) {

                                        imageView.setImageResource(R.drawable.rain);
                                        getImageView1.setImageResource(R.drawable.rain);


                                    } else if (Descreiption.equals("Clear")) {
                                        imageView.setImageResource(R.drawable.sunny);
                                        getImageView1.setImageResource(R.drawable.sunny);


                                    } else if (Descreiption.equals("Clouds")) {
                                        imageView.setImageResource(R.drawable.clouds);
                                        getImageView1.setImageResource(R.drawable.clouds);
                                    } else if (Descreiption.equals("Snow")) {
                                        imageView.setImageResource(R.drawable.rainsnow);
                                        getImageView1.setImageResource(R.drawable.rainsnow);
                                    }
                                }

                            } else {
                                if (time >= 18) {
                                    if (object1.has("main")) {
                                        if (Descreiption.equals("Rain")) {
                                            imageView.setImageResource(R.drawable.rain);
                                            getImageView1.setImageResource(R.drawable.rain);

                                        } else if (Descreiption.equals("Clear")) {
                                            imageView.setImageResource(R.drawable.clear);
                                            getImageView1.setImageResource(R.drawable.clear);
                                        } else if (Descreiption.equals("Clouds")) {
                                            imageView.setImageResource(R.drawable.partlycloudy1);
                                            getImageView1.setImageResource(R.drawable.partlycloudy1);
                                        } else if (Descreiption.equals("Snow")) {
                                            imageView.setImageResource(R.drawable.rainsnow);
                                            getImageView1.setImageResource(R.drawable.rainsnow);
                                        }
                                    }
                                }
                            }
                        }


                        final JSONObject object2 = mainobj.getJSONObject("wind");
                        Wind_Speed = object2.optString("speed");
                        Degree = object2.optString("deg");

                        mWind_Speed.setText(Wind_Speed);
                        mDegree.setText(Degree);

                        DayDate = mainobj.getString("dt_txt");
                        Log.d("date", "" + DayDate.substring(1, 3));

                        final String date = DayDate.substring(0, 10);
                        mDayDate.setText(date);

                        mTempe.setText(Temperature);
                        mDate.setText(date);
                        mDesc.setText(Descreiption);

                        layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mTemperature.setText(Temperature);

                                mMin_Temp.setText(Min_Temp);
                                mMax_Temp.setText(Max_Temp);
                                mDescription.setText(Descreiption);
                                Wind_Speed = object2.optString("speed");
                                Degree = object2.optString("deg");
                                mDayDate.setText(date);
                                layout.setVisibility(View.GONE);

                                for (int j = 0; j < listView.getChildCount(); j++)
                                    listView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);

                                // change the background color of the selected element
                                listView.setBackgroundColor(Color.TRANSPARENT);

                                if (time >= 7 && time < 18) {

                                    if (Descreiption.equals("Rain")) {

                                        imageView.setImageResource(R.drawable.rain);
                                        getImageView1.setImageResource(R.drawable.rain);

                                    } else if (Descreiption.equals("Clear")) {
                                        imageView.setImageResource(R.drawable.sunny);
                                        getImageView1.setImageResource(R.drawable.sunny);
                                        //imageView.setBackgroundResource(R.drawable.sunny2);

                                    } else if (Descreiption.equals("Clouds")) {
                                        imageView.setImageResource(R.drawable.clouds);
                                        getImageView1.setImageResource(R.drawable.clouds);
                                    } else if (Descreiption.equals("Snow")) {
                                        imageView.setImageResource(R.drawable.rainsnow);
                                        getImageView1.setImageResource(R.drawable.rainsnow);
                                    }
                                } else

                                {
                                    if (time >= 18) {

                                        if (Descreiption.equals("Rain")) {
                                            imageView.setImageResource(R.drawable.rain);
                                            getImageView1.setImageResource(R.drawable.rain);

                                        } else if (Descreiption.equals("Clear")) {
                                            imageView.setImageResource(R.drawable.clear);
                                            getImageView1.setImageResource(R.drawable.clear);
                                        } else if (Descreiption.equals("Clouds")) {
                                            imageView.setImageResource(R.drawable.partlycloudy1);
                                            getImageView1.setImageResource(R.drawable.partlycloudy1);
                                        } else if (Descreiption.equals("Snow")) {
                                            imageView.setImageResource(R.drawable.rainsnow);
                                            getImageView1.setImageResource(R.drawable.rainsnow);
                                        }
                                    }
                                }


                            }

                        });


                    }


                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

                try {
                    final JSONObject jsonObject = new JSONObject(response);
                    final JSONArray array1 = jsonObject.getJSONArray("list");
                    final ArrayList<String> arrayList = new ArrayList<String>();
                    final ArrayList<String> date = new ArrayList<String>();
                    final ArrayList<String> decrip = new ArrayList<String>();
                    final ArrayList<String> minTemp = new ArrayList<String>();
                    final ArrayList<String> MaxTemp = new ArrayList<String>();
                    final ArrayList<String> wind = new ArrayList<String>();
                    final ArrayList<String> deg = new ArrayList<String>();


                    //day 2
                    for (j1 = 8; j1 <= array1.length(); j1++) {
                        JSONObject mainobj = array1.getJSONObject(j1);
                        final JSONObject main = mainobj.getJSONObject("main");

                        final String Tempo = main.optString("temp");
                        final String min_Temp = main.optString("temp_min");
                        final String max_Temp = main.optString("temp_max");
                        //Log.d("min_Temp",""+min_Temp);
                        // Log.d("<<<", "<<<<" + Tempo);


                        arrayList.add(Tempo);
                        minTemp.add(min_Temp);
                        MaxTemp.add(max_Temp);
                        // Log.d("array", "   " + arrayList);


                        JSONObject jsonobject = array1.getJSONObject(j1);
                        JSONArray array3 = jsonobject.getJSONArray("weather");

                        for (int i1 = 1; i1 <= array3.length(); i1++) {
                            JSONObject main1 = array3.getJSONObject(0);
                            String description = main1.optString("main");
                            Log.d("12345", "" + description);
                            // mDescription.setText(Descreiption);

                            decrip.add(description);

                        }


                        JSONObject object2 = mainobj.getJSONObject("wind");
                        final String WindSpeed = object2.optString("speed");
                        final String Deg = object2.optString("deg");

                        wind.add(WindSpeed);
                        deg.add(Deg);
                        //Log.d("Wind",""+WindSpeed);


                        final String DayDate = mainobj.getString("dt_txt");
                        date.add(DayDate.substring(0, 10));


//                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,arrayList);
//                        listView.setAdapter(adapter);

                        final Adapter adapter = new Adapter(getApplicationContext(), arrayList, date, decrip, MaxTemp, minTemp, wind, deg);

                        listView.setAdapter(adapter);


                        Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                        listView.startAnimation(slide_down);


                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                                //Toast.makeText(MainActivity.this, "Position " + position, Toast.LENGTH_SHORT).show();
                                layout.setVisibility(View.VISIBLE);


                                //view.setBackgroundColor(Color.GRAY);
                                for (int j = 0; j < parent.getChildCount(); j++)
                                    parent.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);

                                // change the background color of the selected element
                                view.setBackgroundColor(getResources().getColor(R.color.defaut));


                                String tem = arrayList.get(position);
                                String date1 = date.get(position);
                                String des = decrip.get(position);
                                String Max_Temp = MaxTemp.get(position);
                                String min_Temp = minTemp.get(position);
                                String Wind = wind.get(position);
                                String degr = deg.get(position);


                                mTemperature.setText(tem);
                                mMax_Temp.setText(Max_Temp);
                                mMin_Temp.setText(min_Temp);
                                mDescription.setText(des);
                                mDayDate.setText(date1);
                                mWind_Speed.setText(Wind);
                                mDegree.setText(degr);


                                if (time >= 7 && time < 18) {

                                    if (des.equals("Rain")) {

                                        imageView.setImageResource(R.drawable.rain);

                                    } else if (des.equals("Clear")) {
                                        imageView.setImageResource(R.drawable.sunny);
                                        //imageView.setBackgroundResource(R.drawable.sunny2);

                                    } else if (des.equals("Clouds")) {
                                        imageView.setImageResource(R.drawable.clouds);
                                    } else if (des.equals("Snow")) {
                                        imageView.setImageResource(R.drawable.rainsnow);
                                    }


                                } else {

                                    if (des.equals("Rain")) {
                                        imageView.setImageResource(R.drawable.rain);

                                    } else if (des.equals("Clear")) {
                                        imageView.setImageResource(R.drawable.clear);
                                    } else if (des.equals("Clouds")) {
                                        imageView.setImageResource(R.drawable.partlycloudy1);
                                    } else if (des.equals("Snow")) {
                                        imageView.setImageResource(R.drawable.rainsnow);
                                    }

                                }


                            }
                        });
                        j1 = j1 + 7;

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Some Error Occured....", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }


    private void CurrentLocation() {
        final FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location Not Found...", Toast.LENGTH_SHORT).show();
            return;
        } else {

            Task<Location> locationTask = client.getLastLocation();
            if (locationTask != null) {
                locationTask.addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {

                        task.addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                parseJSON(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));

                                String lat1 = String.valueOf(location.getLatitude());
                                String lon1 = String.valueOf(location.getLongitude());

                                Log.d("lat1", lat1 + " " + lon1);

                            }
                        });

                        task.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Location Not Found...,Enter Location Manually...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                        CurrentLocation();
                    }
                } else {
                    Toast.makeText(this, "No permission Granted", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }

    }

}
