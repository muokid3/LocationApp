package com.dm.berxley.locationapp.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dm.berxley.locationapp.models.LocationData

class LocationVM: ViewModel() {
    private val _location = mutableStateOf<LocationData?>(null)
    var location: State<LocationData?> = _location

    fun updateLocation(newLocationData: LocationData){
        _location.value = newLocationData
    }
}