package com.omagrahari.niyotailassignment.network;

import com.omagrahari.niyotailassignment.entity.NearbySpotsResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {
    @GET("maps/api/place/nearbysearch/json?")
    suspend fun getNearbyTouristSpots(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String,
        @Query("key") apiKey: String
    ): NearbySpotsResponse
}
