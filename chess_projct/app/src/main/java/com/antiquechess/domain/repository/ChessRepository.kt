package com.antiquechess.domain.repository

import com.antiquechess.domain.model.Game

interface ChessRepository {
    suspend fun getRecentGames(username: String, limit: Int = 30): Result<List<Game>>
}
