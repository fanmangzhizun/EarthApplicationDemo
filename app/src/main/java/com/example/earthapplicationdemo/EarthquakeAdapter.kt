import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.earthapplicationdemo.Feature
import com.example.earthapplicationdemo.R

class EarthquakeAdapter(private val onItemClick: (Feature) -> Unit) :
    ListAdapter<Feature, EarthquakeAdapter.EarthquakeViewHolder>(EarthquakeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarthquakeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_earthquake, parent, false)
        return EarthquakeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EarthquakeViewHolder, position: Int) {
        val earthquake = getItem(position)
        holder.bind(earthquake)
    }

    inner class EarthquakeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val placeTextView: TextView = itemView.findViewById(R.id.placeTextView)
        private val magnitudeTextView: TextView = itemView.findViewById(R.id.magnitudeTextView)
        private val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        private val coordinatesTextView: TextView = itemView.findViewById(R.id.coordinatesTextView)

        @SuppressLint("ResourceAsColor")
        fun bind(earthquake: Feature) {
            placeTextView.text = earthquake.properties.place
            magnitudeTextView.text = "Magnitude: ${earthquake.properties.mag}"
            if ((earthquake.properties.mag ?: 0) >= (7.5 as Nothing)){
                magnitudeTextView.setTextColor(R.color.purple_500)
            } else {
                magnitudeTextView.setTextColor(R.color.black)
            }
            timeTextView.text = "Time: ${earthquake.properties.time}"
            val coordinates = earthquake.geometry.coordinates
            coordinatesTextView.text = "Coordinates: (${coordinates[1]}, ${coordinates[0]})"
            itemView.setOnClickListener { onItemClick.invoke(earthquake) }
        }
    }
}

class EarthquakeDiffCallback : DiffUtil.ItemCallback<Feature>() {
    override fun areItemsTheSame(oldItem: Feature, newItem: Feature): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Feature, newItem: Feature): Boolean {
        return oldItem == newItem
    }
}
