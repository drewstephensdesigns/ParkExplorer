package com.github.drewstephensdesigns.parkexplorer.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.drewstephensdesigns.parkexplorer.databinding.LayoutParkBinding
import com.github.drewstephensdesigns.parkexplorer.model.Park
import com.github.drewstephensdesigns.parkexplorer.model.ParkDetails
import kotlinx.coroutines.awaitAll

class ParkAdapter(
    private val parks: ArrayList<Park>,
    private val listener: ParkAdapterListener
) : RecyclerView.Adapter<ParkAdapter.ViewHolder>() {

    private var parksListAll: List<Park> = parks

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutParkBinding.inflate(LayoutInflater.from(parent.context), parent, false )
        return ViewHolder(binding)
    }

    fun updateData(newParks: List<Park>) {
        parksListAll = newParks
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val parkAllInfo = parks[0].parks[position]
            holder.apply {
                parkFullName.text = parkAllInfo.fullName
                parkStateLocation.text = parkAllInfo.states
                parkDesignation.text = parkAllInfo.designation
                parkCategory.text = parkAllInfo.parkName

                //dataName.text = p
            }
    }

    override fun getItemCount(): Int {
        return parks[0].parks.size

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(binding: LayoutParkBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onParkClickListener(parks[0].parks[adapterPosition])
            }
        }
        var parkFullName: TextView = binding.parkFullName
        var parkStateLocation: TextView = binding.parkStateLocation
        var parkDesignation: TextView = binding.parkDesignation
        var parkCategory: TextView = binding.parkCategory
        var dataName : TextView = binding.dataName

    }

    interface ParkAdapterListener {
        fun onParkClickListener(p: ParkDetails)
    }
}