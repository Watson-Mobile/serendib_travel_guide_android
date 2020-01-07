package com.watson.serendibtravelguide.config;

public class Config {
    public static String serverIp = "http://ec2-34-238-82-190.compute-1.amazonaws.com/api/";
    public static String BASE_URL_IMG = serverIp+"image?image_path=";
    public static long LOCATION_REFRESH_TIME = 60;
    public static float LOCATION_REFRESH_DISTANCE = 0;
    public static final String SEARCH_URL = serverIp+"search_place/";
}
