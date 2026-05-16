package com.gramasanjeevini.models

data class Medicine(
    val id: String = "",
    val name: String = "",
    val genericName: String = "",
    val category: String = "",
    val strength: String = "",
    val priceRupees: Double = 0.0,
    val stockCount: Int = 0,
    val expiryDate: String = "",
    val shopId: String = ""
) {
    val isLowStock: Boolean
        get() = stockCount <= 10
}
