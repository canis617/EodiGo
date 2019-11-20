package com.hyuncho.ranplgo.ui.visitor

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.hyuncho.ranplgo.R
import com.hyuncho.ranplgo.ui.base.BaseActivity
import com.hyuncho.ranplgo.utils.Location
import kotlinx.android.synthetic.main.activity_visitor.*


class VisitorActivity : BaseActivity(),
    VisitorContract.View, OnMapReadyCallback {

    private lateinit var visitorPresenter: VisitorPresenter
    private lateinit var currentLocation : Location

    private var selectedLocation : LatLng = LatLng(-30.0, 120.0)
    private var selectedMarker : Marker? = null
    private var currentMarker : Marker? = null
    private var startMarker : Marker? = null
    private var rangeMarker : Circle? = null
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visitor)
        initPresenter()

        initBtn()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        visitorPresenter.locationInit(this)
        visitorPresenter.checkPermission(this)

    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0

        val location = LatLng(37.54, 126.99)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))

    }

    override fun onDestroy() {
        super.onDestroy()
        visitorPresenter.detachView()
    }

    override fun onResume() {
        super.onResume()
        visitorPresenter.addLocationListener()
    }

    override fun initPresenter() {
        visitorPresenter = VisitorPresenter()
        visitorPresenter.attachView(this)
    }

    override fun showError(error: String) {
        Toast.makeText(this@VisitorActivity, error, Toast.LENGTH_SHORT).show()
    }

    fun initBtn() {
        game_create_btn.setOnClickListener {
            visitorPresenter.gameCreate(max_distance.text.toString(), currentLocation)
        }
        show_street_view_btn.setOnClickListener {
            visitorPresenter.showStreetView(this)
        }
        select_btn.setOnClickListener {
            visitorPresenter.visit(this, Integer.parseInt(max_distance.text.toString()))
        }
    }

    override fun updateMap(startLocation : LatLng, circleMarker: CircleOptions) {
        if(startMarker != null){
            startMarker?.remove()
        }
        startMarker = mMap.addMarker(MarkerOptions().position(startLocation).title("Your Starting Location"))
        if(rangeMarker != null){
            rangeMarker?.remove()
        }
        rangeMarker = mMap.addCircle(circleMarker)
    }

    inner class MyLocationCallBack : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)

            val location = p0?.lastLocation
            // 위도 경도를 지도 서버에 전달하면,
            // 위치에 대한 지도 결과를 받아와서 저장.

            location?.run {
                val latLng = LatLng(latitude, longitude) // 위도 경도 좌표 전달.
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
                // 지도에 애니메이션 효과로 카메라 이동.
                // 좌표 위치로 이동하면서 배율은 17 (0~19까지 범위가 존재.)

                Log.d("MapsActivity", "위도: $latitude, 경도 : $longitude")

                currentLocation = Location(latLng)

                if(currentMarker != null){
                    currentMarker?.remove()
                }
                currentMarker = mMap.addMarker(MarkerOptions().position(currentLocation.getLatLng()).title("Your Current Location"))
                visitorPresenter.updateCurrentLocation(currentLocation.getLatLng())

            }
        }
    }

}
