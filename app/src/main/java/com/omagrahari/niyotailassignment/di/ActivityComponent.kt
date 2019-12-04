package com.omagrahari.niyotailassignment.di

import com.omagrahari.niyotailassignment.MainActivity
import com.omagrahari.niyotailassignment.di.module.ApplicationModule
import com.omagrahari.niyotailassignment.di.module.RepositoryModule
import com.omagrahari.niyotailassignment.di.module.RetrofitModule
import com.omagrahari.niyotailassignment.repository.ContentRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, RepositoryModule::class, RetrofitModule::class])
interface ActivityComponent {
    fun inject(mainActivity: MainActivity?)

    fun inject(contentRepository: ContentRepository?)
}
