package com.antiquechess.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class GameEntity(
    @PrimaryKey
    val gameId: String,
    val pgn: String,
    val date: Long,
    val analyzed: Boolean = false
)
