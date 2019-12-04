package com.omagrahari.niyotailassignment.viewmodel

import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.omagrahari.niyotailassignment.entity.NearbySpotsResponse
import com.omagrahari.niyotailassignment.entity.Result
import com.omagrahari.niyotailassignment.repository.ContentRepository
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext


class MainActivityViewModel(private val contentRepository: ContentRepository) : ViewModel() {
    var latitude: Double? = null
    var longitude: Double? = null
    private val job: Job = SupervisorJob()
    val IO: CoroutineContext get() = Dispatchers.IO
    val coroutineContext: CoroutineContext get() = IO + job + CoroutineName(this.javaClass.simpleName)
    lateinit var nearbySpots: NearbySpotsResponse
    var route: ArrayList<Result> = ArrayList()

    val getNearBySpots = liveData(coroutineContext) {
        nearbySpots = contentRepository.getNearbySpots(latitude, longitude)

        emit(nearbySpots)
    }

    fun getRoute(selectedSpots: List<Result>) = liveData(coroutineContext) {
        route = ArrayList()
        val routeInt = contentRepository.getRoute(latitude, longitude, selectedSpots)

        for (i in routeInt) {
            if (i != 0) {
                route.add(selectedSpots.get(i - 1))
            }
        }

        emit(route)
    }

    fun getSpotsSelected(): ArrayList<Result> {
        val selectedSpots: List<Result> =
            nearbySpots.results!!.filter { it.isSelected }

        return selectedSpots as ArrayList<Result>
    }

    fun getCity(geocoder: Geocoder): String {
        try {
            val addresses: List<Address> = geocoder.getFromLocation(latitude!!, longitude!!, 1)
            val cityName: String = addresses[0].locality

            return cityName
        } catch (e: Exception) {
            return ""
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}