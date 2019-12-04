package com.omagrahari.niyotailassignment

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.omagrahari.niyotailassignment.adapter.NearbySpotsAdapter
import com.omagrahari.niyotailassignment.databinding.ActivityMainBinding
import com.omagrahari.niyotailassignment.entity.Result
import com.omagrahari.niyotailassignment.utils.Constants.Companion.LOCATION_REQUEST_CODE
import com.omagrahari.niyotailassignment.utils.Constants.Companion.PARAM_LIST
import com.omagrahari.niyotailassignment.utils.GPSTracker
import com.omagrahari.niyotailassignment.utils.Utils
import com.omagrahari.niyotailassignment.viewmodel.MainActivityViewModel
import com.omagrahari.niyotailassignment.viewmodel.MainActivityViewModelFactory
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModeFactory: MainActivityViewModelFactory
    lateinit var viewModel: MainActivityViewModel
    lateinit var binding: ActivityMainBinding
    var nearbySpotsAdapter: NearbySpotsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        NiyotailApplication.niyotailApplication.getComponent().inject(this)
        viewModel = ViewModelProvider(this, viewModeFactory).get(MainActivityViewModel::class.java)

        checkNetworkConnection()
    }

    private fun checkNetworkConnection() {
        if (Utils.isNetworkAvailable(this)) {
            permissionRequest()
        } else {
            giveReloadOption(getString(R.string.error_turn_on_wifi))
        }
    }

    private fun getNearbyTouristSpots() {
        val gpsTracker = GPSTracker(this)
        viewModel.latitude = gpsTracker.getLocation()!!.latitude
        viewModel.longitude = gpsTracker.getLocation()!!.longitude
        binding.tvCity.text = "City: " + viewModel.getCity(Geocoder(this, Locale.getDefault()))
        binding.progress.visibility = VISIBLE

        viewModel.getNearBySpots.observe(this, Observer {
            binding.progress.visibility = GONE
            nearbySpotsAdapter = NearbySpotsAdapter(it.results)
            binding.rvSpots.adapter = nearbySpotsAdapter

            if (it.status != "OK") {
                giveReloadOption(it.status)
            }
        })
    }

    fun giveReloadOption(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun onSearch(view: View) {
        checkNetworkConnection()
    }

    fun showMap(view: View) {
        val selectedList = viewModel.getSpotsSelected()
        if (selectedList.size != 0) {
            val intent = Intent(this, MapActivity::class.java)
            intent.putParcelableArrayListExtra(PARAM_LIST, selectedList)
            startActivity(intent)
        } else
            Toast.makeText(this, getString(R.string.error_select), Toast.LENGTH_LONG).show()
    }

    fun findRoute(view: View) {
        val selectedList = viewModel.getSpotsSelected()
        if (selectedList.size != 0)
            viewModel.getRoute(selectedList).observe(this, Observer {
                showBestRoute(it)
            })
        else
            Toast.makeText(this, getString(R.string.error_select), Toast.LENGTH_LONG).show()
    }

    /**
     *  SHOW BEST ROUTE IN DIALOG
     */
    private fun showBestRoute(it: ArrayList<Result>) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.layout_best_route)

        val tvRoute = dialog.findViewById(R.id.tvBestRoute) as TextView

        var route: String? = ""

        for (i in it)
            route = route + "\n\n↓\n\n" + i.name + " "

        tvRoute.text = route

        dialog.show()
    }


    /**
     * REQUEST LOCATION PERMISSION
     */
    private fun permissionRequest() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            !== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                LOCATION_REQUEST_CODE
            )
        } else {
            getNearbyTouristSpots()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getNearbyTouristSpots()
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                ) {
                    showRationaleDialog()
                } else {
                    showRationaleDialog()
                }
            }
        }
    }

    private fun showRationaleDialog() {
        val dialog = AlertDialog.Builder(this)
            .setMessage(getString(R.string.msg_request_location))
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, which ->
                permissionRequest()
            }
            .create()
        dialog.show()
    }
}
