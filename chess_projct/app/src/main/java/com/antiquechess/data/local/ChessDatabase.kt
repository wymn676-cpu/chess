package com.antiquechess.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.antiquechess.data.local.dao.ChessDao
import com.antiquechess.data.local.entity.GameEntity
import com.antiquechess.data.local.entity.MoveAnalysisEntity
import com.antiquechess.data.local.entity.PlayerEntity
import com.antiquechess.data.local.entity.WeaknessStatsEntity

@Database(
    entities = [
        PlayerEntity::class,
        GameEntity::class,
        MoveAnalysisEntity::class,
        WeaknessStatsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ChessDatabase : RoomDatabase() {
    abstract val dao: ChessDao
}
