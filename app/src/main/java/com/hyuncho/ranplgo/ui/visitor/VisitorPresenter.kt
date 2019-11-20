package com.hyuncho.ranplgo.ui.visitor

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.hyuncho.ranplgo.ui.googlemap.GoogleMapActivity
import com.hyuncho.ranplgo.ui.result.ResultActivity
import com.hyuncho.ranplgo.utils.Location
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

class VisitorPresenter : VisitorContract.Presenter {

    private var visitorView : VisitorContract.View? = null

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: VisitorActivity.MyLocationCallBack
    val REQUEST_ACCESS_FINE_LOCATION = 1000

    private var resultLocation : LatLng? = null
    private lateinit var startLocation : LatLng
    private var currentLocation : LatLng? = null
    private lateinit var circleMarker : CircleOptions

    override fun attachView(view: VisitorContract.View) {
        visitorView = view
    }

    override fun detachView() {
        visitorView = null
    }

    override fun gameCreate(distance: String, currentLocation : Location) {
        resultLocation = currentLocation.getRandomPlace(distance.toDouble())
        Log.d("result point", resultLocation.toString())
        startLocation = currentLocation.getLatLng()
        Log.d("start point", startLocation.toString())

        circleMarker = CircleOptions().center(startLocation)
            .radius(distance.toDouble())
            .strokeWidth(0f)
            .fillColor(Color.parseColor("#880000ff"))
        visitorView?.updateMap(startLocation, circleMarker)
    }

    override fun showStreetView(context : Context) {
        val nextIntent = Intent(context, GoogleMapActivity::class.java)
        if (resultLocation != null){
            nextIntent.putExtra("lat", resultLocation?.latitude)
            nextIntent.putExtra("lng", resultLocation?.longitude)
            context.startActivity(nextIntent)
        }
    }

    override fun visit(context:Context, range : Int){
        val nextIntent = Intent(context, ResultActivity::class.java)
        if (currentLocation != null){
            nextIntent.putExtra("startLat", startLocation.latitude)
            nextIntent.putExtra("startLng", startLocation.longitude)
            nextIntent.putExtra("resultLat", resultLocation?.latitude)
            nextIntent.putExtra("resultLng", resultLocation?.longitude)
            nextIntent.putExtra("selectedLat", currentLocation?.latitude)
            nextIntent.putExtra("selectedLng", currentLocation?.longitude)
            nextIntent.putExtra("range", range)
            nextIntent.putExtra("mode", "visitor")
            context.startActivity(nextIntent)
        }
    }

    override fun updateCurrentLocation(currentLocation: LatLng) {
        this.currentLocation = currentLocation
    }

    override fun checkPermission(context: Context) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION // 위치에 대한 권한 요청
            )
            != PackageManager.PERMISSION_GRANTED
// 사용자 권한 체크로
// 외부 저장소 읽기가 허용되지 않았다면
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    context as VisitorActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) { // 허용되지 않았다면 다시 확인.
                context.alert(
                    "사진 정보를 얻으려면 외부 저장소 권한이 필수로 필요합니다.",

                    "권한이 필요한 이유"
                ) {
                    yesButton {
                        // 권한 허용
                        ActivityCompat.requestPermissions(
                            context,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            REQUEST_ACCESS_FINE_LOCATION
                        )
                    }
                    noButton {
                        // 권한 비허용
                    }
                }.show()
            } else {
                ActivityCompat.requestPermissions(
                    context,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_ACCESS_FINE_LOCATION
                )
            }
        } else {
            addLocationListener()
        }
    }

    override fun locationInit(context : Context) {
        fusedLocationProviderClient = FusedLocationProviderClient(context)
        // 현재 사용자 위치를 저장.
        locationCallback = (context as VisitorActivity).MyLocationCallBack() // 내부 클래스 조작용 객체 생성
        locationRequest = LocationRequest() // 위치 요청.

        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        // 위치 요청의 우선순위 = 높은 정확도 우선.
        locationRequest.interval = 10000 // 내 위치 지도 전달 간격
        locationRequest.fastestInterval = 5000 // 지도 갱신 간격.
    }

    @SuppressLint("MissingPermission")
    // 위험 권한 사용시 요청 코드가 호출되어야 하는데,
    // 없어서 발생됨. 요청 코드는 따로 처리 했음.
    override fun addLocationListener() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
        //위치 권한을 요청해야 함.
        // 액티비티가 잠깐 쉴 때,
        // 자신의 위치를 확인하고, 갱신된 정보를 요청
    }


}