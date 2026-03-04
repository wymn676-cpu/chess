package com.antiquechess.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ArchivesResponse(
    @SerializedName("archives")
    val archives: List<String>
)
