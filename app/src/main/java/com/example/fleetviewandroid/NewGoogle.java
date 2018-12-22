package com.example.fleetviewandroid;

import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
 
public class NewGoogle extends FragmentActivity implements LocationListener {

    //String url="http://192.168.2.239:8181/FleetViewProjectTest/MyServlet";
    String url="http://twtech.in:8080/FleetViewProject/MyServlet";

    private GoogleMap googleMap;
    private double latitude;
    private double longitude;
    private String date;
    private String time;
    private String vname;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_google);
        
        Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		 latitude = Double.parseDouble(bundle.getString("lat"));
		 longitude = Double.parseDouble(bundle.getString("lng"));
		 date = bundle.getString("date");
		 time = bundle.getString("time");
		 vname = bundle.getString("vname");
        
        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
 
        // Showing status
        if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available
 
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
 
        }else { // Google Play Services are available
 /*
            // Getting reference to the SupportMapFragment of activity_main.xml
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
 
            // Getting GoogleMap object from the fragment
            googleMap = fm.getMap();
 
            // Enabling MyLocation Layer of Google Map
           // googleMap.setMyLocationEnabled(true);
 */           
        	googleMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();

   
            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            // Creating a LatLng object for the current location
            LatLng loc = new LatLng(latitude, longitude);
            
            Marker kiel = googleMap.addMarker(new MarkerOptions()
    		.position(loc)
    		.title(vname)
    		.snippet(date + "    "  +time)
    		.icon(BitmapDescriptorFactory
    				.fromResource(R.drawable.blue_marker)));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));
 
          /*  // Getting LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
 
            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();
 
            // Getting the name of the best provider
            //String provider = locationManager.getBestProvider(criteria, true);
 
            // Getting Current Location
            Location location = locationManager.getLastKnownLocation(provider);
 
            if(location!=null){
                onLocationChanged(location);
            }
            locationManager.requestLocationUpdates(provider, 20000, 0, this);
            */
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        
        // Getting latitude of the current location
        double latitude = 15.763544;
 
        // Getting longitude of the current location
        double longitude = 18.346343;
 
        // Creating a LatLng object for the current location
        LatLng loc = new LatLng(latitude, longitude);
        
        Marker kiel = googleMap.addMarker(new MarkerOptions()
		.position(loc)
		.title("Vehicle")
		.snippet("This is your vehicle")
		.icon(BitmapDescriptorFactory
				.fromResource(R.drawable.blue_marker)));
        
//googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));
 
        // Showing the current location in Google Map
 //       googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
 
        // Zoom in the Google Map
   googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
 
        // Setting latitude and longitude in the TextView tv_location
     //   tvLocation.setText("Latitude:" +  latitude  + ", Longitude:"+ longitude );
 
    }
 
    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }
 
    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }
 
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }
 
  
}