
package com.hyuncho.ranplgo.ui.googlemap

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.StreetViewPanorama
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.StreetViewPanoramaLink
import com.hyuncho.ranplgo.R

class GoogleMapActivity : AppCompatActivity(){

    // George St, Sydney
    private lateinit var location : LatLng

    private lateinit var streetViewPanorama: StreetViewPanorama



    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_street_view_panorama)

        getLocationIntent()

        val streetViewPanoramaFragment = supportFragmentManager.findFragmentById(R.id.google_street_view_map) as SupportStreetViewPanoramaFragment
        Log.d("streetViewPanoramaFragment", streetViewPanoramaFragment.toString())
        streetViewPanoramaFragment.getStreetViewPanoramaAsync { panorama ->
            streetViewPanorama = panorama
            // Only set the panorama to sydney on startup (when no panoramas have been
            // loaded which is when the savedInstanceState is null).
            if (savedInstanceState == null) {
                streetViewPanorama.setPosition(location,1000)

                streetViewPanorama.setUserNavigationEnabled(true);
                streetViewPanorama.setPanningGesturesEnabled(true);
                streetViewPanorama.setZoomGesturesEnabled(true);
            }
        }
    }

    private fun getLocationIntent(){
        if(intent.hasExtra("lat")){
            location = LatLng(intent.getDoubleExtra("lat", 37.553473),intent.getDoubleExtra("lng", 127.035819))
        }
    }

    private fun checkReadyThen(stuffToDo : () -> Unit) {
        if (!::streetViewPanorama.isInitialized) {
            Toast.makeText(this, "map is not ready", Toast.LENGTH_SHORT).show()
        } else {
            stuffToDo()
        }
    }

    private fun Array<StreetViewPanoramaLink>.findClosestLinkToBearing(
        bearing: Float
    ): StreetViewPanoramaLink {

        // Find the difference between angle a and b as a value between 0 and 180.
        val findNormalizedDifference = fun (a: Float, b: Float): Float {
            val diff = a - b
            val normalizedDiff = diff - (360 * Math.floor((diff / 360.0f).toDouble())).toFloat()
            return if ((normalizedDiff < 180.0f)) normalizedDiff else 360.0f - normalizedDiff
        }

        var minBearingDiff = 360f
        var closestLink = this[0]
        for (link in this) {
            if (minBearingDiff > findNormalizedDifference(bearing, link.bearing)) {
                minBearingDiff = findNormalizedDifference(bearing, link.bearing)
                closestLink = link
            }
        }
        return closestLink
    }
}