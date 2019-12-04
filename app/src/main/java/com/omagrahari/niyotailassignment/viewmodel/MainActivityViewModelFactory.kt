package com.omagrahari.niyotailassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.omagrahari.niyotailassignment.repository.ContentRepository
import javax.inject.Inject

class MainActivityViewModelFactory @Inject constructor(private val contentRepositry: ContentRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainActivityViewModel::class.java!!)) {
            MainActivityViewModel(this.contentRepositry) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}