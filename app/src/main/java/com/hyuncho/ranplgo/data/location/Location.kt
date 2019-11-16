package com.hyuncho.ranplgo.data.location

class Location {

    class Location(val latitude: Long, val longitude: Long) {
        private val lat_front: Int = latitude.toInt()
        private val lat_back: Int = ((latitude - lat_front.toLong())*10000000000).toInt()
        private val lng_front: Int = longitude.toInt()
        private val lng_back: Int = ((latitude - lng_front.toLong())*10000000000).toInt()
    }

}