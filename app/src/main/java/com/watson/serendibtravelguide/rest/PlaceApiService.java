package com.watson.serendibtravelguide.rest;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
//import retrofit2.http.Path;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.watson.serendibtravelguide.data.model.User;
import com.watson.serendibtravelguide.data.model.UserResponse;
import com.watson.serendibtravelguide.models.ImageUploadResponse;
import com.watson.serendibtravelguide.models.Place;
import com.watson.serendibtravelguide.models.PlaceAddResponse;
import com.watson.serendibtravelguide.models.PlaceResponse;

import java.util.List;

public interface PlaceApiService {

    //SAMPLE ENDPOINT https://api.themoviedb.org/3/movie/top_rated
    @GET("place")
    Call<PlaceResponse> getByLocation(@Query("longitude") String longitude, @Query("latitude") String latitude);

    @GET("all_place")
    Call<PlaceResponse> getAllPlaces();

    @GET("search_place")
    Call<PlaceResponse> searchPlaces(@Query("query") String query);

    @POST("place")
    @FormUrlEncoded
    Call<PlaceAddResponse> savePlace(@Body Place place);

    @POST("place/add")
    @FormUrlEncoded
    Call<PlaceAddResponse> savePlaceSeperate(
            @Field("name") String name,
            @Field("location") String[] location,
            @Field("description") String description,
            @Field("other_names") String otherNames,
//            @Field("type") String[] type,
            @Field("type_array") String type,
            @Field("user_id") String id,
            @Field("images") String imagePaths);

    @POST("place")
    @Multipart
    Call<PlaceAddResponse> savePlaceWithImage(
            @Part MultipartBody.Part file
            , @Part("name") RequestBody name
            , @Part("description") RequestBody description
            , @Part("type") RequestBody type
            , @Part("location") RequestBody location);

    @POST("place/images")
    @Multipart
    Call<ImageUploadResponse> uploadImage(
            @Part MultipartBody.Part file,
            @Query("name") String name);

    @GET("not_verified_place")
    Call<PlaceResponse> getNotVerifiedPlaces(@Query("longitude") String longitude, @Query("latitude") String latitude);

    @PUT("place/verify/{id}")
//    @FormUrlEncoded
    Call<PlaceAddResponse>  verifyPlace(@Path("id") String id);



}
