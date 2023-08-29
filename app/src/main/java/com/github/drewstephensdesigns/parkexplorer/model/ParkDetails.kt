package com.github.drewstephensdesigns.parkexplorer.model

import com.google.gson.annotations.SerializedName

data class Park(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("parks")
    val parks: List<ParkDetails>
)

data class ParkDetails(
    @SerializedName("states")
    val states: String,
    @SerializedName("parkCode")
    val parkCode: String,
    @SerializedName("designation")
    val designation: String,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("parkName")
    val parkName: String
)