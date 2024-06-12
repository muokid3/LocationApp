package com.dm.berxley.locationapp.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.dm.berxley.locationapp.models.LocationData
import com.dm.berxley.locationapp.viewModels.LocationVM
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import java.util.Locale

class LocationUtils(val context: Context) {

    private val _fuseLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun requestLocationUpdates(viewModel: LocationVM){
        val locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let {
                    val location = LocationData(lat = it.latitude , lng = it.longitude)
                    viewModel.updateLocation(location)
                }
            }
        }

        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,1000).build()

       _fuseLocationClient.requestLocationUpdates(locationRequest,locationCallback, Looper.getMainLooper())
    }
    fun hasLocationPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun reverseGeocodeLocation(locationData: LocationData): String{

        val geocoder = Geocoder(context, Locale.getDefault())
        val coordinate = LatLng(locationData.lat, locationData.lng)
        val addresses:MutableList<Address>? = geocoder.getFromLocation(coordinate.latitude,coordinate.longitude,1)

        return if(addresses?.isNotEmpty() == true){
            addresses[0].getAddressLine(0)
        }else{
            "Address not found"
        }

    }
}