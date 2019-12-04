package com.omagrahari.niyotailassignment.di.module;

import android.app.Application
import com.omagrahari.niyotailassignment.repository.ContentRepository
import com.omagrahari.niyotailassignment.utils.BestRoute
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun providesContentRepository(application: Application?): ContentRepository {
        return ContentRepository(application)
    }

    @Singleton
    @Provides
    fun providesBestRoute(): BestRoute {
        return BestRoute()
    }
}