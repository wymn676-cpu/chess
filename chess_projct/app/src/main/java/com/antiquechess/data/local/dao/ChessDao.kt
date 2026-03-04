package com.antiquechess.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.antiquechess.data.local.entity.GameEntity
import com.antiquechess.data.local.entity.MoveAnalysisEntity
import com.antiquechess.data.local.entity.PlayerEntity
import com.antiquechess.data.local.entity.WeaknessStatsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChessDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: PlayerEntity)

    @Query("SELECT * FROM player WHERE username = :username LIMIT 1")
    suspend fun getPlayer(username: String): PlayerEntity?

    // Insert game, ignore if already exists so we don't overwrite analysis status
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGames(games: List<GameEntity>)

    @Query("SELECT * FROM game WHERE analyzed = 0 ORDER BY date ASC LIMIT 1")
    suspend fun getNextUnanalyzedGame(): GameEntity?

    @Query("UPDATE game SET analyzed = 1 WHERE gameId = :gameId")
    suspend fun markGameAnalyzed(gameId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoveAnalyses(analyses: List<MoveAnalysisEntity>)

    @Query("SELECT * FROM move_analysis WHERE gameId = :gameId ORDER BY moveNumber ASC")
    fun getMoveAnalyses(gameId: String): Flow<List<MoveAnalysisEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeaknessStats(stats: WeaknessStatsEntity)

    @Query("SELECT * FROM weakness_stats ORDER BY count DESC")
    fun getAllWeaknessStats(): Flow<List<WeaknessStatsEntity>>

    @Query("SELECT COUNT(*) FROM move_analysis WHERE mistakeType = 'Blunder'")
    fun getTotalBlunders(): Flow<Int>
}
