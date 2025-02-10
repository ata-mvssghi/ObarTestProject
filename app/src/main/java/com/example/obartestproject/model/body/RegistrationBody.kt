package com.example.obartestproject.model.body

data class RegistrationBody(
    val region: String = "1",
    val address: String,
    val lat: Double,
    val lng: Double,
    val coordinate_mobile: String,
    val coordinate_phone_number: String,
    val first_name: String,
    val last_name: String,
    val gender: String,
    )