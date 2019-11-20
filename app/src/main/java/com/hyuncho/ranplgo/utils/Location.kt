package com.hyuncho.ranplgo.utils

import com.google.android.gms.maps.model.LatLng
import java.lang.Math.*
import java.util.*
import kotlin.collections.ArrayList

class Location (latlng: LatLng){

    private val latlng = latlng

    private val latperKM = 1 / (6400 * 2 * PI / 360)
    private val lngperKM = 1 / (cos(latlng.latitude) * 6400 * 2 * PI / 360)

    val random = Random()

    fun getLat() : Double{
        return latlng.latitude
    }
    fun getLng() : Double{
        return latlng.longitude
    }
    fun getLatLng() : LatLng {
        return latlng
    }

    fun getRandomPlace(meter : Double) : LatLng{
        val km  = random.nextDouble() * meter / 1000
        val latVector = (random.nextDouble()*2-1)
        val lngVector = (random.nextDouble()*2-1)
        var normalizedVector : LatLng = normalize(latVector, lngVector)

        val newLocation = LatLng(latlng.latitude + normalizedVector.latitude*latperKM*km,
            latlng.longitude + normalizedVector.longitude*lngperKM * km)
        return newLocation
    }

    fun getTrackCourse(meter:Double): ArrayList<LatLng> {
        val km  = random.nextDouble() * (meter / 1000)/2 + meter/1000/2
        val trackingCourse = ArrayList<LatLng>()
        trackingCourse.clear()
        val pointNum = random.nextInt(2) + 2

        var latVector : Double = (random.nextDouble()*2-1)
        var lngVector : Double = (random.nextDouble()*2-1)
        var normalizedVector : LatLng = normalize(latVector, lngVector)
        var newLocation : LatLng = LatLng(latlng.latitude + normalizedVector.latitude*latperKM*km,
            latlng.longitude + normalizedVector.longitude*lngperKM * km)
        trackingCourse.add(newLocation)

        var theta : Int

        for(index in 0..pointNum){
            theta = random.nextInt(60) + 30
            normalizedVector = rotateVector(normalizedVector, theta)
            newLocation  = LatLng(latlng.latitude + normalizedVector.latitude*latperKM*km,
                latlng.longitude + normalizedVector.longitude*lngperKM * km)
            trackingCourse.add(newLocation)
        }

        return trackingCourse
    }

    fun getDistance(other: LatLng) : Double{
        val X = latlng.latitude - other.latitude
        val XM = 6400 * 2 * PI / 360 * X / 1000
        val Y = latlng.longitude - other.longitude
        val YM = cos(latlng.latitude) * 6400 * 2 * PI / 360 * Y / 1000
        val distance = sqrt(XM * XM + YM * YM)
        return distance
    }

    fun getScore(other : LatLng, range:Int) : Int {
        val distance = getDistance(other)
        val score = sqrt(range.toDouble()-distance/2) * sqrt(range.toDouble())
        return score.toInt()
    }

    private fun normalize(latVector : Double, lngVector : Double) : LatLng{
        val distance = sqrt(latVector * latVector + lngVector * lngVector)
        return LatLng(latVector/distance , lngVector/distance)
    }

    private fun rotateVector(normalizedVector : LatLng, theta : Int ) : LatLng{
        var newTheta : Double = theta.toDouble()

        val rotatedLat = cos(newTheta) * normalizedVector.latitude - sin(newTheta) * normalizedVector.longitude
        val rotatedLng = sin(newTheta) * normalizedVector.latitude + cos(newTheta) * normalizedVector.longitude
        return LatLng(rotatedLat, rotatedLng)
    }


}