package com.antiquechess.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GamesResponse(
    @SerializedName("games")
    val games: List<GameDto>
)

data class GameDto(
    @SerializedName("url") val url: String,
    @SerializedName("pgn") val pgn: String?,
    @SerializedName("end_time") val endTime: Long,
    @SerializedName("white") val white: PlayerInfoDto,
    @SerializedName("black") val black: PlayerInfoDto
)

data class PlayerInfoDto(
    @SerializedName("username") val username: String,
    @SerializedName("result") val result: String
)
