package com.antiquechess.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "move_analysis",
    primaryKeys = ["gameId", "moveNumber"],
    foreignKeys = [
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["gameId"],
            childColumns = ["gameId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("gameId")]
)
data class MoveAnalysisEntity(
    val gameId: String,
    val moveNumber: Int,
    val evaluation: Double,
    val mistakeType: String, // Good, Inaccuracy, Mistake, Blunder
    val bestMove: String
)
