package com.tsgreenberg.fm_schedule.utils

import java.util.*

fun Calendar.minutesFromMidnight(): Int = 60 * get(Calendar.HOUR_OF_DAY) + get(Calendar.MINUTE)