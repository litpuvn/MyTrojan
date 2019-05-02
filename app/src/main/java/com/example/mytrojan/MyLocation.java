package com.example.mytrojan;

import java.util.Timer;
import java.util.TimerTask;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

public class MyLocation {
    private static final String TAG = "MyLocation";
    Timer timer;
    LocationManager lm;
    LocationResult locationResult;
    private Long startTime;
    private boolean gpsEnabled = false;
    private boolean networkEnabled = false;
    private Location gpsLocation;
    private Location networkLocation;
    private int maxTimeToRunMs;
    private boolean preferGps;

    @SuppressLint("MissingPermission")
    public boolean getLocation(Context context, LocationResult result, int maxTimeToRunMs, boolean useGps,
                               boolean useNetwork, boolean preferGps) {
        this.maxTimeToRunMs = maxTimeToRunMs;
        // I use LocationResult callback class to pass location value from
        // MyLocation to user code.
        locationResult = result;
        this.preferGps = preferGps;
        if (lm == null) {
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }

        // exceptions will be thrown if provider is not permitted.
        if (useGps) {
            try {
                gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception e) {
                Log.e(TAG, "Error checking gps availability.", e);
            }
        }
        if (useNetwork) {
            try {
                networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception e) {
                Log.e(TAG, "Error checking network availability.", e);
            }
        }

        Log.d(TAG, "Gps: " + gpsEnabled + ", network: " + networkEnabled);

        // don't start listeners if no provider is enabled
        if (!gpsEnabled && !networkEnabled) {
            Toast.makeText(context, "No location provider enabled", Toast.LENGTH_LONG).show();
            locationResult.gotLocation(null);
            return false;
        }

        if (gpsEnabled) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
        }
        if (networkEnabled) {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
        }
        // TODO register timer task and cancel onDestroy to avoid OutOfMemoryError
        startTime = System.currentTimeMillis();
        timer = new Timer();
        timer.scheduleAtFixedRate(new CheckLocation(), 0, 1000);
        return true;
    }

    public boolean getLocation(Context context, LocationResult locationResult) {

        return this.getLocation(context, locationResult, -1, false, false, false);
    }

    private class CheckLocation extends TimerTask {

        @Override
        public void run() {
            checkIsLocationGoodEnough();
        }

    }

    /**
     * <p>
     * Checks whether the provided location is accurate enough. If it is, stops
     * the listeners and fires the callback.
     * </p>
     * The location is good enough if
     * <ul>
     * <li>the location is gps provided, or</li>
     * <li>accuracy <= 50 meters, or</li>
     * <li>10 seconds has passed and we still do not have a gps location</li>
     * </ul>
     *
     */
    private void checkIsLocationGoodEnough() {
        Location ret = null;
        Long runtime = System.currentTimeMillis() - startTime;
        Log.d(TAG, "runtime: " + runtime);
        if (gpsEnabled && networkEnabled) {
            if (gpsLocation != null) {
                ret = gpsLocation;
            } else if (networkLocation != null && networkLocation.hasAccuracy() && networkLocation.getAccuracy() <= 50) {
                ret = networkLocation;
            } else if (!preferGps && runtime > 10000 && networkLocation != null) {
                ret = networkLocation;
            }
        } else if (gpsEnabled) {
            if (gpsLocation != null) {
                ret = gpsLocation;
            }
        } else if (networkEnabled) {
            if (networkLocation != null && networkLocation.hasAccuracy() && networkLocation.getAccuracy() <= 50) {
                ret = networkLocation;
            } else if (runtime > 10000 && networkLocation != null) {
                ret = networkLocation;
            }
        }
        if (ret != null) {
            timer.cancel();
            locationResult.gotLocation(ret);
            lm.removeUpdates(locationListenerGps);
            lm.removeUpdates(locationListenerNetwork);
        } else if (runtime > maxTimeToRunMs) {
            getLastLocation();
            timer.cancel();
        }
    }

    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            gpsLocation = location;
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            networkLocation = location;
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        lm.removeUpdates(locationListenerGps);
        lm.removeUpdates(locationListenerNetwork);

        Location netLoc = null;
        Location gpsLoc = null;
        if (gpsEnabled) {
            gpsLoc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (networkEnabled) {
            netLoc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        // if there are both values use the latest one
        if (gpsLoc != null && netLoc != null) {
            if (gpsLoc.getTime() > netLoc.getTime()) {
                locationResult.gotLocation(gpsLoc);
            } else {
                locationResult.gotLocation(netLoc);
            }
            return;
        }

        if (gpsLoc != null) {
            locationResult.gotLocation(gpsLoc);
            return;
        }
        if (netLoc != null) {
            locationResult.gotLocation(netLoc);
            return;
        }
        locationResult.gotLocation(null);
    }

    /**
     * TODO Call this from onPause to prevent application crashing when the
     * location fetching is interrupted.
     */
    public void cancelTimer() {
        timer.cancel();
        lm.removeUpdates(locationListenerGps);
        lm.removeUpdates(locationListenerNetwork);
    }

    public static abstract class LocationResult {
        public abstract void gotLocation(Location location);
    }
}
