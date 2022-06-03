package com.tsgreenberg.eta_info.utils

import java.util.*

fun Date.isValidForAlarm(minutes: Int) = Date().time.let { it <= it - (minutes * 60 * 1000) }