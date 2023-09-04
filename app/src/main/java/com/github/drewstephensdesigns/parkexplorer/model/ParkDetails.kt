package com.github.drewstephensdesigns.parkexplorer.model

import com.google.gson.annotations.SerializedName

data class DataClass(
    val total: Int,
    val limit: Int,
    val start: Int,
    val data: List<Data>
)

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
    @SerializedName("parkCode")
    val designation: String,
    @SerializedName("parkCode")
    val fullName: String,
    @SerializedName("parkCode")
    val url: String,
    @SerializedName("name")
    val name: String
)