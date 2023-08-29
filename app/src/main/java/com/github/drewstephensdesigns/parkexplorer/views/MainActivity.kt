package com.github.drewstephensdesigns.parkexplorer.views

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.github.drewstephensdesigns.parkexplorer.adapters.ParkAdapter
import com.github.drewstephensdesigns.parkexplorer.databinding.ActivityMainBinding
import com.github.drewstephensdesigns.parkexplorer.model.Park
import com.github.drewstephensdesigns.parkexplorer.model.ParkDetails
import com.github.drewstephensdesigns.parkexplorer.utils.Config
import com.github.drewstephensdesigns.parkexplorer.utils.DividerDecoration
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity(), ParkAdapter.ParkAdapterListener {

    private lateinit var _binding: ActivityMainBinding
    private lateinit var parkAdapter: ParkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        setSupportActionBar(_binding.mainToolBar)

        _binding.loading.visibility = View.VISIBLE
        fetchParkData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchParkData() {
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val url = Config.BASE_URL

        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                try {
                    Log.e("response", response)
                    val jsonObject = JSONObject(response)
                    val jsonArray = jsonObject.getJSONArray("data")
                    val parksList = ArrayList<Park>()

                    for (i in 0 until jsonArray.length()) {
                        val parkObject = jsonArray.getJSONObject(i)
                        val id = parkObject.getString("id")
                        val name = parkObject.getString("name")
                        val parksArray = parkObject.getJSONArray("parks")

                        val parkDetailsList = ArrayList<ParkDetails>()

                        for (j in 0 until parksArray.length()) {
                            val parkDetailsObject = parksArray.getJSONObject(j)
                            val states = parkDetailsObject.getString("states")
                            val parkCode = parkDetailsObject.getString("parkCode")
                            val designation = parkDetailsObject.getString("designation")
                            val fullName = parkDetailsObject.getString("fullName")
                            val parkurl = parkDetailsObject.getString("url")
                            val parkName = parkDetailsObject.getString("name")

                            val parkDetailsBlob = ParkDetails(
                                states,
                                parkCode,
                                designation,
                                fullName,
                                parkurl,
                                parkName
                            )
                            parkDetailsList.add(parkDetailsBlob)
                        }

                        val park = Park(id, name, parkDetailsList)

                        parksList.add(park)
                    }

                    // Now you have the parsed parksList containing the data
                    // Do something with the data here
                    _binding.recyclerView.setHasFixedSize(false)
                    _binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                    parkAdapter = ParkAdapter(parksList, this)
                    _binding.recyclerView.adapter = parkAdapter

                    _binding.loading.visibility = View.GONE
                    _binding.recyclerView.apply {
                        itemAnimator = DefaultItemAnimator()
                        addItemDecoration(
                            DividerDecoration(
                                context,
                                DividerItemDecoration.VERTICAL,
                                36
                            )
                        )
                    }
                    parkAdapter.updateData(parksList)
                    parkAdapter.notifyDataSetChanged()


                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                // Handle error
                Toast.makeText(applicationContext, "Error: " + error.message, Toast.LENGTH_SHORT).show()
            })

        requestQueue.add(stringRequest)
    }

    override fun onParkClickListener(p: ParkDetails) {
        //Toast.makeText(applicationContext, "Clicked on: " + p.fullName, Toast.LENGTH_SHORT).show()
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(p.url))
        startActivity(browserIntent)
    }
}
