package com.antiquechess.data.repository

import com.antiquechess.data.remote.ChessComApi
import com.antiquechess.domain.model.Game
import com.antiquechess.domain.repository.ChessRepository

class ChessRepositoryImpl(
    private val api: ChessComApi
) : ChessRepository {

    override suspend fun getRecentGames(username: String, limit: Int): Result<List<Game>> {
        return try {
            val archivesResponse = api.getGameArchives(username)
            val allArchives = archivesResponse.archives
            
            if (allArchives.isEmpty()) {
                return Result.success(emptyList())
            }

            val games = mutableListOf<Game>()
            
            // Loop through archives backwards (newest first)
            for (i in allArchives.indices.reversed()) {
                val archiveUrl = allArchives[i]
                val gamesInArchive = api.getGamesFromArchive(archiveUrl).games
                
                // Add games from this archive, newest first within the archive
                val sortedArchiveGames = gamesInArchive.sortedByDescending { it.endTime }
                
                for (dto in sortedArchiveGames) {
                    if (dto.pgn != null) {
                        games.add(
                            Game(
                                gameId = dto.url,
                                pgn = dto.pgn,
                                endTime = dto.endTime,
                                whitePlayer = dto.white.username,
                                blackPlayer = dto.black.username
                            )
                        )
                    }
                    if (games.size >= limit) {
                        return Result.success(games)
                    }
                }
            }
            Result.success(games)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
