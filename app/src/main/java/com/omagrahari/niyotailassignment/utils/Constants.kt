package com.omagrahari.niyotailassignment.utils

import com.omagrahari.niyotailassignment.BuildConfig.API_KEY

class Constants {
    companion object {
        val LOCATION_REQUEST_CODE = 111
        val BASE_URL = "https://maps.googleapis.com/"
        val IMAGE_URL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=300&photoreference=<REFERENCE_ID>&key=" + API_KEY
        val REFENCE_ID = "<REFERENCE_ID>"
        val PARAM_LIST = "PARAM_LIST"
    }
}