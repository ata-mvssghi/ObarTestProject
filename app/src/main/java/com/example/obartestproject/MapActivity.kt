package com.example.obartestproject

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.util.GeoPoint
class MapActivity : AppCompatActivity() {

    private lateinit var mapView: MapView
    private lateinit var selectedLocation: GeoPoint
    private val CLICK_THRESHOLD = 10

    private var startX = 0f
    private var startY = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        Configuration.getInstance().setUserAgentValue("ObarTestProject")

        mapView = findViewById(R.id.mapview)

        mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK)
        mapView.setBuiltInZoomControls(true)
        mapView.setMultiTouchControls(true)

        val mapController: IMapController = mapView.controller
        mapController.setZoom(15)

        val initialLocation = GeoPoint(35.6892, 51.3890)
        mapController.setCenter(initialLocation)

        val marker = Marker(mapView)
        marker.icon = getDrawable(R.drawable.dropdown)
        marker.position = initialLocation
        mapView.overlays.add(marker)

        mapView.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.x
                    startY = event.y
                    false
                }

                MotionEvent.ACTION_UP -> {
                    val deltaX = Math.abs(event.x - startX)
                    val deltaY = Math.abs(event.y - startY)
                    if (deltaX <= CLICK_THRESHOLD && deltaY <= CLICK_THRESHOLD) {
                        val geoPoint = mapView.projection.fromPixels(event.x.toInt(), event.y.toInt())
                        selectedLocation = geoPoint as GeoPoint
                        marker.position = selectedLocation
                        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    }
                    false
                }

                else -> false
            }
        }
        val setLocationButton = findViewById<Button>(R.id.set_location_button)
        setLocationButton.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("latitude", selectedLocation.latitude)
            resultIntent.putExtra("longitude", selectedLocation.longitude)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
