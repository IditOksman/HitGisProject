package com.hit.hitgisproject.views.activities.main

import android.graphics.Path
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatCheckedTextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.hit.hitgisproject.R
import com.hit.hitgisproject.databinding.ActivityMainBinding
import com.hit.hitgisproject.models.Place
import com.hit.hitgisproject.models.PlacesDataBase
import com.hit.hitgisproject.views.activities.base.BaseActivity


class MainActivity : BaseActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private var mapFragment: SupportMapFragment? = null
    private var googleMap: GoogleMap? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        mapFragment = supportFragmentManager.findFragmentById(
            R.id.map_fragment
        ) as? SupportMapFragment
        mapFragment?.getMapAsync(::onMapReady)
        setContentView(binding.root)

        setCitySpinnerData()
        setListeners()
    }

    private fun setCitySpinnerData() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, PlacesDataBase.getCitiesList())
        binding.spinnerCity.adapter = adapter
        setSpaSpinnerData(binding.spinnerCity.selectedItem.toString())
    }

    private fun setSpaSpinnerDataByCity(places: List<Place>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, places.map { it.name })
        binding.spinnerSpa.adapter = adapter
    }

    private fun setSpaSpinnerData(city: String) {
        when(city) {
            "Rishon Leziyon" -> { setSpaSpinnerDataByCity(PlacesDataBase.getPlacesByCity("Rishon Leziyon")) }
            "Holon" -> { setSpaSpinnerDataByCity(PlacesDataBase.getPlacesByCity("Holon")) }
            "Bat Yam" -> { setSpaSpinnerDataByCity(PlacesDataBase.getPlacesByCity("Bat Yam")) }
            "Tel Aviv" -> { setSpaSpinnerDataByCity(PlacesDataBase.getPlacesByCity("Tel Aviv")) }
            "Herziliya" -> { setSpaSpinnerDataByCity(PlacesDataBase.getPlacesByCity("Herziliya")) }
        }
    }

    private fun setListeners() {
        binding.apply {
            submitBtn.setOnClickListener {
                showPlaceOnMap(spinnerSpa.selectedItem.toString())
            }

            spinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    setSpaSpinnerData((view as AppCompatCheckedTextView).text.toString())
                }
            }
        }
    }

    private fun showPlaceOnMap(spaName: String) {
        PlacesDataBase.getSpaByName(spaName).also { place ->

            val markerOptions = MarkerOptions()
                .position(place.LatitudeLongtitude)
            PlacesDataBase.markers.add(
                googleMap?.addMarker(markerOptions).apply {
                this?.title = place.name
            })
            googleMap?.moveCamera(CameraUpdateFactory.newLatLng(place.LatitudeLongtitude))
            googleMap?.animateCamera(CameraUpdateFactory.zoomIn())
            googleMap?.animateCamera(CameraUpdateFactory.zoomTo(15F), 2000, null)
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0

        googleMap?.setOnMarkerClickListener(this@MainActivity)

        PlacesDataBase.getHITPlace().also { place ->
            val markerOptions = MarkerOptions()
                .position(place.LatitudeLongtitude)
            PlacesDataBase.markers.add(
                googleMap?.addMarker(markerOptions).apply {
                    this?.title = place.name
                })
            googleMap?.moveCamera(CameraUpdateFactory.newLatLng(place.LatitudeLongtitude))
            googleMap?.animateCamera(CameraUpdateFactory.zoomIn())
            googleMap?.animateCamera(CameraUpdateFactory.zoomTo(15F), 2000, null)
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        PlacesDataBase.placesList.forEach { place ->
            if(place.name == marker.title) {
                marker.remove()
            }
        }
        return true
    }
}
