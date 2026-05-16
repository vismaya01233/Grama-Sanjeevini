package com.gramasanjeevini.activities

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.gramasanjeevini.R
import com.gramasanjeevini.viewmodels.MedicineViewModel

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel: MedicineViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true

        enableMyLocation()
        
        viewModel.allShops.observe(this) { shops ->
            mMap.clear()
            for (shop in shops) {
                val position = LatLng(shop.latitude, shop.longitude)
                mMap.addMarker(
                    MarkerOptions()
                        .position(position)
                        .title(shop.name)
                        .snippet("${shop.village} - ${if (shop.isOpen) "Open" else "Closed"}")
                )
            }
            
            // If no specific location passed, zoom to fit all markers
            val targetLat = intent.getDoubleExtra(EXTRA_LATITUDE, Double.NaN)
            val targetLng = intent.getDoubleExtra(EXTRA_LONGITUDE, Double.NaN)
            if (targetLat.isNaN() || targetLng.isNaN()) {
                zoomToFitMarkers()
            }
        }

        viewModel.loadAllShops()

        // Check if a specific location was passed
        val targetLat = intent.getDoubleExtra(EXTRA_LATITUDE, Double.NaN)
        val targetLng = intent.getDoubleExtra(EXTRA_LONGITUDE, Double.NaN)

        if (!targetLat.isNaN() && !targetLng.isNaN()) {
            val targetLocation = LatLng(targetLat, targetLng)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(targetLocation, 16f))
        } else {
            fetchAndMoveToCurrentLocation()
        }
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    private fun fetchAndMoveToCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                }
            }
    }

    private fun zoomToFitMarkers() {
        val shops = viewModel.allShops.value ?: return
        if (shops.isEmpty()) return

        val builder = LatLngBounds.Builder()
        for (shop in shops) {
            builder.include(LatLng(shop.latitude, shop.longitude))
        }
        val bounds = builder.build()
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation()
                fetchAndMoveToCurrentLocation()
            }
        }
    }

    companion object {
        const val EXTRA_LATITUDE = "extra_latitude"
        const val EXTRA_LONGITUDE = "extra_longitude"
        const val EXTRA_TITLE = "extra_title"
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}
