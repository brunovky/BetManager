package com.brunooliveira.betmanager.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.format(): String = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(this)