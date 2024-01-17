package com.brunooliveira.betmanager.util

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.produceState
import com.brunooliveira.betmanager.model.Bet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

typealias Bets = List<Bet>

fun Date.format(pattern: String = "dd/MM/yyyy HH:mm:ss") = SimpleDateFormat(pattern, Locale.getDefault()).format(this)

fun String.toDate(pattern: String = "dd/MM/yyyy HH:mm:ss") = SimpleDateFormat(pattern, Locale.getDefault()).parse(this)!!

fun Context.getActivity(): AppCompatActivity? = when (this) {
    is AppCompatActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}