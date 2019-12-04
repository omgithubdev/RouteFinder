package com.omagrahari.niyotailassignment.di.module

import com.omagrahari.niyotailassignment.network.ApiInterface
import com.omagrahari.niyotailassignment.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun providesApiInterface(retrofit: Retrofit): ApiInterface =
        retrofit.create<ApiInterface>(ApiInterface::class.java)

    @get:Provides
    @get:Singleton
    val retrofit: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}