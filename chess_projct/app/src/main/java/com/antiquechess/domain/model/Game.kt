package com.antiquechess.domain.model

data class Game(
    val gameId: String,
    val pgn: String,
    val endTime: Long,
    val whitePlayer: String,
    val blackPlayer: String
)
