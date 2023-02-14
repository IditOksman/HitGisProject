package com.hit.hitgisproject.models

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

object PlacesDataBase {
    val placesList: MutableList<Place> = mutableListOf(
        Place("Raindrop Spa", LatLng(31.989987276093135, 34.7736121736623),"Rishon Leziyon"),
        Place("Spa Plus Atarim",LatLng(31.99314289159841, 34.77024437973963),"Rishon Leziyon"),
        Place("Titanic Spa",LatLng(31.99394362203064, 34.76689698316092),"Rishon Leziyon"),
        Place("Spa Yamit 2000",LatLng(32.00442526670171, 34.79462029146772), "Holon"),
        Place("Share Spa",LatLng(32.01796228399181, 34.73908784053374), "Bat Yam"),
        Place("Through The Body",LatLng(32.0409560730716, 34.74741341773422), "Tel Aviv"),
        Place("Spa Neve Tzedek",LatLng(32.061907409142506, 34.76492287697048), "Tel Aviv"),
        Place("Spa Boutique Tel Aviv",LatLng(32.080609936173936, 34.76824654366551), "Tel Aviv"),
        Place("Horizon Spa",LatLng(32.08256402646329, 34.76763962909625),"Tel Aviv"),
        Place("Sea & Spa",LatLng(32.0839010115377, 34.7712204250549),"Tel Aviv"),
        Place("Share Spa",LatLng(32.08575218935237, 34.76939968134711),"Tel Aviv"),
        Place("Sheva Spa",LatLng(32.09012287706883, 34.76994590445945),"Tel Aviv"),
        Place("Alexander Spa", LatLng(32.09444193954783, 34.772919785848835), "Tel Aviv"),
        Place("Dream Spa", LatLng(32.161874782602666, 34.80861448934904), "Herziliya"),
        Place("Tatiana Professional Spa", LatLng(32.00884785427306, 34.755004175429626), "Bat Yam"),
        Place("Valentina Spa", LatLng(32.02349262554476, 34.77902437944509), "Holon"),
        Place("Saharov Spa", LatLng(31.990741121425867, 34.772876521748685), "Rishon Leziyon"),
    )

    var markers: MutableList<Marker?> = mutableListOf()

    fun getCitiesList(): List<String> {
        return listOf(
            "Rishon Leziyon",
            "Holon",
            "Bat Yam",
            "Tel Aviv",
            "Herziliya",
            )
    }

    fun getPlacesByCity(city: String): List<Place> {
        return placesList.filter { place ->
           place.city == city
        }
    }

    fun getSpaByName(name: String): Place {
        return placesList.first { place ->
            place.name == name
        }
    }

    fun getHITPlace(): Place {
        return Place("HIT", LatLng(32.01496723191609, 34.774585828048764),"Holon")
    }
}
