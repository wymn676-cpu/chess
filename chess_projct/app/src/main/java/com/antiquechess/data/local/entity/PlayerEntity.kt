package com.antiquechess.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player")
data class PlayerEntity(
    @PrimaryKey
    val username: String,
    val lastSync: Long
)
