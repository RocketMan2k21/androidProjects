package com.bestdeveloper.funnyroad;

import android.graphics.Color;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PointsGenerator {

    private GoogleMap mMap;

    private Location currentLocation;

    // User distance
    private int distanceInMeters;

    private Circle circle;

    public PointsGenerator(GoogleMap map, Location currentLocation, int distanceInMeters) {
        this.mMap = map;
        this.currentLocation = currentLocation;
        this.distanceInMeters = distanceInMeters/2;
    }

    // Generates a circle and markers on it, returns List of coordinates
    public void generatePoints(){


        CircleOptions circleOptions = new CircleOptions()
                .center(calculateNewCoordinates(currentLocation.getLatitude(),
                        currentLocation.getLongitude(), distanceInMeters/1000, 180))
                .radius(distanceInMeters)
                .fillColor(R.color.black);

        circle = mMap.addCircle(circleOptions);








    }

    public  LatLng calculateNewCoordinates(double lat, double lon, double distanceKm, double bearingDegrees) {

        final double  earthRadiusKm = 6371; // Radius of the Earth in kilometers

        // Convert latitude and longitude from degrees to radians
        double latRad = Math.toRadians(lat);
        double lonRad = Math.toRadians(lon);

        // Convert bearing from degrees to radians
        double bearingRad = Math.toRadians(bearingDegrees);

        // Calculate new latitude
        double newLatRad = Math.asin(Math.sin(latRad) * Math.cos(distanceKm / earthRadiusKm) +
                Math.cos(latRad) * Math.sin(distanceKm / earthRadiusKm) * Math.cos(bearingRad));

        // Calculate new longitude
        double newLonRad = lonRad + Math.atan2(Math.sin(bearingRad) * Math.sin(distanceKm / earthRadiusKm) * Math.cos(latRad),
                Math.cos(distanceKm / earthRadiusKm) - Math.sin(latRad) * Math.sin(newLatRad));

        // Convert back to degrees
        double newLat = Math.toDegrees(newLatRad);
        double newLon = Math.toDegrees(newLonRad);

        return new LatLng(newLat, newLon);


    }

    public List<LatLng> getPointsOnCircumference(final double radius, int numOfPoints){

        LatLng center = new LatLng(0.0, 0.0);

        double slice = 360 / numOfPoints;
        ArrayList<LatLng> lngArrayList = new ArrayList<>();

        for (int i = 0; i < numOfPoints; i++)
        {
            double angle = slice * i;
            double X = center.latitude + radius * Math.cos(angle * Math.PI / 180);
            double Y = center.longitude + radius * Math.sin(angle * Math.PI / 180);

            DecimalFormat df = new DecimalFormat("#.##");
            lngArrayList.add(new LatLng(Double.parseDouble(df.format(X)), Double.parseDouble(df.format(Y))));

            mMap.addMarker(new MarkerOptions().position(lngArrayList.get(i))
                    .title("Marker" + i));

            Log.i("CRL", "point " + (i + 1) + " = X:" + lngArrayList.get(i).latitude + ", Y: " + lngArrayList.get(i).longitude);
        }

        return lngArrayList;

    }

}
