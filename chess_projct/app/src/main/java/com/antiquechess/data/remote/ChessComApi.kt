package com.antiquechess.data.remote

import com.antiquechess.data.remote.dto.ArchivesResponse
import com.antiquechess.data.remote.dto.GamesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ChessComApi {

    @GET("pub/player/{username}/games/archives")
    suspend fun getGameArchives(@Path("username") username: String): ArchivesResponse

    @GET
    suspend fun getGamesFromArchive(@Url archiveUrl: String): GamesResponse
}
