package com.omagrahari.niyotailassignment.utils

import android.content.Context
import android.location.Location
import android.net.ConnectivityManager
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.target.Target
import com.omagrahari.niyotailassignment.R
import com.omagrahari.niyotailassignment.utils.Constants.Companion.IMAGE_URL
import com.omagrahari.niyotailassignment.utils.Constants.Companion.REFENCE_ID
import java.text.DecimalFormat


class Utils {
    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, photoReference: String?) {
            if (!photoReference.isNullOrEmpty())
                GlideApp.with(view.context).load(
                    IMAGE_URL.replace(
                        REFENCE_ID,
                        photoReference
                    )
                ).override(Target.SIZE_ORIGINAL).placeholder(R.drawable.placeholder).into(view)
        }

        @JvmStatic
        @BindingAdapter("typeText")
        fun setText(view: TextView, types: List<String>) {
            var strType = "Types: "
            if (types != null)
                for (type in types) {
                    strType = strType + type.replace("_", " ") + ", "
                }
            view.text = strType
        }

        fun getDistance(latOrigin: Double,longOrigin: Double, latDest: Double, longDest: Double): Float {
            val startPoint = Location("locationA")
            startPoint.setLatitude(latOrigin)
            startPoint.setLongitude(longOrigin)

            val endPoint = Location("locationB")
            endPoint.setLatitude(latDest)
            endPoint.setLongitude(longDest)

            val distance: Float = startPoint.distanceTo(endPoint)

            val decimalFormat = DecimalFormat("#.##")
            return decimalFormat.format(distance/1000).toFloat()
        }
    }
}