package com.omagrahari.niyotailassignment

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.omagrahari.niyotailassignment.entity.Result
import com.omagrahari.niyotailassignment.utils.Constants.Companion.PARAM_LIST

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    var spotList: ArrayList<Result> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        if (intent != null) {
            spotList = intent.getParcelableArrayListExtra(PARAM_LIST)
        } else {
            finish()
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        for (result in spotList) {
            createMarker(
                result.geometry.location.lat,
                result.geometry.location.lng,
                result.name,
                map
            )
        }

        val cameraPosition = CameraPosition.Builder()
            .target(
                LatLng(
                    spotList.get(0).geometry.location.lat,
                    spotList.get(0).geometry.location.lng
                )
            )
            .zoom(12f).build()
        //Zoom in and animate the camera.
        //Zoom in and animate the camera.
        map!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    fun createMarker(
        latitude: Double,
        longitude: Double,
        title: String?,
        map: GoogleMap?
    ): Marker? {
        return map!!.addMarker(
            MarkerOptions()
                .position(LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()

        return super.onOptionsItemSelected(item)
    }
}
