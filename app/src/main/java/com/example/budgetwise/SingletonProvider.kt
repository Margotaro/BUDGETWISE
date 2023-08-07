package com.example.budgetwise

import android.app.Application
import androidx.room.Room
import com.example.budgetwise.database.RecordDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRecordDatabase(application: Application): RecordDatabase {
        return Room.databaseBuilder(
            application,
            RecordDatabase::class.java,
            "budget-wise"
        ).build()
    }
}