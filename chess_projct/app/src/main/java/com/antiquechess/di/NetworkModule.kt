package com.antiquechess.di

import com.antiquechess.data.remote.ChessComApi
import com.antiquechess.data.repository.ChessRepositoryImpl
import com.antiquechess.domain.repository.ChessRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.chess.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideChessComApi(retrofit: Retrofit): ChessComApi {
        return retrofit.create(ChessComApi::class.java)
    }

    @Provides
    @Singleton
    fun provideChessRepository(api: ChessComApi): ChessRepository {
        return ChessRepositoryImpl(api)
    }
}
