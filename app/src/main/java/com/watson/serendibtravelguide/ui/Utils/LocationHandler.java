package com.watson.serendibtravelguide.ui.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.mapbox.geojson.Point;

import static android.content.Context.LOCATION_SERVICE;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.watson.serendibtravelguide.config.Config.LOCATION_REFRESH_DISTANCE;
import static com.watson.serendibtravelguide.config.Config.LOCATION_REFRESH_TIME;

public class LocationHandler {
    private static LocationManager mLocationManager;
    public static Point defaultLocation = Point.fromLngLat(79.899963, 6.797072);
    public static Point currentLocation = Point.fromLngLat(79.899963, 6.797072);

    private static final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
            String longitude = "Longitude: " + location.getLongitude();
            String latitude = "Latitude: " + location.getLatitude();
            String s = longitude + "\n" + latitude;
            Log.d(TAG, "location String : " + s);

            currentLocation = Point.fromLngLat(location.getLongitude(), location.getLatitude());
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };


    public static void initLocationHandler(Activity activity){
        mLocationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);

        if (activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        if (activity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                LOCATION_REFRESH_DISTANCE, mLocationListener);
    }

//    public static Point getCurrentLocation() {
//        return currentLocation;
//    }
}
