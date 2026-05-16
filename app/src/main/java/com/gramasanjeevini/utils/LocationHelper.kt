package com.gramasanjeevini.utils

object LocationHelper {
    fun formatDistance(distanceKm: Double): String =
        if (distanceKm < 1.0) "${(distanceKm * 1000).toInt()} m" else "$distanceKm km"
}
