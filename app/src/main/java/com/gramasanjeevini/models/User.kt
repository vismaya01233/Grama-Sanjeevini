package com.gramasanjeevini.models

data class User(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val role: UserRole,
    val shopId: String? = null
)

enum class UserRole {
    PATIENT,
    PHARMACIST
}
