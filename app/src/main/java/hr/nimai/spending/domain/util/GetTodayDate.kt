package hr.nimai.spending.domain.util

import java.text.SimpleDateFormat
import java.util.*

fun getTodayDate(): String {
    val c = Calendar.getInstance().time
    val sdf = SimpleDateFormat("dd.MM.yyyy.", Locale.GERMANY)
    return sdf.format(c)
}