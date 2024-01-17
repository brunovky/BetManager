package com.brunooliveira.betmanager.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import com.brunooliveira.betmanager.data.repository.BetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: BetRepository
) : ViewModel() {

    val bets = repository.getBets()

    var currentContent = mutableIntStateOf(MAIN_CONTENT)

}

const val MAIN_CONTENT = 0
const val DASHBOARD_CONTENT = 1