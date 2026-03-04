package com.antiquechess.di

import android.content.Context
import com.antiquechess.data.engine.StockfishEngineImpl
import com.antiquechess.domain.engine.ChessEngine
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EngineModule {

    @Provides
    @Singleton
    fun provideChessEngine(@ApplicationContext context: Context): ChessEngine {
        // In a real android application, we would extract the shipped native binary
        // from assets or jniLibs to the app's internal filesystem (context.filesDir) 
        // string. We're mocking the location here.
        val targetPath = File(context.filesDir, "libstockfish.so").absolutePath
        return StockfishEngineImpl(enginePath = targetPath)
    }
}
