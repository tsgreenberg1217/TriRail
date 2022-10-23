package com.tsgreenberg.eta_info.utils

import com.tsgreenberg.ui_components.toMinutes
import java.util.*

fun Date.isValidForAlarm(alarmTime: Int) = toMinutes() <= alarmTime
