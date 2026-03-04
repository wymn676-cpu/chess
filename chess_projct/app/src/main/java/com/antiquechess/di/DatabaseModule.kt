package com.antiquechess.di

import android.app.Application
import androidx.room.Room
import com.antiquechess.data.local.ChessDatabase
import com.antiquechess.data.local.dao.ChessDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideChessDatabase(app: Application): ChessDatabase {
        return Room.databaseBuilder(
            app,
            ChessDatabase::class.java,
            "antique_chess_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideChessDao(db: ChessDatabase): ChessDao {
        return db.dao
    }
}
