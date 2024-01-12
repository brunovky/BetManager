package com.brunooliveira.betmanager.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.brunooliveira.betmanager.model.Bet

@Dao
interface BetDao {

    @Query("SELECT * FROM bet ORDER BY date DESC")
    fun getBets(): List<Bet>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBet(bet: Bet)

    @Update
    suspend fun updateBet(bet: Bet)

    @Delete
    suspend fun deleteBet(bet: Bet)

}