package com.tsgreenberg.fm_eta.utils

import com.tsgreenberg.ui_components.toMinutes
import java.util.*

fun Date.isValidForAlarm(alarmTime: Int) = toMinutes() <= alarmTime
