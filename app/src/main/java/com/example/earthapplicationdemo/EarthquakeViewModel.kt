package com.example.earthapplicationdemo

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class EarthquakeViewModel : ViewModel() {

    // 地震数据列表
    val earthquakes = mutableListOf<Earthquake>()

    // 从 API 中获取地震数据
    suspend fun fetchEarthquakeData() {
        withContext(Dispatchers.IO) {
            val url = URL("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2023-01-01&endtime=2024-01-01&minmagnitude=7")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
                reader.close()
                parseEarthquakeData(response.toString())
            }
            connection.disconnect()
        }
    }

    // 解析地震数据
    private fun parseEarthquakeData(response: String) {
        val jsonObject = JSONObject(response)
        val jsonArray = jsonObject.getJSONObject("features").getJSONArray("features")
        for (i in 0 until jsonArray.length()) {
            val quakeObject = jsonArray.getJSONObject(i)
            val properties = quakeObject.getJSONObject("properties")
            val geometry = quakeObject.getJSONObject("geometry")
            val coordinates = geometry.getJSONArray("coordinates")

            val id = quakeObject.getString("id")
            val magnitude = properties.getDouble("mag")
            val location = properties.getString("place")
            val time = properties.getLong("time")
            val latitude = coordinates.getDouble(1)
            val longitude = coordinates.getDouble(0)

            earthquakes.add(Earthquake(id, magnitude, location, time, latitude, longitude))
        }
    }
}
