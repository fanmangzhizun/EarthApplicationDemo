package com.example.earthapplicationdemo

data class EarthQuakeData(
    val bbox: List<Double>,
    val features: List<Feature>,
    val metadata: Metadata,
    val type: String
)

data class Feature(
    val geometry: Geometry,
    val id: String,
    val properties: Properties,
    val type: String
)

data class Metadata(
    val api: String,
    val count: Int,
    val generated: Long,
    val status: Int,
    val title: String,
    val url: String
)

data class Geometry(
    val coordinates: List<Double>,
    val type: String
)

data class Properties(
    val alert: String?=null,
    val cdi: Double?=null,
    val code: String?=null,
    val detail: String?=null,
    val dmin: Double?=null,
    val felt: Int?=null,
    val gap: Int?=null,
    val ids: String?=null,
    val mag: Double?=null,
    val magType: String?=null,
    val mmi: Double?=null,
    val net: String?=null,
    val nst: Int?=null,
    val place: String?=null,
    val rms: Double?=null,
    val sig: Int?=null,
    val sources: String?=null,
    val status: String?=null,
    val time: Long?=null,
    val title: String?=null,
    val tsunami: Int?=null,
    val type: String?=null,
    val types: String?=null,
    val tz: Any?=null,
    val updated: Long?=null,
    val url: String?=null
)