package com.tsgreenberg.ui_components

fun String.toMinutes(): Int {
    var newString = this
    val isPm = contains("PM")
    newString = newString.replace(if (isPm) "PM" else "AM", "").trim()
    val min = newString.split(":").map { it.toInt() }.let {
        (if (it[0] == 12) 0 else it[0]) * 60 + (if (isPm) 720 else 0) + it[1]
    }
    return min

}

fun Int.toTimeString(): String {
    val minutes = this % 60
    var hours = this.floorDiv(60)
    val isPm = hours > 12
    hours = if (isPm) hours - 12 else hours
    return "$hours:$minutes ${if (isPm) "PM" else "AM"}"
}