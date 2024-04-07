package com.example.earthapplicationdemo

data class Earthquake(
    val id: String,
    val magnitude: Double,
    val location: String,
    val time: Long,
    val latitude: Double,
    val longitude: Double
)
