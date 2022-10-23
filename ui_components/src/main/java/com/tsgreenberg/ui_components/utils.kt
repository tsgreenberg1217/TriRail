package com.tsgreenberg.ui_components

import java.text.SimpleDateFormat
import java.util.*

fun String.toMinutes(): Int {
    var newString = this
    val isPm = contains("PM")
    newString = newString.replace(if (isPm) "PM" else "AM", "").trim()
    val min = newString.split(":").map { it.toInt() }.let {
        (if (it[0] == 12) 0 else it[0]) * 60 + (if (isPm) 720 else 0) + it[1]
    }
    return min

}

fun Date.toMinutes(): Int = SimpleDateFormat("hh:mm aa", Locale.US).format(this).toMinutes()


fun Int.toTimeString(): String {
    val minutes = this % 60
    var hours = this.floorDiv(60)
    val isPm = hours > 12
    hours = if (isPm) hours - 12 else hours
    return "$hours:$minutes ${if (isPm) "PM" else "AM"}"
}

internal fun Date.isWeekend(): Boolean = Calendar.getInstance().let {
    it.time = this
    it.get(Calendar.DAY_OF_WEEK).let { d ->
        d == Calendar.SATURDAY || d == Calendar.SUNDAY
    }
}

// HOLIDAYS 2022
internal const val NEW_YEARS = "1/1"
internal const val MEMORIAL_DAY = "5/30"
internal const val FOURTH_OF_JULY = "7/4"
internal const val LABOR_DAY = "9/5"
internal const val THANKSGIVING = "11/24"
internal const val CHRISTMAS = "12/25"

internal fun Date.isHoliday(): Boolean = Calendar.getInstance().let {
    it.time = this
    val month = it.get(Calendar.MONTH)
    val day = it.get(Calendar.DAY_OF_MONTH)
    val monthString = "$month/$day"

    when (monthString) {
        NEW_YEARS, MEMORIAL_DAY, FOURTH_OF_JULY, LABOR_DAY, THANKSGIVING, CHRISTMAS -> true
        else -> false
    }
}

fun Date.isWeekendHours(): Boolean = isWeekend() || isHoliday()


fun String.toFullStationName(): String {
    return when (this) {
        "BOC" -> "Boca Raton"
        "BOY" -> "Boynton Beach"
        "CYP" -> "Cypress Creek"
        "DFB" -> "Deerfield Beach"
        "DEL" -> "Delray Beach"
        "FTL" -> "Fort Lauderdale"
        "FLA" -> "Ft Lauderdale Airport"
        "GOL" -> "Golden Glades"
        "HIA" -> "Hialeah Market"
        "HOL" -> "Hollywood"
        "LAK" -> "Lake Worth"
        "MAN" -> "Mangonia Park"
        "MET" -> "Metrorail Transfer"
        "MIA" -> "Miami Airport"
        "OPL" -> "Opa-Locka"
        "POM" -> "Pompano Beach"
        "SHE" -> "Sheridan Street"
        "WPB" -> "West Palm Beach"
        else -> "Unknown"

    }
}
fun Int.toShortStationName():String{
    return when (this) {
        1 -> "MAN"
        2 -> "WPB"
        3 -> "LAK"
        4 -> "BOY"
        5 -> "DEL"
        6 -> "BOC"
        7 -> "DFB"
        8 -> "POM"
        9 -> "CYP"
        10 -> "FTL"
        11 -> "FLA"
        12 -> "SHE"
        13 -> "HOL"
        14 -> "GOL"
        15 -> "OPL"
        16 -> "MET"
        17 -> "HIA"
        18 -> "MIA"
        else -> "???"

    }
}

fun Int.toEtaString(): String = if (this > 59) {
    val mins = this % 60
    val hours = this.floorDiv(60)
    "$hours hr $mins mins"
} else {
    "$this mins"
}
