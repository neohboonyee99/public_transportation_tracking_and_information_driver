package com.example.public_transportation_driver_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.public_transportation_driver_application.R;
import com.example.public_transportation_driver_application.Retrofit.RetrofitClient;
import com.example.public_transportation_driver_application.Retrofit.ServerService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ServerService serverService;
    TextInputEditText usernameET, passwordET;
    public static final String EXTRA_ROUTE_NUMBER = "extra_route_number";
    public static final String EXTRA_PLATE_NUMBER = "extra_plate_number";
    public static final String EXTRA_USERNAME = "extra_username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameET = findViewById(R.id.userNameET);
        passwordET = findViewById(R.id.passwordET);
        serverService = RetrofitClient.getClient(ServerService.BASE_URL).create(ServerService.class);

    }

    public void loginUser(View view) {
        String username,password;
        username = usernameET.getText().toString().trim();
        password = passwordET.getText().toString().trim();

        if(username.equals("") || password.equals("")){
            Toast.makeText(LoginActivity.this, "Please Enter the username and password", Toast.LENGTH_SHORT).show();
        }
        else{
            Call<JsonObject> loginDriverCall = serverService.loginDriver(username, password);
            loginDriverCall.enqueue(new Callback<JsonObject>(){

                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    if(response.isSuccessful()){
                        JsonObject jsonObject = response.body();
                        int loginStatus = Objects.requireNonNull(jsonObject).get("login_status").getAsInt();

                        if(loginStatus == 1){
                            handleDriverObject(jsonObject);
                        }

                        else if(loginStatus == 0){
                            int errorCode = jsonObject.get("error_code").getAsInt();
                            switch(errorCode){
                                case 1:
                                    Toast.makeText(LoginActivity.this,"Wrong Password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 2:
                                    Toast.makeText(LoginActivity.this,"User not found.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 3:
                                    Toast.makeText(LoginActivity.this,"There was a problem with the server", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(LoginActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    public void handleDriverObject(JsonObject jsonObject){
        Intent intent;

        JsonObject driver = jsonObject.get("driver").getAsJsonObject();
        String routeNumber,plateNumber;

        routeNumber = driver.get("route_number").getAsString();
        plateNumber = driver.get("bus_plate_number").getAsString();

        intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra(EXTRA_ROUTE_NUMBER,routeNumber);
        intent.putExtra(EXTRA_PLATE_NUMBER,plateNumber);
        startActivity(intent);
    }

    public void exitApplication(View view) {
        this.finishAffinity();
    }

}