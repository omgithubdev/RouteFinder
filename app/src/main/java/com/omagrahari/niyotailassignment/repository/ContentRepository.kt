package com.omagrahari.niyotailassignment.repository;


import android.app.Application
import com.omagrahari.niyotailassignment.BuildConfig
import com.omagrahari.niyotailassignment.NiyotailApplication
import com.omagrahari.niyotailassignment.entity.*
import com.omagrahari.niyotailassignment.network.ApiInterface
import com.omagrahari.niyotailassignment.utils.BestRoute
import com.omagrahari.niyotailassignment.utils.Utils.Companion.getDistance
import javax.inject.Inject

class ContentRepository(application: Application?) {
    @Inject
    lateinit var apiInterface: ApiInterface
    @Inject
    lateinit var bestRoute: BestRoute

    val application: Application = application!!

    init {
        NiyotailApplication.niyotailApplication.getComponent().inject(this)
    }

    suspend fun getNearbySpots(latitude: Double?, longitude: Double?): NearbySpotsResponse {
        val location = latitude.toString() + "," + longitude.toString()
        val nearBySpots = apiInterface.getNearbyTouristSpots(
            location,
            10000,
            "tourist_attraction",
            BuildConfig.API_KEY
        )

        for (result in nearBySpots.results) {
            val distance = getDistance(
                latitude!!,
                longitude!!,
                result.geometry.location.lat,
                result.geometry.location.lng
            )
            result.distance = distance
        }

        return nearBySpots
    }

    fun getRoute(
        latitude: Double?,
        longitude: Double?,
        selectedSpots: List<Result>
    ): ArrayList<Int> {
        val distanceArray = getDistanceArray(latitude, longitude, selectedSpots)

        val path: ArrayList<Int> = bestRoute.calculateRoute(distanceArray)

        return path
    }


    fun getDistanceArray(
        latitude: Double?,
        longitude: Double?,
        selectedSpots: List<Result>
    ): ArrayList<ArrayList<Float>> {
        var geometryList: ArrayList<Geometry> = ArrayList()
        var geometry = Geometry(Location(latitude!!, longitude!!), Viewport(null, null))
        geometryList!!.add(geometry)

        geometryList.addAll(selectedSpots!!.map { it.geometry!! })

        var distArray = ArrayList<ArrayList<Float>>()

        for (i in geometryList) {
            var distances: ArrayList<Float> = ArrayList()
            for (j in geometryList) {
                if (i.location.lat == j.location.lat
                    && i.location.lng == j.location.lng
                )
                    distances.add(0f)
                else
                    distances.add(
                        getDistance(i.location.lat, i.location.lng, j.location.lat, j.location.lng)
                    )

            }
            distArray.add(distances)
        }

        return distArray
    }
}
