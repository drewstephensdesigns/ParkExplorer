package com.github.drewstephensdesigns.parkexplorer.model

import com.google.gson.annotations.SerializedName

data class Data(
    val id: String,
    val name: String,
    val parks: List<Park>
)

data class Park(
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
    @SerializedName("name")
    val name: String
)