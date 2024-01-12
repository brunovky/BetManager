package com.brunooliveira.betmanager.data.repository

import com.brunooliveira.betmanager.data.dao.BetDao
import com.brunooliveira.betmanager.model.Bet

class BetRepository(private val betDao: BetDao) {

    fun getBets() = betDao.getBets()

    suspend fun addBet(bet: Bet) = betDao.addBet(bet)

    suspend fun updateBet(bet: Bet) = betDao.updateBet(bet)

    suspend fun deleteBet(bet: Bet) = betDao.deleteBet(bet)

}