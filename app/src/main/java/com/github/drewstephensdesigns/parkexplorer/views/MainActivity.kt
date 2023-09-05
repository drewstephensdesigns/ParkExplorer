package com.github.drewstephensdesigns.parkexplorer.views

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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

        // Initial Fetching
        fetchParkData()

        // Bottom Sheet for cleaner look
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

                    // Original Code
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
                            "Incarceration",
                            "Industry",
                            "LGBTQ Industry",
                            "Laborer and Worker",
                            "Lakes",
                            "Landscape Design"
                        )
                 )

                    // Changes the JSON response when user selects a topic
                    // Only showing first 40 topics for clarity
                    val topics = mapOf(
                        0 to "African American Heritage",
                        1 to "American Revolution",
                        2 to "Ancient Seas",
                        3 to "Animals",
                        4 to "Archeology",
                        5 to "Architecture and Building",
                        6 to "Arctic",
                        7 to "Arts",
                        8 to "Asian American Heritage",
                        9 to "Aviation",
                        10 to "Banking",
                        11 to "Birthplace",
                        12 to "Burial, Cemetery, and Gravesite",
                        13 to "Canyons and Canyonlands",
                        14 to "Caves, Caverns, and Karst",
                        15 to "Climate Change",
                        16 to "Coasts, Islands, and Atolls",
                        17 to "Colonization and Settlement",
                        18 to "Commerce",
                        19 to "Dams",
                        20 to "Dunes",
                        21 to "Engineering",
                        22 to "Enslavement",
                        23 to "Estuaries and Mangroves",
                        24 to "Explorers and Expeditions",
                        25 to "Farming and Agriculture",
                        26 to "Fire",
                        27 to "Foothills, Plains, and Valleys",
                        28 to "Forests and Woodlands",
                        29 to "Forts",
                        30 to "Fossils and Paleontology",
                        31 to "Geology",
                        32 to "Geothermal",
                        33 to "Glaciers",
                        34 to "Grasslands",
                        35 to "Great Depression",
                        36 to "Groundwater",
                        37 to "Hispanic American Heritage",
                        38 to "Immigration",
                        39 to "Impact Craters",
                        40 to "Incarceration",
                        41 to "Industry",
                        42 to "LGBTQ Industry",
                        43 to "Laborer and Worker",
                        44 to "Lakes",
                        45 to "Landscape Design"
                    )
                    changeListener { value ->
                        topics[value]?.let { onTopicSelected(it) }
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
        _binding.mainToolBar.title = topic
    }

    private fun parseParksData(response: String): List<Data> {
        val parksList = ArrayList<Data>()

        val jsonObject = JSONObject(response)

        val jsonArray = jsonObject.getJSONArray("data")

        for (i in 0 until jsonArray.length()) {
            val parkObject = jsonArray.getJSONObject(i)
            val id = parkObject.getString("id")
            val name = parkObject.getString("name")

            val parkDetailsList = ArrayList<Park>()
            val parksArray = parkObject.getJSONArray("parks")
            for (j in 0 until parksArray.length()) {
                val parkDetailsObject = parksArray.getJSONObject(j)
                val fullName = parkDetailsObject.getString("fullName")
                val parkName = parkDetailsObject.getString("name")
                val states = parkDetailsObject.getString("states")
                val designation = parkDetailsObject.getString("designation")
                val parkCode = parkDetailsObject.getString("parkCode")
                val parkUrl = parkDetailsObject.getString("url")

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
        return parksList
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateRecyclerView(parksList: List<Park>) {
        parkAdapter = ParkAdapter(parksList, this)
        _binding.recyclerView.adapter = parkAdapter
        _binding.loading.visibility = View.GONE
        parkAdapter.notifyDataSetChanged()
    }


    // Opens NPS webpage of corresponding park
    override fun onParkClickListener(p: Park) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(p.url))
        startActivity(browserIntent)
    }
}
