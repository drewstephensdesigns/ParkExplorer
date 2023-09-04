package com.github.drewstephensdesigns.parkexplorer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.drewstephensdesigns.parkexplorer.databinding.LayoutParkBinding
import com.github.drewstephensdesigns.parkexplorer.model.Park

class ParkAdapter(
    private val parks: List<Park>,
    private val listener: ParkAdapterListener
) : RecyclerView.Adapter<ParkAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutParkBinding.inflate(LayoutInflater.from(parent.context), parent, false )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val parkAllInfo = parks[position]
            holder.apply {
                parkFullName.text = parkAllInfo.fullName
                parkCategory.text = parkAllInfo.name
                parkStateLocation.text = parkAllInfo.states
                parkDesignation.text = parkAllInfo.designation
            }
    }

    override fun getItemCount(): Int = parks.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(binding: LayoutParkBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onParkClickListener(parks[bindingAdapterPosition])
            }
        }
        var parkFullName: TextView = binding.parkFullName
        var parkStateLocation: TextView = binding.parkStateLocation
        var parkDesignation: TextView = binding.parkDesignation
        var parkCategory: TextView = binding.parkCategory

    }

    interface ParkAdapterListener {
        fun onParkClickListener(p: Park)
    }
}