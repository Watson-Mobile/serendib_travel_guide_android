package com.watson.serendibtravelguide.rest;

import retrofit2.Call;
import retrofit2.http.GET;
//import retrofit2.http.Path;
import retrofit2.http.Query;

import com.watson.serendibtravelguide.models.MovieResponse;

public interface MovieApiService {
    //MOVIE SEARCH AUTOCOMPLETE
//    @GET("/search/movie")
//    Call<SearchMovieResponse> search(@Query("api_key") String apiKey, @Query("query") String query);

    //TOP RATED MOVIES https://api.themoviedb.org/3/movie/top_rated
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    //MOVIE DETAIL
//    @GET("/3/movie/{id}")
//    Call<MovieResponse> movieDetails(@Path("id") int movieID, @Query("api_key") String apiKey);
//
//    //MOVIE IMAGES
//    @GET("/movie/{id}/images")
//    Call<ImagesResponse> movieImages(@Query("api_key") String apiKey, @Path("id") int movieID);
}