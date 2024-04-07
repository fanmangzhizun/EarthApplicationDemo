import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.earthapplicationdemo.EarthquakeListAdapter
import com.example.earthapplicationdemo.EarthquakeViewModel
import com.example.earthapplicationdemo.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: EarthquakeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(EarthquakeViewModel::class.java)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = EarthquakeListAdapter(viewModel.earthquakes)
        recyclerView.adapter = adapter

        // 创建一个作用域为主线程的协程
        val mainScope = CoroutineScope(Dispatchers.Main)

        // 在协程中调用挂起函数
        mainScope.launch {
            viewModel.fetchEarthquakeData()
        }
    }
}
