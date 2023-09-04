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
import com.github.drewstephensdesigns.parkexplorer.BuildConfig
import com.github.drewstephensdesigns.parkexplorer.adapters.ParkAdapter
import com.github.drewstephensdesigns.parkexplorer.databinding.ActivityMainBinding
import com.github.drewstephensdesigns.parkexplorer.model.Data
import com.github.drewstephensdesigns.parkexplorer.model.Park
import com.github.drewstephensdesigns.parkexplorer.utils.DividerDecoration
import com.maxkeppeler.sheets.input.InputSheet
import com.maxkeppeler.sheets.input.type.spinner.InputSpinner
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity(), ParkAdapter.ParkAdapterListener {

    private lateinit var _binding: ActivityMainBinding
    private lateinit var parkAdapter: ParkAdapter

    // Prevents API Key from being exposed by version control
    private val key = BuildConfig.API_KEY

    // Default Park Topic
    private var selectedTopic: String = "Laborer and Worker" // Default topic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        setSupportActionBar(_binding.mainToolBar)

        _binding.loading.visibility = View.VISIBLE

        _binding.recyclerView.setHasFixedSize(false)
        _binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)

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

        fetchParkData()
        bottomSheet()
    }

    // BottomSheet Plugin
    // Makes the Park Topic cleaner
    private fun bottomSheet() {
        _binding.topicFilter.setOnClickListener {

            InputSheet().show(this) {
                title("National Parks")

                with(InputSpinner {
                    label("Select a Topic from the dropdown")
                    options(
                        // First 40 topics
                        mutableListOf(
                            "African American Heritage",
                            "American Revolution",
                            "Ancient Seas",
                            "Animals",
                            "Archeology",
                            "architecture and Building",
                            "Arctic",
                            "Arts",
                            "Asian American Heritage",
                            "Aviation",
                            "Banking",
                            "Birthplace",
                            "Burial, Cemetary and Gravesite",
                            "Canyons and Canyonlands",
                            "Caves, Caverns and Karst",
                            "Climate Change",
                            "Coasts, Islands and Atolls",
                            "Colonization and Settlement",
                            "Commerce",
                            "Dams",
                            "Dunes",
                            "Engineering",
                            "Enslavement",
                            "Estuaries and Mangroves",
                            "Explorers and Expeditions",
                            "Farming and Agriculture",
                            "Fire",
                            "Foothills, Plains and Valleys",
                            "Forests and Woodlands",
                            "Forts",
                            "Fossils and Paleontology",
                            "Geology",
                            "Geothermal",
                            "Glaciers",
                            "Grasslands",
                            "Great Depression",
                            "Groundwater",
                            "Hispanic American Heritage",
                            "Immigration",
                            "Impact Craters",
                        )
                    )

                    // Changes the JSON response when user selects a topic
                    // Only showing first 40 topics for clarity
                    changeListener { value ->
                        when (value) {
                            0 -> {
                                onTopicSelected("African American Heritage")
                            }

                            1 -> {
                                onTopicSelected("American Revolution")
                            }

                            2 -> {
                                onTopicSelected("Ancient Seas")
                            }

                            3 -> {
                                onTopicSelected("Animals")
                            }

                            4 -> {
                                onTopicSelected("Archeology")
                            }

                            5 -> {
                                onTopicSelected("Architecture and Building")
                            }

                            6 -> {
                                onTopicSelected("Arctic")
                            }

                            7 -> {
                                onTopicSelected("Arts")
                            }

                            8 -> {
                                onTopicSelected("Asian American Heritage")
                            }

                            9 -> {
                                onTopicSelected("Aviation")
                            }

                            10 -> {
                                onTopicSelected("Banking")
                            }

                            11 -> {
                                onTopicSelected("Birthplace")
                            }

                            12 -> {
                                onTopicSelected("Burial, Cemetary and Gravesite")
                            }

                            13 -> {
                                onTopicSelected("Canyons and Canyonlands")
                            }

                            14 -> {
                                onTopicSelected("Caves, Caverns and Karst")
                            }

                            15 -> {
                                onTopicSelected("Climate Change")
                            }

                            16 -> {
                                onTopicSelected("Coasts, Islands and Atolls")
                            }

                            17 -> {
                                onTopicSelected("Colonization and Settlement")
                            }

                            18 -> {
                                onTopicSelected("Commerce")
                            }

                            19 -> {
                                onTopicSelected("Dams")
                            }

                            20 -> {
                                onTopicSelected("Dunes")
                            }

                            21 -> {
                                onTopicSelected("Engineering")
                            }

                            22 -> {
                                onTopicSelected("Enslavement")
                            }

                            23 -> {
                                onTopicSelected("Estuaries and Mangroves")
                            }

                            24 -> {
                                onTopicSelected("Explorers and Expeditions")
                            }

                            25 -> {
                                onTopicSelected("Farming and Agriculture")
                            }

                            26 -> {
                                onTopicSelected("Fire")
                            }

                            27 -> {
                                onTopicSelected("Foothills, Plains and Valleys")
                            }

                            28 -> {
                                onTopicSelected("Forests and Woodlands")
                            }

                            29 -> {
                                onTopicSelected("Forts")
                            }

                            30 -> {
                                onTopicSelected("Fossils and Paleontology")
                            }

                            31 -> {
                                onTopicSelected("Geology")
                            }

                            32 -> {
                                onTopicSelected("Geothermal")
                            }

                            33 -> {
                                onTopicSelected("Glaciers")
                            }

                            34 -> {
                                onTopicSelected("Grasslands")
                            }

                            35 -> {
                                onTopicSelected("Great Depression")
                            }

                            36 -> {
                                onTopicSelected("Groundwater")
                            }

                            37 -> {
                                onTopicSelected("Hispanic American Heritage")
                            }

                            38 -> {
                                onTopicSelected("Immigration")
                            }

                            39 -> {
                                onTopicSelected("Impact Craters")
                            }

                            40 -> {
                                onTopicSelected("Incarceration")
                            }
                            else -> {
                                //onTopicSelected("Oceans")
                            }
                        }
                    }
                })
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchParkData() {
        val requestQueue = Volley.newRequestQueue(applicationContext)

        // Replace the API URL with a template and insert the selected topic
        val baseUrl = "https://developer.nps.gov/api/v1/topics/parks?&q={query}&api_key=$key&limit=3000"

        val url = baseUrl.replace("{query}", selectedTopic)

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val parksList = parseParksData(response)
                    updateRecyclerView(parksList[0].parks)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                // Handle error
                Toast.makeText(applicationContext, "Error: " + error.message, Toast.LENGTH_SHORT)
                    .show()
            })

        requestQueue.add(stringRequest)
    }

    private fun onTopicSelected(topic: String) {
        selectedTopic = topic
        fetchParkData() // Fetch data for the selected topic
    }

    private fun parseParksData(response: String): List<Data> {
        val parksList = ArrayList<Data>()

        val jsonObject = JSONObject(response)

        val total = jsonObject.getString("total")
        val limit = jsonObject.getString("limit")
        val start = jsonObject.getString("start")

        val jsonArray = jsonObject.getJSONArray("data")

        for (i in 0 until jsonArray.length()) {
            val parkObject = jsonArray.getJSONObject(i)
            val id = parkObject.getString("id")
            val name = parkObject.getString("name")

            val parkDetailsList = ArrayList<Park>()
            val parksArray = parkObject.getJSONArray("parks")
            for (j in 0 until parksArray.length()) {
                val parkDetailsObject = parksArray.getJSONObject(j)
                val states = parkDetailsObject.getString("states")
                val parkCode = parkDetailsObject.getString("parkCode")
                val designation = parkDetailsObject.getString("designation")
                val fullName = parkDetailsObject.getString("fullName")
                val parkUrl = parkDetailsObject.getString("url")
                val parkName = parkDetailsObject.getString("name")

                parkDetailsList.add(
                    Park(
                        states,
                        parkCode,
                        designation,
                        fullName,
                        parkUrl,
                        parkName
                    )
                )
            }

            val parkData = Data(id, name, parkDetailsList)
            parksList.add(parkData)
        }
        Log.i("PARSE PARKS", "Total Is: $total+ Limit Is: $limit+ Start Is: $start")
        return parksList
    }

    private fun updateRecyclerView(parksList: List<Park>) {
        parkAdapter = ParkAdapter(parksList, this)
        _binding.recyclerView.adapter = parkAdapter
        _binding.loading.visibility = View.GONE
        parkAdapter.notifyDataSetChanged()
    }


    override fun onParkClickListener(p: Park) {
        //Toast.makeText(applicationContext, "Clicked on: " + p.fullName, Toast.LENGTH_SHORT).show()
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(p.url))
        startActivity(browserIntent)
    }
}
