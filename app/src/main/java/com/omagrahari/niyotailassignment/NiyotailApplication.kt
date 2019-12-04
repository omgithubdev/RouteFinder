package com.omagrahari.niyotailassignment

import android.app.Application
import com.omagrahari.niyotailassignment.di.ActivityComponent
import com.omagrahari.niyotailassignment.di.DaggerActivityComponent
import com.omagrahari.niyotailassignment.di.module.ApplicationModule

class NiyotailApplication : Application() {
    private lateinit var component: ActivityComponent

    companion object {
        lateinit var niyotailApplication: NiyotailApplication private set
    }

    override fun onCreate() {
        super.onCreate()

        niyotailApplication = this

        component = DaggerActivityComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    fun getComponent(): ActivityComponent {
        return component
    }
}