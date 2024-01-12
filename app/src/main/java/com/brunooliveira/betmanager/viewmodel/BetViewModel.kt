package com.brunooliveira.betmanager.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brunooliveira.betmanager.data.repository.BetRepository
import com.brunooliveira.betmanager.model.Bet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BetViewModel @Inject constructor(
    private val repository: BetRepository
) : ViewModel() {

    var bet by mutableStateOf(Bet.NEW)
        private set

    fun updateTitle(title: String) {
        bet = bet.copy(
            title = title
        )
    }

    fun updateStatus(status: String) {
        bet = bet.copy(
            status = status
        )
    }

    fun updateBetHouse(betHouse: String) {
        bet = bet.copy(
            betHouse = betHouse
        )
    }

    fun updateDescription(description: String) {
        bet = bet.copy(
            description = description
        )
    }

    fun updateStake(stake: Double) {
        bet = bet.copy(
            stake = stake
        )
    }

    fun updateOdds(odds: Double) {
        bet = bet.copy(
            odds = odds
        )
    }

    fun updateSport(sport: String) {
        bet = bet.copy(
            sport = sport
        )
    }

    fun addBet() = viewModelScope.launch {
        repository.addBet(bet)
    }

}