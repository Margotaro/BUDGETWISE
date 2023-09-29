package com.example.budgetwise

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.budgetwise.database.RecordDAO
import com.example.budgetwise.database.RecordDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideRecordDAO(recDatabase: RecordDatabase): RecordDAO {
        return recDatabase.recordDao()
    }
    @Provides
    @Singleton
    fun provideRecordDatabase(@ApplicationContext appContext: Context): RecordDatabase {
        return Room.databaseBuilder(
            appContext,
            RecordDatabase::class.java,
            "RecReader"
        ).build()
    }
}