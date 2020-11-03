package com.example.public_transportation_driver_applicaiton.Retrofit;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ServerService {
    String BASE_URL = "https://192.168.42.145/fyp/public_transportation_tracking_and_information/";

    @POST("loginDriver.php")
    @FormUrlEncoded
    Call<JsonObject> loginDriver(
            @Field("username") String username,
            @Field("password") String password);

    @POST("createBus.php")
    @FormUrlEncoded
    Call<JsonObject> createBus(
            @Field("routeNumber") String routeNumber,
            @Field("plateNumber") String plateNumber,
            @Field("latitude") double latitude,
            @Field("longitude") double longitude,
            @Field("journey") boolean journey);

    @POST("deleteBus.php")
    @FormUrlEncoded
    Call<JsonObject> deleteBus(
            @Field("plateNumber") String plateNumber);

    @POST("getBusStop.php")
    @FormUrlEncoded
    Call<JsonObject> getBusStop(
            @Field("routeNumber") String routeNumber,
            @Field("journey") boolean journey);


    @POST("updateBusLocation.php")
    @FormUrlEncoded
    Call<JsonObject> updateBusLocation(
            @Field("plateNumber") String plateNumber,
            @Field("latitude") double latitude,
            @Field("longitude") double longitude);
}
