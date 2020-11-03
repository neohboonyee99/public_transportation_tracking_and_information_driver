package com.example.public_transportation_driver_applicaiton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.public_transportation_driver_applicaiton.Retrofit.RetrofitClient;
import com.example.public_transportation_driver_applicaiton.Retrofit.ServerService;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    String routeNumber, currentBusStop,username, plateNumber, responseMsg, responseMsg2,responseMsg3, responseMsg4;
    ArrayList<String> busStop;
    SwitchCompat startStopSwitch, startBackSwitch;
    TextView getRouteNumberTV, getCurrentBusStopTV;
    Boolean journey = false;
    ImageButton logoutBtn;
    Button nextStopBtn;
    double latitude, longitude;
    ServerService serverService;
    int counter=0;

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

        startStopSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                updateLocation();
                startBackSwitch.setClickable(false);
                logoutBtn.setClickable(false);
                nextStopBtn.setClickable(true);

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
                                            currentBusStop = route.get("start_stop").getAsString();
                                            busStop.add(route.get("1st_stop_go").getAsString());
                                            busStop.add(route.get("2nd_stop_go").getAsString());
                                            busStop.add(route.get("3rd_stop_go").getAsString());
                                            busStop.add(route.get("4th_stop_go").getAsString());
                                            busStop.add(route.get("5th_stop_go").getAsString());
                                            busStop.add(route.get("6th_stop_go").getAsString());
                                            busStop.add(route.get("7th_stop_go").getAsString());
                                            busStop.add(route.get("8th_stop_go").getAsString());
                                            busStop.add(route.get("9th_stop_go").getAsString());
                                            busStop.add(route.get("10th_stop_go").getAsString());
                                            busStop.add(route.get("11th_stop_go").getAsString());
                                            busStop.add(route.get("12th_stop_go").getAsString());
                                            busStop.add(route.get("13th_stop_go").getAsString());
                                            busStop.add(route.get("14th_stop_go").getAsString());
                                            busStop.add(route.get("15th_stop_go").getAsString());
                                            busStop.add(route.get("16th_stop_go").getAsString());
                                            busStop.add(route.get("17th_stop_go").getAsString());
                                            busStop.add(route.get("18th_stop_go").getAsString());
                                            busStop.add(route.get("19th_stop_go").getAsString());
                                            busStop.add(route.get("20th_stop_go").getAsString());
                                            getCurrentBusStopTV.setText(currentBusStop);

                                        }
                                        else{
                                            currentBusStop = route.get("back_stop").getAsString();
                                            busStop.add(route.get("1st_stop_back").getAsString());
                                            busStop.add(route.get("2nd_stop_back").getAsString());
                                            busStop.add(route.get("3rd_stop_back").getAsString());
                                            busStop.add(route.get("4th_stop_back").getAsString());
                                            busStop.add(route.get("5th_stop_back").getAsString());
                                            busStop.add(route.get("6th_stop_back").getAsString());
                                            busStop.add(route.get("7th_stop_back").getAsString());
                                            busStop.add(route.get("8th_stop_back").getAsString());
                                            busStop.add(route.get("9th_stop_back").getAsString());
                                            busStop.add(route.get("10th_stop_back").getAsString());
                                            busStop.add(route.get("11th_stop_back").getAsString());
                                            busStop.add(route.get("12th_stop_back").getAsString());
                                            busStop.add(route.get("13th_stop_back").getAsString());
                                            busStop.add(route.get("14th_stop_back").getAsString());
                                            busStop.add(route.get("15th_stop_back").getAsString());
                                            busStop.add(route.get("16th_stop_back").getAsString());
                                            busStop.add(route.get("17th_stop_back").getAsString());
                                            busStop.add(route.get("18th_stop_back").getAsString());
                                            busStop.add(route.get("19th_stop_back").getAsString());
                                            busStop.add(route.get("20th_stop_back").getAsString());
                                            getCurrentBusStopTV.setText(currentBusStop);
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Toast.makeText(MainActivity.this,responseMsg2, Toast.LENGTH_SHORT).show();
                                }
                            }));

                        }


                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(MainActivity.this,responseMsg, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MainActivity.this,responseMsg3, Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        while(startStopSwitch.isChecked()){
            updateLocation();
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
                    Toast.makeText(MainActivity.this,responseMsg4, Toast.LENGTH_SHORT).show();

                }
            });
        }

        startBackSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                 journey = true;
            }
            else{
                journey = false;
            }
        });

    }

    public void updateLocation(){
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
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

        //check the network provider is enabled
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, location -> {
                //get latitude
                latitude = location.getLatitude();
                //get longitude
                longitude = location.getLongitude();
            });
        }

        else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, location -> {
                //get latitude
                latitude = location.getLatitude();
                //get longitude
                longitude = location.getLongitude();

            });
        }
    }

    public void logOutOnClicked(View view) {
        Toast.makeText(MainActivity.this,"Logging out driver.",Toast.LENGTH_SHORT).show();
        this.finish();
    }

    public void nextStopOnClicked(View view) {
        if(!busStop.get(counter).equals("NULL")){
            getCurrentBusStopTV.setText(busStop.get(counter));
            counter++;
        }
        else{
            counter =0;
            Toast.makeText(MainActivity.this,"No more next stop!", Toast.LENGTH_SHORT).show();
            nextStopBtn.setClickable(false);

        }

    }
}