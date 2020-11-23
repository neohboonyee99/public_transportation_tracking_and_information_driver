package com.example.public_transportation_driver_application;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.public_transportation_driver_application.R;
import com.example.public_transportation_driver_application.Retrofit.RetrofitClient;
import com.example.public_transportation_driver_application.Retrofit.ServerService;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements  LocationListener {

    String routeNumber, currentBusStop,nextStop, username, plateNumber, responseMsg, responseMsg2,responseMsg3, responseMsg4,responseMsg5;
    ArrayList<String> busStop;
    SwitchCompat startStopSwitch, startBackSwitch;
    TextView getRouteNumberTV, getCurrentBusStopTV;
    public static final int LOCATION_REQUEST = 1;
    int LOCATION_UPDATE_MIN_TIME = 1000;
    int LOCATION_UPDATE_MIN_DISTANCE = 10;
    Boolean journey = false;
    ImageButton logoutBtn;
    Button nextStopBtn;
    double latitude, longitude;
    ServerService serverService;
    int counter=0;
    Location cLocation;
    LocationManager mLocationManager;
    LocationListener mLocationListener;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = getIntent().getStringExtra("extra_username");
        startStopSwitch = findViewById(R.id.startStopSwitch);
        startBackSwitch = findViewById(R.id.startBackSwitch);
        getRouteNumberTV = findViewById(R.id.getRouteNumberTV);
        getCurrentBusStopTV = findViewById(R.id.getCurrentBusStopTV);
        logoutBtn = findViewById(R.id.logOutBtn);
        nextStopBtn = findViewById(R.id.nextStopBtn);
        serverService = RetrofitClient.getClient(ServerService.BASE_URL).create(ServerService.class);
        routeNumber = getIntent().getStringExtra("extra_route_number");
        plateNumber = getIntent().getStringExtra("extra_plate_number");
        busStop = new ArrayList<>();

        getRouteNumberTV.setText(routeNumber);
        nextStopBtn.setClickable(false);
        getCurrentBusStopTV.setText(R.string.stop_operating_text);
        updateLocation();

        startStopSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                updateLocation();
                startBackSwitch.setClickable(false);
                logoutBtn.setClickable(false);
                nextStopBtn.setClickable(true);
                nextStopBtn.setEnabled(true);

                Call<JsonObject> startOperation = serverService.createBus(routeNumber,plateNumber,latitude,longitude,journey);
                startOperation.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            JsonObject jsonObject = response.body();
                            responseMsg = jsonObject.get("msg").getAsString();
                            Toast.makeText(MainActivity.this,responseMsg, Toast.LENGTH_SHORT).show();
                            Call<JsonObject> getBusStop = serverService.getBusStop(routeNumber, journey);
                            getBusStop.enqueue((new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    if (response.isSuccessful()){
                                        JsonObject jsonObject = response.body();
                                        responseMsg2 = jsonObject.get("msg").getAsString();
                                        JsonObject route = jsonObject.get("bus_route").getAsJsonObject();
                                        if (journey){
                                            currentBusStop = route.get("start_stop").toString();
                                            busStop.add(route.get("1st_stop_go").toString());
                                            busStop.add(route.get("2nd_stop_go").toString());
                                            busStop.add(route.get("3rd_stop_go").toString());
                                            busStop.add(route.get("4th_stop_go").toString());
                                            busStop.add(route.get("5th_stop_go").toString());
                                            busStop.add(route.get("6th_stop_go").toString());
                                            busStop.add(route.get("7th_stop_go").toString());
                                            busStop.add(route.get("8th_stop_go").toString());
                                            busStop.add(route.get("9th_stop_go").toString());
                                            busStop.add(route.get("10th_stop_go").toString());
                                            busStop.add(route.get("11th_stop_go").toString());
                                            busStop.add(route.get("12th_stop_go").toString());
                                            busStop.add(route.get("13th_stop_go").toString());
                                            busStop.add(route.get("14th_stop_go").toString());
                                            busStop.add(route.get("15th_stop_go").toString());
                                            busStop.add(route.get("16th_stop_go").toString());
                                            busStop.add(route.get("17th_stop_go").toString());
                                            busStop.add(route.get("18th_stop_go").toString());
                                            busStop.add(route.get("19th_stop_go").toString());
                                            busStop.add(route.get("20th_stop_go").toString());
                                        }
                                        else{
                                            Log.e("TESTING", String.valueOf(route));
                                            currentBusStop = route.get("back_stop").toString();
                                            busStop.add(route.get("1st_stop_back").toString());
                                            busStop.add(route.get("2nd_stop_back").toString());
                                            busStop.add(route.get("3rd_stop_back").toString());
                                            busStop.add(route.get("4th_stop_back").toString());
                                            busStop.add(route.get("5th_stop_back").toString());
                                            busStop.add(route.get("6th_stop_back").toString());
                                            busStop.add(route.get("7th_stop_back").toString());
                                            busStop.add(route.get("8th_stop_back").toString());
                                            busStop.add(route.get("9th_stop_back").toString());
                                            busStop.add(route.get("10th_stop_back").toString());
                                            busStop.add(route.get("11th_stop_back").toString());
                                            busStop.add(route.get("12th_stop_back").toString());
                                            busStop.add(route.get("13th_stop_back").toString());
                                            busStop.add(route.get("14th_stop_back").toString());
                                            busStop.add(route.get("15th_stop_back").toString());
                                            busStop.add(route.get("16th_stop_back").toString());
                                            busStop.add(route.get("17th_stop_back").toString());
                                            busStop.add(route.get("18th_stop_back").toString());
                                            busStop.add(route.get("19th_stop_back").toString());
                                            busStop.add(route.get("20th_stop_back").toString());
                                        }
                                        getCurrentBusStopTV.setText(currentBusStop);
                                    }
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Toast.makeText(MainActivity.this,t.getMessage()+"testing", Toast.LENGTH_SHORT).show();
                                }
                            }));

                        }


                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(MainActivity.this,t.getMessage()+"testing2", Toast.LENGTH_SHORT).show();
                    }
                });



            }
            else{
                startBackSwitch.setClickable(true);
                logoutBtn.setClickable(true);
                nextStopBtn.setClickable(false);

                Call<JsonObject> stopOperation = serverService.deleteBus(plateNumber);
                stopOperation.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            JsonObject jsonObject = response.body();
                            responseMsg3 = jsonObject.get("msg").getAsString();
                            Toast.makeText(MainActivity.this,responseMsg3, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(MainActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
                getCurrentBusStopTV.setText(R.string.stop_operating_text);
            }
        });

        startBackSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                 journey = true;
            }
            else{
                journey = false;
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void updateLocation(){

        //Get Current Location
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat   .checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
        }
        else{

            if(!isGPSEnabled){
                Toast.makeText(MainActivity.this, "Please turn on the GPS on your device.",Toast.LENGTH_SHORT).show();
            }


        }

        mLocationListener = location -> {
            Log.e("Location", "Location Changed");
            cLocation = location;
            latitude = cLocation.getLatitude();
            longitude = cLocation.getLongitude();
            Call<JsonObject> updateLocation = serverService.updateBusLocation(plateNumber, latitude, longitude);
            updateLocation.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()){
                        JsonObject jsonObject = response.body();
                        responseMsg4 = jsonObject.get("msg").getAsString();
                        Toast.makeText(MainActivity.this,responseMsg4, Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(MainActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        };
       cLocation = mLocationManager.getLastKnownLocation(mLocationManager.GPS_PROVIDER);
       if(cLocation!=null){
           latitude = cLocation.getLatitude();
           longitude = cLocation.getLongitude();
       }

        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);

    }

    public void logOutOnClicked(View view) {
        Toast.makeText(MainActivity.this,"Logging out driver.",Toast.LENGTH_SHORT).show();
        this.finish();
    }

    public void nextStopOnClicked(View view) {
        if(!busStop.get(counter).equals("null")){

            currentBusStop = busStop.get(counter).replace("\"", "");;
            nextStop = busStop.get(counter+1).replace("\"", "");
            Toast.makeText(MainActivity.this,currentBusStop, Toast.LENGTH_SHORT).show();
            getCurrentBusStopTV.setText(currentBusStop);
            Call<JsonObject> updateBusStop = serverService.updateBusStop(plateNumber,currentBusStop, nextStop);
            updateBusStop.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.isSuccessful()){
                        JsonObject jsonObject = response.body();
                        responseMsg5 = jsonObject.get("msg").getAsString();
                        Toast.makeText(MainActivity.this,responseMsg5, Toast.LENGTH_SHORT).show();
                    }
                    else{

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(MainActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            counter++;
        }
        else{
            nextStopBtn.setClickable(false);
            nextStopBtn.setEnabled(false);
            counter =0;
            Toast.makeText(MainActivity.this,"No more next stop!", Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public void onLocationChanged(@NonNull Location l) {
        mLocationListener = location -> {
            Log.e("Location", "Location Changed");
            cLocation = l;
            latitude = cLocation.getLatitude();
            longitude = cLocation.getLongitude();
            Call<JsonObject> updateLocation = serverService.updateBusLocation(plateNumber, latitude, longitude);
            updateLocation.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()){
                        JsonObject jsonObject = response.body();
                        responseMsg4 = jsonObject.get("msg").getAsString();
                        Toast.makeText(MainActivity.this,responseMsg4, Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(MainActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        };
    }
}