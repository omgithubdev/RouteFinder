package com.omagrahari.niyotailassignment.utils;

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log

class GPSTracker(private val mContext: Context) : Service(),
    LocationListener {
    // flag for GPS status
    var isGPSEnabled = false
    // flag for network status
    var isNetworkEnabled = false
    // flag for GPS status
    var canGetLocation = false
    private var location: Location? = null
    private var latitude = 0.0
    private var longitude = 0.0
    // Declaring a Location Manager
    protected var locationManager: LocationManager? = null

    @SuppressLint("MissingPermission")
    fun getLocation(): Location? {
        try {
            locationManager = mContext
                .getSystemService(Context.LOCATION_SERVICE) as LocationManager
            // getting GPS status
            isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
            // getting network status
            isNetworkEnabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (!isGPSEnabled && !isNetworkEnabled) { // no network provider is enabled
            } else {
                canGetLocation = true
                if (isNetworkEnabled) {
                    locationManager!!.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this
                    )
                    Log.d("Network", "Network")
                    if (locationManager != null) {
                        location =
                            locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                        if (location != null) {
                            latitude = location!!.latitude
                            longitude = location!!.longitude
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager!!.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this
                        )
                        Log.d("GPS Enabled", "GPS Enabled")
                        if (locationManager != null) {
                            location = locationManager!!
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                            if (location != null) {
                                latitude = location!!.latitude
                                longitude = location!!.longitude
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
        }
        return location
    }

    override fun onLocationChanged(location: Location) {
        var bestAccuracy = -1f
        if (location.accuracy != 0.0f
            && location.accuracy < bestAccuracy || bestAccuracy == -1f
        ) {
            locationManager!!.removeUpdates(this)
        }
        bestAccuracy = location.accuracy
    }

    override fun onProviderDisabled(provider: String) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onStatusChanged(
        provider: String,
        status: Int,
        extras: Bundle
    ) {
    }

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    val accurecy: Float
        get() = location!!.accuracy

    companion object {
        // The minimum distance to change Updates in meters
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10 // 10 meters
        // The minimum time between updates in milliseconds
        private const val MIN_TIME_BW_UPDATES = 1000 * 60 * 1 // 1 minute
            .toLong()
    }

}
