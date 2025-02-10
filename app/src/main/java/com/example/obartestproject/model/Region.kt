package com.example.obartestproject.model

data class Region(
    val city_object: CityObject,
    val id: Int,
    val name: String,
    val state_object: StateObject
)