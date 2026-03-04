package com.antiquechess.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weakness_stats")
data class WeaknessStatsEntity(
    @PrimaryKey
    val category: String, // e.g., "Missed Mate", "Hanging Piece", "Opening Blunter"
    val count: Int
)
