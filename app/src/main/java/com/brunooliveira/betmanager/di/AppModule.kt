package com.brunooliveira.betmanager.di

import android.content.Context
import androidx.room.Room
import com.brunooliveira.betmanager.data.dao.BetDao
import com.brunooliveira.betmanager.data.db.AppDatabase
import com.brunooliveira.betmanager.data.repository.BetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun appDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(context, AppDatabase::class.java, "bet_manager").build()

    @Provides
    fun betDao(appDatabase: AppDatabase) = appDatabase.betDao

    @Provides
    fun betRepository(betDao: BetDao) = BetRepository(betDao)

}