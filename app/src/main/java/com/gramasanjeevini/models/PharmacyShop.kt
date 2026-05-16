package com.gramasanjeevini.models

data class PharmacyShop(
    val id: String = "",
    val name: String = "",
    val ownerName: String = "",
    val village: String = "",
    val distanceKm: Double = 0.0,
    val phoneNumber: String = "",
    val isOpen: Boolean = true,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)
