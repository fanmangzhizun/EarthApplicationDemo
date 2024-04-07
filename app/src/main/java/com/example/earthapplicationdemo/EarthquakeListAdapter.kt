package com.example.earthapplicationdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EarthquakeListAdapter(private val earthquakes: List<Earthquake>) :
    RecyclerView.Adapter<EarthquakeListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val magnitudeTextView: TextView = view.findViewById(R.id.magnitudeTextView)
        val locationTextView: TextView = view.findViewById(R.id.locationTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.earthquake_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val earthquake = earthquakes[position]
        holder.magnitudeTextView.text = "Magnitude: ${earthquake.magnitude}"
        holder.locationTextView.text = "Location: ${earthquake.location}"
    }

    override fun getItemCount(): Int {
        return earthquakes.size
    }
}
