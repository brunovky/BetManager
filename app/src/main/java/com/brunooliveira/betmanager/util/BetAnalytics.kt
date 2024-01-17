package com.brunooliveira.betmanager.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.brunooliveira.betmanager.R

object BetAnalytics {

    @Composable
    fun totalResult(bets: Bets): String {
        val result = bets.sumOf {
            return@sumOf when (it.status) {
                stringResource(R.string.green) -> (it.odds - 1) * it.stake
                stringResource(R.string.red) -> -it.stake
                else -> 0.0
            }
        }
        return if (result > 0.0) "+${result}u" else "${result}u"
    }

    @Composable
    fun totalROI(bets: Bets): String {
        val result = bets.sumOf {
            return@sumOf when (it.status) {
                stringResource(R.string.green) -> (it.odds - 1) * it.stake
                stringResource(R.string.red) -> -it.stake
                else -> 0.0
            }
        }
        val totalStake = bets.sumOf { it.stake }
        val roi = ((result - totalStake) / totalStake) * 100
        return "${roi}%"
    }

}