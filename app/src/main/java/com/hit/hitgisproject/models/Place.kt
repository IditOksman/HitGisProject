package com.hit.hitgisproject.models

import com.google.android.gms.maps.model.LatLng

data class Place(
    var name: String,
    var LatitudeLongtitude: LatLng,
    var city: String,
    var content: String

)
