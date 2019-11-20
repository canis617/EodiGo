package com.hyuncho.ranplgo.ui.result


import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.hyuncho.ranplgo.R
import com.hyuncho.ranplgo.data.location.location
import com.hyuncho.ranplgo.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : BaseActivity(),
    ResultContract.View , OnMapReadyCallback {

    private lateinit var resultPresenter: ResultPresenter
    private lateinit var startLocation :LatLng
    private lateinit var selectedLocation :LatLng
    private lateinit var resultLocation :LatLng
    private lateinit var resultLocationList : ArrayList<LatLng>
    private lateinit var selectedLocationList : ArrayList<LatLng>
    private lateinit var mMap: GoogleMap
    private lateinit var mode : String
    private var range : Int = 0


    val polylineOptions = PolylineOptions().width(5f).color(Color.RED)

    val polylineOptionsResult = PolylineOptions().width(5f).color(Color.RED)
    val polylineOptionsSelected = PolylineOptions().width(5f).color(Color.BLUE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        resultPresenter.attachView(this)

        initPresenter()
        receiveIntent()
        initBtn()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        if(mode == "tracking"){
            setScoreText(resultPresenter.setScoreListText(resultLocationList, selectedLocationList, range))
        }
        else {
            setScoreText(resultPresenter.getResultScore(resultLocation,selectedLocation,range))
        }

        resultPresenter.saveResultToFirebase(mode)
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0


        if(mode == "tracking"){
            for(resultList in resultLocationList){
                polylineOptionsResult.add(resultList)
            }
            for(selectedList in selectedLocationList){
                polylineOptionsSelected.add(selectedList)
            }
            mMap.addPolyline(polylineOptionsResult)
            mMap.addPolyline(polylineOptionsSelected)
        }
        else {
            if(mode == "guesser"){
                mMap.addMarker(MarkerOptions().position(startLocation).title("Your Start Location"))
                mMap.addMarker(MarkerOptions().position(selectedLocation).title("Your Selected Location"))
                mMap.addMarker(MarkerOptions().position(resultLocation).title("Game Result Location"))
            }
            if(mode == "visitor"){
                mMap.addMarker(MarkerOptions().position(startLocation).title("Your Start Location"))
                mMap.addMarker(MarkerOptions().position(selectedLocation).title("Your Current Location"))
                mMap.addMarker(MarkerOptions().position(resultLocation).title("Game Result Location"))
            }
            polylineOptions.add(resultLocation)
            polylineOptions.add(selectedLocation)
            mMap.addPolyline(polylineOptions)
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(startLocation))

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(startLocation, 14f))
    }

    override fun setScoreText(score:Int){
        result_score.setText(mode + " SCORE : "+score.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        resultPresenter.detachView()
    }

    override fun initPresenter() {
        resultPresenter = ResultPresenter()
    }

    override fun showError(error: String) {
        Toast.makeText(this@ResultActivity, error, Toast.LENGTH_SHORT).show()
    }

    private fun receiveIntent(){
        mode = intent.getStringExtra("mode")
        range = intent.getIntExtra("range",0)
        startLocation = LatLng(intent.getDoubleExtra("startLat", 37.54),
            intent.getDoubleExtra("startLng", 126.99))
        if(mode == "tracking") {
            resultLocationList = locationListToLatLngList(intent.getParcelableArrayListExtra("resultList"))
            selectedLocationList = locationListToLatLngList(intent.getParcelableArrayListExtra("selectedList"))
        }
        else {
            selectedLocation = LatLng(intent.getDoubleExtra("selectedLat", 37.53),
                intent.getDoubleExtra("selectedLng", 127.0))
            resultLocation = LatLng(intent.getDoubleExtra("resultLat", 37.52),
                intent.getDoubleExtra("resultLng", 128.0))
        }
    }

    private fun initBtn() {
        result_to_start_btn.setOnClickListener {
            resultPresenter.goto(this,"start")
        }
        result_to_ranking_btn.setOnClickListener {
            resultPresenter.goto(this,"ranking")
        }
    }

    fun locationListToLatLngList(locationList : ArrayList<location>) : ArrayList<LatLng>{
        val newArray = ArrayList<LatLng>()
        for(location in locationList){
            newArray.add(LatLng(location.latitude, location.longitude))
        }
        return newArray
    }
}
