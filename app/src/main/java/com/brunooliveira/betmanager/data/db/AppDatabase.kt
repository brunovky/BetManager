package com.brunooliveira.betmanager.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.brunooliveira.betmanager.data.dao.BetDao
import com.brunooliveira.betmanager.model.Bet

@Database(entities = [Bet::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract val betDao: BetDao

}