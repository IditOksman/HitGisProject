package com.hit.hitgisproject.views.activities.main

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Path
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
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
import com.hit.hitgisproject.utils.WazeDialog
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.topbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.favourite) {
            binding.clearAllMarkersFab.callOnClick()
            setAllFavouritePlacesOnMap()
            Toast.makeText(this, "Favourite places shown on the map!", Toast.LENGTH_LONG).show()
            return true
        }
        return super.onOptionsItemSelected(item)
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

    private fun setAllFavouritePlacesOnMap() {
        PlacesDataBase.placesList.forEach { place ->
            if(place.isFavourite) {
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
            clearAllMarkersFab.setOnClickListener {
                googleMap?.clear()
                getAndPlaceDefaultMarker()
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

        getAndPlaceDefaultMarker()
    }

    private fun getAndPlaceDefaultMarker() {
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
                WazeDialog(this).show("Navigate using waze?","") {
                    if(it == WazeDialog.ResponseType.YES) {
                        val uri = WAZE_PREFIX + place.LatitudeLongtitude.latitude + ", " + place.LatitudeLongtitude.longitude + WAZE_POSTFIX
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
                    }
                    if(it == WazeDialog.ResponseType.FAVOURITE) {
                        place.isFavourite = true
                        Toast.makeText(this,"Place set as favourite!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        return true
    }

    companion object {
        const val WAZE_PREFIX = "https://waze.com/ul?ll="
        const val WAZE_POSTFIX = "&navigate=yes"
    }
}

