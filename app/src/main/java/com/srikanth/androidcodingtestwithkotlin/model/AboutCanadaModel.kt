package com.srikanth.androidcodingtestwithkotlin.model

import Rows
import com.google.gson.annotations.SerializedName

/**
 * Data class for aboutCanada response
 */
data class AboutCanadaModel(
    @SerializedName("title") val title: String,
    @SerializedName("rows") val rows: List<Rows>
)