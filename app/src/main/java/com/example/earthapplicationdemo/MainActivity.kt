import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.earthapplicationdemo.Feature
import com.example.earthapplicationdemo.Geometry
import com.example.earthapplicationdemo.Properties
import com.example.earthapplicationdemo.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EarthquakeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = EarthquakeAdapter { earthquake ->
            val intent = Intent(this, MapActivity::class.java).apply {
                putExtra("latitude", earthquake.geometry.coordinates[1])
                putExtra("longitude", earthquake.geometry.coordinates[0])
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        fetchEarthquakeData()
    }

    private fun fetchEarthquakeData() {
        CoroutineScope(Dispatchers.IO).launch {
            val url = URL("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2023-01-01&endtime=2024-01-01&minmagnitude=7")
            val connection = url.openConnection() as HttpURLConnection
            val inputStream = connection.inputStream
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val response = StringBuilder()
            var inputLine: String?
            while (bufferedReader.readLine().also { inputLine = it } != null) {
                response.append(inputLine)
            }
            bufferedReader.close()
            connection.disconnect()

            val jsonObject = JSONObject(response.toString())
            val features = jsonObject.getJSONArray("features")

            val earthquakes = mutableListOf<Feature>()
            for (i in 0 until features.length()) {
                val feature = features.getJSONObject(i)
                val properties = feature.getJSONObject("properties")
                val geometry = feature.getJSONObject("geometry")
                val coordinates = geometry.getJSONArray("coordinates")
                val earthquake = Feature(
                    Geometry(listOf(coordinates.getDouble(0)), coordinates.getDouble(1).toString()),
                    properties.getString("id"),
                    Properties(
                        properties.getDouble("mag").toString(),
                        properties.getLong("time").toDouble(),
                        properties.getString("place")
                    ),
                    "Feature"
                )
                earthquakes.add(earthquake)
            }

            runOnUiThread {
                adapter.submitList(earthquakes)
            }
        }
    }
}
