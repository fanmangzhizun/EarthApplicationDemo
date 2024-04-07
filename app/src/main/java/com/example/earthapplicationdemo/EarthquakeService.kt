import com.example.earthapplicationdemo.EarthQuakeData
import retrofit2.http.GET
import retrofit2.http.Query

interface EarthquakeService {
    @GET("query?format=geojson")
    suspend fun getEarthquakes(
        @Query("starttime") startTime: String,
        @Query("endtime") endTime: String,
        @Query("minmagnitude") minMagnitude: Double
    ): EarthQuakeData
}
