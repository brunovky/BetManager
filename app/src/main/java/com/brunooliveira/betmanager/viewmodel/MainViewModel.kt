package com.brunooliveira.betmanager.viewmodel

import androidx.lifecycle.ViewModel
import com.brunooliveira.betmanager.data.repository.BetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: BetRepository
) : ViewModel() {

    val bets = repository.getBets()

}